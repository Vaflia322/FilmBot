package bot;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ApiFilmTest {
    private final ApiFilm apiFilm = new ApiFilm();
    @Test
    void incorrectGenreResultEquals() {
        var response = apiFilm.takeFilms(Type.жанр,"драма");
        Map<Integer,String> data = response.fetchData();
        assertTrue(data.containsKey(1));
    }
    @Test
    void incorrectGenreResultNotEquals() {
        Object result = apiFilm.takeFilms(Type.жанр,"123");
        assertNotEquals(result.toString(),"Вот ваш фильм");
    }
}