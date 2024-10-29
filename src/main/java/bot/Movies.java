package bot;

import java.util.*;

public final class Movies extends ApiObject{
    private final Queue<Record> records = new LinkedList<>();
    public void addFilm(Record record){
        records.add(record);
    }

    public Queue<Record> getFilms() {
        return records;
    }
}
