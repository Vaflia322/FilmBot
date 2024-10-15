package bot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

class ApiFilmTest {
    @Test
    void incorrectGenreResultEquals() {
        ApiFilm apiFilm = new ApiFilm();
        Object result = apiFilm.takeFilms(Type.valueOf("жанр"),"123");
        assertEquals(result.toString(),"Вы ввели некорректный жанр");
    }
    @Test
    void incorrectGenreResultNotEquals() {
        ApiFilm apiFilm = new ApiFilm();
        Object result = apiFilm.takeFilms(Type.valueOf("жанр"),"123");
        assertNotEquals(result.toString(),"Вот ваш фильм");
    }
}