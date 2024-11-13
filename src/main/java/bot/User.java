package bot;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

public class User {
    private final long userID;
    private final Map<String,String> apiRequest;
    private Queue<Film> films;
    public User(long userID){
        this.userID = userID;
        apiRequest = new HashMap<>();
    }

    public long getUserID() {
        return userID;
    }

    public Map<String, String> getApiRequest() {
        return apiRequest;
    }
    public void cleanMap(){
        apiRequest.remove("characteristicType");
        apiRequest.remove("request");
    }

    public void setApiRequest(String key,String value) {
        apiRequest.put(key,value);
    }
    public Queue<Film> getFilms(){
        return films;
    }

    public void setFilms(Queue<Film> films) {
        this.films = films;
    }
}
