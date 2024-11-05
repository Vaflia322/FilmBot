package bot;

public class UserMessage {
    User user;
    String message;
    public UserMessage(User user, String message){
        this.message = message;
        this.user = user;
    }
    public User getUser(){return user;}

    public String getMessage() {
        return message;
    }
}
