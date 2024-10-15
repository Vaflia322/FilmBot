package bot;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class CommandStorageTest {

    @Test
    void commandHelpResultEquals() {
        CommandStorage commandStorage = new CommandStorage();
        String result = commandStorage.parsingSupportedCommand("-help");;
        assertEquals("Для получения справки введите -help, если вам нужно подсказать фильм введите подскажи фильм\n" +
                "Чтобы прекратить работу бота введите стоп",result);
    }
    @Test
    void commandTellFilmResultNotEquals() {
        CommandStorage commandStorage = new CommandStorage();
        String result = commandStorage.parsingSupportedCommand("подскажи фильм");
        assertNotEquals("не подскажу",result);
    }

    @Test
    void parsingSupportedCommandFilmsInputGenre(){
        CommandStorage commandStorage = new CommandStorage();
        String result = commandStorage.parsingSupportedFilmsCommand("жанр");
        assertNotEquals("Введите жанр фильма",result);
    }
    @Test
    void parsingSupportedCommandFilmsInputRating(){
        CommandStorage commandStorage = new CommandStorage();
        String result = commandStorage.parsingSupportedFilmsCommand("рейтинг");
        assertEquals("Введите рейтинг, либо диапазон рейтинга, например 7, 7.2-8.3",result);
    }
}