package bot;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public sealed abstract class ApiObject permits Films,Error {
    public abstract Map<Integer,String> fetchData();
}
final class Films extends ApiObject{
    private final Map<Integer,String> films = new HashMap<>();
    public void addFilm(Integer numberOfFilm,String name, String description,String rating){
        Film film = new Film(name,description,rating);
        films.put(numberOfFilm,film.getStringFilm());
    }

    @Override
    public Map<Integer,String> fetchData() {
        return films;
    }

    public static class Film{
        private String name;
        private String description;
        private String rating;

        public Film(String name, String description, String rating) {
            this.name = name;
            this.description = description;
            this.rating = rating;
        }

        public String getStringFilm(){
            StringBuilder result = new StringBuilder().append("Название: ").append(name).append("\n");
            result.append("Описание: ").append(description).append("\n");
            result.append("Рейтинг кинопоиска: ").append(rating).append("\n");
            return result.toString();
        }
    }
}
final class Error extends ApiObject{
    private String error;
    private final Map<Integer,String> errors = new HashMap<>();
    public void setError(String error) {
        this.error = error;
    }
    @Override
    public Map<Integer,String> fetchData(){
        errors.put(-1,error);
        return errors;
    }
}
