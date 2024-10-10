package bot;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RequestValidationTest {

    @Test
    void isGenreExists1() {
        RequestValidation requestValidation = new RequestValidation();
        boolean result = requestValidation.isGenreExists("драма");
        assertTrue(result);
    }

    @Test
    void isNameExists1() {
        RequestValidation requestValidation = new RequestValidation();
        boolean result = requestValidation.isNameExists("Гадкий я");
        assertTrue(result);
    }

    @Test
    void isRatingExists1() {
        RequestValidation requestValidation = new RequestValidation();
        boolean result = requestValidation.isRatingExists("8");
        assertTrue(result);
    }

    @Test
    void isYearExists1() {
        RequestValidation requestValidation = new RequestValidation();
        boolean result = requestValidation.isYearExists("2020-2021");
        assertTrue(result);
    }
    @Test
    void isGenreExists2() {
        RequestValidation requestValidation = new RequestValidation();
        boolean result = requestValidation.isGenreExists("любой");
        assertTrue(result);
    }

    @Test
    void isNameExists2() {
        RequestValidation requestValidation = new RequestValidation();
        boolean result = requestValidation.isNameExists("Shrek");
        assertTrue(result);
    }

    @Test
    void isRatingExists2() {
        RequestValidation requestValidation = new RequestValidation();
        boolean result = requestValidation.isRatingExists("52");
        assertTrue(result);
    }

    @Test
    void isYearExists2() {
        RequestValidation requestValidation = new RequestValidation();
        boolean result = requestValidation.isYearExists("2020-202123");
        assertTrue(result);
    }
}