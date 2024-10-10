package bot;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ApiFilmTest {
    @Test
    void takeFilms1() throws Exception {
        ApiFilm apiFilm = new ApiFilm();
        String result = apiFilm.takeFilms("жанр","123");
        assertEquals(result,"Вы ввели некорректный жанр");
    }
    @Test
    void takeFilms2() throws Exception {
        ApiFilm apiFilm = new ApiFilm();
        String result = apiFilm.takeFilms("жанр","123");
        assertEquals(result,"Вот ваш фильм");
    }
}