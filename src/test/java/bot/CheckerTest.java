package bot;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CheckerTest {

    @Test
    void checkForGenre() {
        Checker checker = new Checker();
        boolean result = checker.checkForGenre("драма");
        assertEquals(true,result);
    }

    @Test
    void checkForName() {
        Checker checker = new Checker();
        boolean result = checker.checkForName("Гадкий я");
        assertEquals(true,result);
    }

    @Test
    void checkForRating() {
        Checker checker = new Checker();
        boolean result = checker.checkForRating("8");
        assertEquals(true,result);
    }

    @Test
    void checkForYear() {
        Checker checker = new Checker();
        boolean result = checker.checkForYear("2020-2021");
        assertEquals(true,result);
    }
}