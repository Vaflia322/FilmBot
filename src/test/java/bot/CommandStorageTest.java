package bot;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CommandStorageTest {

    @Test
    void parsing1() {
        CommandStorage parser = new CommandStorage();
        String result = parser.parsing("-help");;
        assertEquals("Для получения справки введите -help, если вам нужно подсказать фильм введите подскажи фильм\n" +
                "Чтобы прекратить работу бота введите стоп",result);
    }
    @Test
    void parsing2() {
        CommandStorage parser = new CommandStorage();
        String result = parser.parsing("подскажи фильм");
        assertEquals("не подскажу",result);
    }

    @Test
    void parsingFilms(){
        CommandStorage tellFilm = new CommandStorage();
        String result = tellFilm.parsingFilms("жанр");
        assertEquals("Введите жанр фильма",result);
    }
    @Test
    void parsingFilms1(){
        CommandStorage tellFilm = new CommandStorage();
        String result = tellFilm.parsingFilms("рейтинг");
        assertEquals("Введите рейтинг, либо диапазон рейтинга, например 7, 7.2-8.3",result);
    }
}