package test.java.Bot;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import main.java.Bot.ApiFilm;
class ApiFilmTest {
    @Test
    void takeFilms() throws Exception {
        ApiFilm apiFilm = new ApiFilm();
        String result = apiFilm.takeFilms("жанр","123");
        assertNotNull(result);
    }
}