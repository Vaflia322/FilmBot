package bot;

import java.util.*;

public final class Movies extends ApiObject{
    private final ArrayDeque<Film> films = new ArrayDeque<>();
    public void addFilm(Film film){
        films.addFirst(film);
    }

    public ArrayDeque<Film> getFilms() {
        return films;
    }
}
