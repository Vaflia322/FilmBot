package bot;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ApiFilmTest {
    @Test
    void takeFilms1() {
        ApiFilm apiFilm = new ApiFilm();
        Object result = apiFilm.takeFilms("жанр","123");
        assertEquals(result.toString(),"Вы ввели некорректный жанр");
    }
    @Test
    void takeFilms2() {
        ApiFilm apiFilm = new ApiFilm();
        Object result = apiFilm.takeFilms("жанр","123");
        assertEquals(result.toString(),"Вот ваш фильм");
    }
}