package bot;
import java.util.Scanner;
public class ConsoleDialog implements Dialog {
    private Scanner scanner = new Scanner(System.in);
    public UserMessage takeArg(User user){
        String result = scanner.nextLine();
        result = result.toLowerCase();
        return new UserMessage(new User(1), result);
    }
    public void print(User user, String command) {
        System.out.println(command);
    }
}
