package bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class TelegramDialog extends TelegramLongPollingBot implements Dialog {
    private String botToken;
    private String botUsername;
    private LogicDialog logicDialog = new LogicDialog(new ApiFilm(), this);

    {
        BufferedReader tgReader;
        try {
            tgReader = new BufferedReader(new FileReader("tg.txt"));
            botToken = tgReader.readLine();
            botUsername = tgReader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private final Map<Long, UserData> userDialogs = new HashMap<>(); // Хранит активные диалоги пользователей

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText().toLowerCase();
            Long userId = update.getMessage().getChatId();
            UserData userData;
            if (userDialogs.containsKey(userId)) {
                userData = userDialogs.get(userId);
            } else {
                userData = new UserData();
                userData.setUser(new User(userId));
                print(userData.getUser(), "Привет! Я фильм бот.");
            }
            UserState state = logicDialog.makeState(userData.getUser(), messageText);
            userData.setState(state);
            userDialogs.put(userId, userData);
            logicDialog.statusProcessing(userData.getUser(), state, messageText);
            if (userDialogs.get(userId).getState().equals(UserState.END)) {
                userDialogs.remove(userId);
            }
        }
    }


    @Override
    public void print(User user, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(user.getUserID()));
        sendMessage.setText(text);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }
}
