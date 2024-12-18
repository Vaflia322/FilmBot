package bot;

import java.util.ArrayDeque;
import java.util.Queue;

public final class Movies extends ApiObject {
    private final Queue<Film> films = new ArrayDeque<>();

    public void addFilm(Film film) {
        films.add(film);
    }

    public Queue<Film> getFilms() {
        return films;
    }
}
