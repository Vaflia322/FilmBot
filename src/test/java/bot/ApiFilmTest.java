package bot;

import org.junit.jupiter.api.Test;


import java.util.List;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.*;

class ApiFilmTest {
    private final ApiFilm apiFilm = new ApiFilm();
    @Test
    void correctGenreResultTrue() {
        Movies response = (Movies) apiFilm.takeFilms(TypeOfFilmRequest.GENRE,"драма");
        Queue<Film> result = response.getFilms();
        Film film = result.remove();
        List<String> genres = film.genres();
        assertTrue(genres.contains("драма"));
    }
    @Test
    void incorrectGenreErrorEquals() {
        Fault result = (Fault) apiFilm.takeFilms(TypeOfFilmRequest.GENRE,"123");
        assertEquals(result.getError(),"Вы ввели некорректный жанр");
    }
}