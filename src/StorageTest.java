import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StorageTest {

    @Test
    void parsing() throws Exception {
        Storage parser = new Storage();
        String result = parser.parsing("-help");
        assertEquals("Для получения справки введите -help, если вам нужно подсказать фильм введите подскажи фильм\n" +
                "Чтобы прекратить работу бота введите стоп",result);
    }

    @Test
    void tellFilmArg() throws Exception {
        Storage tellFilm = new Storage();
        String result = tellFilm.tellFilmArg("жанр");
        assertEquals("Введите жанр фильма",result);
    }
}