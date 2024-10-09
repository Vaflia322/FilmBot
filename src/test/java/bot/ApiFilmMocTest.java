package bot;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class ApiFilmMocTest {

    @Test
    void takeFilms1() throws Exception {
        ApiFilm apiFilmMoc = mock(ApiFilm.class);
        given(apiFilmMoc.takeFilms("жанр", "123")).willReturn("Вы ввели некорректный жанр, пожалуйста выберите жанр из списка предложенных\n" +
                "Чтобы его получить введите СПИСОК");
        String result = apiFilmMoc.takeFilms("жанр", "123");

        assertEquals("Вы ввели некорректный жанр, пожалуйста выберите жанр из списка предложенных\n" +
                "Чтобы его получить введите СПИСОК",result);

    }
    @Test
    void takeFilms2() throws Exception {
        ApiFilm apiFilmMoc = mock(ApiFilm.class);
        given(apiFilmMoc.takeFilms("жанр", "драма")).willReturn("список фильмов");
        String result = apiFilmMoc.takeFilms("жанр", "драма");

        assertEquals("список фильмов",result);

    }
    @Test
    void takeFilms3() throws Exception {
        ApiFilm apiFilmMoc = mock(ApiFilm.class);
        given(apiFilmMoc.takeFilms("жанр", "драма")).willReturn("список фильмов");
        String result = apiFilmMoc.takeFilms("жанр", "драма");

        assertEquals("фильмов нет",result);

    }
}