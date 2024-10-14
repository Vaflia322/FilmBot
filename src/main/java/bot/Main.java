
package bot;
public class Main {
    public static void main(String[] args) {
        String command = args[0];
        ApiFilm apiFilm = new ApiFilm();
        LogicDialog logicDialog = new LogicDialog(apiFilm);
        logicDialog.startDialog(command);
    }
}
 