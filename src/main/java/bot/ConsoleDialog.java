package bot;
import java.util.Scanner;
public class ConsoleDialog implements Dialog {
    private Scanner scanner = new Scanner(System.in);
    public String takeArg(){
        String result = scanner.nextLine();
        result = result.toLowerCase();
        return result;
    }
    public void print(String command) {
        System.out.println(command);
    }
}