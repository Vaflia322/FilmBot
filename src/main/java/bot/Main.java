
package bot;
public class Main {
    public static void main(String[] args) {
        String arg = args[0];
        StartTelegramBot startTelegramBot = new StartTelegramBot();
        ApiFilm apiFilm = new ApiFilm();
        System.out.println(arg);
        Dialog dialog = new ConsoleDialog();
        User user = new User(1);
        LogicDialog logicDialog = new LogicDialog(apiFilm, dialog);
        switch(arg){
            case("-everywhere"):
                startTelegramBot.startBot();
                new Thread(() -> logicDialog.startDialog(user)).start();
                break;
            case("-console"):
                logicDialog.startDialog(user);
                break;
            case("-telegram"):
                startTelegramBot.startBot();
                break;
        }
    }
}
 