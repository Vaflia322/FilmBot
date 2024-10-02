import java.util.Scanner;
public class ConsoleBot {
    public String takeArg(){
        Scanner sc = new Scanner(System.in);
        String result = sc.nextLine();
        result = result.toLowerCase();
        return result;
    }
    public void outConsole(String command) throws Exception {
        System.out.println(command);
    }
}
