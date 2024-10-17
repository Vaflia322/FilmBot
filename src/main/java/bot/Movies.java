package bot;

import java.util.ArrayList;

public final class Movies extends ApiObject{
    private final ArrayList<String> films = new ArrayList<>();
    public void addFilm(String name, String description,String rating){
        Film film = new Film(name,description,rating);
        films.add(film.getStringFilm());
    }

    public ArrayList<String> getFilms() {
        return films;
    }
}
