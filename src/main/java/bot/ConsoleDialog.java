package bot;
import java.util.Scanner;
public class ConsoleDialog implements Dialog {
    private Scanner scanner = new Scanner(System.in);
    public void runDialog(){
        String command = scanner.nextLine();
        command = command.toLowerCase();
        User user = new User(1);
        LogicDialog logicDialog = new LogicDialog(new ApiFilm(),this);
        print(user, "Привет! Я фильм бот.");
        while (!command.equals("стоп")){
            UserState state = logicDialog.makeState(user,command);;
            logicDialog.statusProcessing(user,state,command);
            command = scanner.nextLine();
            command = command.toLowerCase();
        }
    }
    @Override
    public void print(User user, String command) {
        System.out.println(command);
    }
}
