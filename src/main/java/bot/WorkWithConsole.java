package bot;
import java.util.Scanner;
public class WorkWithConsole {
    private Scanner scanner = new Scanner(System.in);
    public String takeArg(){
        String result = scanner.nextLine();
        result = result.toLowerCase();
        return result;
    }
    public void print(String command) throws Exception {
        System.out.println(command);
    }
}
