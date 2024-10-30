
package bot;
public class Main {
    public static void main(String[] args) {
        String command = args[0];
        ApiFilm apiFilm = new ApiFilm();
        Dialog dialog = new ConsoleDialog();
        LogicDialog logicDialog = new LogicDialog(apiFilm,dialog);
        logicDialog.startDialog(command);
    }
}
 