package bot;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ApiFilmTest {
    private final ApiFilm apiFilm = new ApiFilm();
    @Test
    void correctGenreResultTrue() {
        Movies response = (Movies) apiFilm.takeFilms(TypeOfFilm.GENRE,"драма");
        ArrayList<String> result = response.getFilms();
        assertFalse(result.isEmpty());
    }
    @Test
    void incorrectGenreResultNotEquals() {
        Fault result = (Fault) apiFilm.takeFilms(TypeOfFilm.GENRE,"123");
        assertNotEquals(result.getError(),"Вот ваш фильм");
    }
}