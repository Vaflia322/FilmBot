package bot;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RequestAuthenticationTest {

    @Test
    void isGenreExists1() {
        RequestAuthentication requestAuthentication = new RequestAuthentication();
        boolean result = requestAuthentication.isGenreExists("драма");
        assertEquals(true,result);
    }

    @Test
    void isNameExists1() {
        RequestAuthentication requestAuthentication = new RequestAuthentication();
        boolean result = requestAuthentication.isNameExists("Гадкий я");
        assertEquals(true,result);
    }

    @Test
    void isRatingExists1() {
        RequestAuthentication requestAuthentication = new RequestAuthentication();
        boolean result = requestAuthentication.isRatingExists("8");
        assertEquals(true,result);
    }

    @Test
    void isYearExists1() {
        RequestAuthentication requestAuthentication = new RequestAuthentication();
        boolean result = requestAuthentication.isYearExists("2020-2021");
        assertEquals(true,result);
    }
    @Test
    void isGenreExists2() {
        RequestAuthentication requestAuthentication = new RequestAuthentication();
        boolean result = requestAuthentication.isGenreExists("любой");
        assertEquals(true,result);
    }

    @Test
    void isNameExists2() {
        RequestAuthentication requestAuthentication = new RequestAuthentication();
        boolean result = requestAuthentication.isNameExists("Shrek");
        assertEquals(true,result);
    }

    @Test
    void isRatingExists2() {
        RequestAuthentication requestAuthentication = new RequestAuthentication();
        boolean result = requestAuthentication.isRatingExists("52");
        assertEquals(true,result);
    }

    @Test
    void isYearExists2() {
        RequestAuthentication requestAuthentication = new RequestAuthentication();
        boolean result = requestAuthentication.isYearExists("2020-202123");
        assertEquals(true,result);
    }
}