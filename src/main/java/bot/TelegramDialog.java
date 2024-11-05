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
    private final Map<Long, BlockingQueue<UserMessage>> userMessageQueues = new HashMap<>();
    private final Map<Long, LogicDialog> userDialogs = new HashMap<>(); // Хранит активные диалоги пользователей
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText().toLowerCase();
            Long userId = update.getMessage().getChatId();
            deleteUserDialog();
            if (userDialogs.containsKey(userId)){
                userMessageQueues.get(userId).offer(new UserMessage(new User(userId), messageText));
            }
            else{
                LogicDialog logicDialog = new LogicDialog(new ApiFilm(), this);
                BlockingQueue<UserMessage> userBlockingQueue = new LinkedBlockingQueue<>();
                userBlockingQueue.offer(new UserMessage(new User(userId), messageText));
                userMessageQueues.put(userId, userBlockingQueue);
                userDialogs.put(userId,logicDialog);
                new Thread(() -> logicDialog.startDialog(new User(userId))).start();
            }
        }
    }
    private void deleteUserDialog(){
        for (Long key : userDialogs.keySet()) {
            LogicDialog logicDialog = userDialogs.get(key);
            if (!logicDialog.getIsRunning()){
                userDialogs.remove(key);
                userMessageQueues.remove(key);
            }
        }
    }
    public UserMessage takeArg(User user) {
        BlockingQueue<UserMessage> queue = userMessageQueues.get(user.getUserID());
        try {
            return queue.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
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
