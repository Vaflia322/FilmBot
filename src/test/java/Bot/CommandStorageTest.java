package test.java.Bot;
import org.junit.jupiter.api.Test;
import main.java.Bot.CommandStorage;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CommandStorageTest {

    @Test
    void parsing() throws Exception {
        CommandStorage parser = new CommandStorage();
        String result = parser.parsing("-help");
        assertEquals("Для получения справки введите -help, если вам нужно подсказать фильм введите подскажи фильм\n" +
                "Чтобы прекратить работу бота введите стоп",result);
    }

    @Test
    void tellFilmArg() throws Exception {
        CommandStorage tellFilm = new CommandStorage();
        String result = tellFilm.tellFilmArg("жанр");
        assertEquals("Введите жанр фильма",result);
    }
}