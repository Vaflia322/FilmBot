package bot;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class StartTelegramBot {
    public void startBot() {
        TelegramBotsApi telegramBotsApi;
        try {
            telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class) ;
            telegramBotsApi.registerBot(new TelegramDialog());
        }catch(TelegramApiException e){
            e.printStackTrace();
        }
    }
}
