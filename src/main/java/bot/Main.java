
package bot;
public class Main {
    public static void main(String[] args) throws Exception {
        String command = args[0];
        LogicDialog logicDialog = new LogicDialog();
        logicDialog.startDialog(command);
    }
}
 