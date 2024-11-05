package bot;

import java.util.ArrayList;
import java.util.List;

public record Film(String name, String description, String rating, List<String> genres) {
    @Override
    public String toString(){
        String genresString = String.join(" ",genres);
        return "Название: " + name + "\n" +
                "Описание: " + description + "\n" +
                "Рейтинг кинопоиска: " + rating + "\n"+
                "Жанры: " + genresString+"\n";
    }
}