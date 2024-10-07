package bot;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RequestAuthenticationTest {

    @Test
    void isGenreExists() {
        RequestAuthentication requestAuthentication = new RequestAuthentication();
        boolean result = requestAuthentication.isGenreExists("драма");
        assertEquals(true,result);
    }

    @Test
    void isNameExists() {
        RequestAuthentication requestAuthentication = new RequestAuthentication();
        boolean result = requestAuthentication.isNameExists("Гадкий я");
        assertEquals(true,result);
    }

    @Test
    void isRatingExists() {
        RequestAuthentication requestAuthentication = new RequestAuthentication();
        boolean result = requestAuthentication.isRatingExists("8");
        assertEquals(true,result);
    }

    @Test
    void isYearExists() {
        RequestAuthentication requestAuthentication = new RequestAuthentication();
        boolean result = requestAuthentication.isYearExists("2020-2021");
        assertEquals(true,result);
    }
}