
package bot;
public class Main {
    public static void main(String[] args) {
        String command = args[0];
        ApiFilm apiFilm = new ApiFilm();
        DialogInterface workWithConsole = new WorkWithConsole();
        LogicDialog logicDialog = new LogicDialog(apiFilm,workWithConsole);
        logicDialog.startDialog(command);
    }
}
 