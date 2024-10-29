package bot;

import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.*;

class ApiFilmTest {
    private final ApiFilm apiFilm = new ApiFilm();
    @Test
    void correctGenreResultTrue() {
        Movies response = (Movies) apiFilm.takeFilms(TypeOfFilmRequest.GENRE,"драма");
        Queue<Record> result = response.getFilms();
        Record record = result.remove();
        String film = record.toString();
        assertTrue(film.contains("драма"));
    }
    @Test
    void incorrectGenreResultNotEquals() {
        Fault result = (Fault) apiFilm.takeFilms(TypeOfFilmRequest.GENRE,"123");
        assertEquals(result.getError(),"Вы ввели некорректный жанр");
    }
}