package main.java.Bot;
import java.util.Scanner;
public class ConsoleBot {
    private Scanner sc = new Scanner(System.in);
    public String takeArg(){
        String result = sc.nextLine();
        result = result.toLowerCase();
        return result;
    }
    public void outConsole(String command) throws Exception {
        System.out.println(command);
    }
}
