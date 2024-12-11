
package bot;

public class Main {
    public static void main(String[] args) {
        String arg = args[0];
        StartTelegramBot startTelegramBot = new StartTelegramBot();
        ConsoleDialog consoleDialog = new ConsoleDialog();
        switch (arg) {
            case ("-everywhere"):
                startTelegramBot.startBot();
                new Thread(() -> consoleDialog.runDialog()).start();
                break;
            case ("-console"):
                consoleDialog.runDialog();
                break;
            case ("-telegram"):
                startTelegramBot.startBot();
                break;
            default:
                throw new RuntimeException("Некорректные аргменуты");
        }
    }
}
 