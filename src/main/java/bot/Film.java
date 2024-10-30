package bot;

import java.util.ArrayList;

public record Film(String name, String description, String rating, ArrayList<String> genres) {
    @Override
    public String toString(){
        StringBuilder genresBuilder = new StringBuilder();
        for (String genre:genres){
            genresBuilder.append(genre).append(" ");
        }
        return "Название: " + name + "\n" +
                "Описание: " + description + "\n" +
                "Рейтинг кинопоиска: " + rating + "\n"+
                "Жанры: " + genresBuilder+"\n";

    }
}