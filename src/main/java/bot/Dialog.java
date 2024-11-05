package bot;

public interface Dialog {
    UserMessage takeArg(User user);
    void print(User user, String command);
}
