package bot;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


public class TelegramDialog extends TelegramLongPollingBot implements Dialog {
    private String BOT_TOKEN;
    private String BOT_USERNAME;
    private LogicDialog logicDialog = new LogicDialog(new ApiFilm(),this);
    {
        BufferedReader tgReader;
        try {
            tgReader = new BufferedReader(new FileReader("tg.txt"));
            BOT_TOKEN = tgReader.readLine();
            BOT_USERNAME = tgReader.readLine();
        } catch (
                FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private final Map<User, String> userDialogs = new HashMap<>(); // Хранит активные диалоги пользователей
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText().toLowerCase();
            Long userId = update.getMessage().getChatId();
            deleteUserDialog();
            User user = null;
            boolean userIsRun = false;
            for (User key : userDialogs.keySet()){
                if (userId == key.getUserID()){
                    user = key;
                    userIsRun = true;
                    break;
                }
            }
            if (!userIsRun){
                user = new User(userId);
                print(user, "Привет! Я фильм бот.");
            }
            String state = logicDialog.makeState(user,messageText);
            userDialogs.put(user,state);
            logicDialog.statusProcessing(user,state,messageText);
        }
    }
    private void deleteUserDialog(){
        for (User key : userDialogs.keySet()) {
            String state = userDialogs.get(key);
            if (state.equals("end")){
                userDialogs.remove(key);
            }
        }
    }
    public void print(User user,String text){
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
        return BOT_USERNAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }
}
