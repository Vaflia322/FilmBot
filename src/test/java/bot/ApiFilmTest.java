package bot;

import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ApiFilmTest {
    private final ApiFilm apiFilm = new ApiFilm();
    @Test
    void correctGenreResultTrue() {
        Movies response = (Movies) apiFilm.takeFilms(TypeOfFilmRequest.GENRE,"драма");
        ArrayDeque<Film> result = response.getFilms();
        assertFalse(result.pop().toString().isEmpty());
    }
    @Test
    void incorrectGenreResultNotEquals() {
        Fault result = (Fault) apiFilm.takeFilms(TypeOfFilmRequest.GENRE,"123");
        assertNotEquals(result.getError(),"Вот ваш фильм");
    }
}