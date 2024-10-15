package bot;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RequestValidationTest {

    @Test
    void genreValidation() {
        RequestValidation requestValidation = new RequestValidation();
        boolean result = requestValidation.isGenreExists("драма");
        assertTrue(result);
    }

    @Test
    void nameValidation() {
        RequestValidation requestValidation = new RequestValidation();
        boolean result = requestValidation.isNameExists("Гадкий я");
        assertTrue(result);
    }

    @Test
    void ratingValidation() {
        RequestValidation requestValidation = new RequestValidation();
        boolean result = requestValidation.isRatingExists("8");
        assertTrue(result);
    }

    @Test
    void yearValidation() {
        RequestValidation requestValidation = new RequestValidation();
        boolean result = requestValidation.isYearExists("2020-2021");
        assertTrue(result);
    }
    @Test
    void genreValidationFailed() {
        RequestValidation requestValidation = new RequestValidation();
        boolean result = requestValidation.isGenreExists("любой");
        assertFalse(result);
    }

    @Test
    void nameValidationFailed() {
        RequestValidation requestValidation = new RequestValidation();
        boolean result = requestValidation.isNameExists("Shrek");
        assertFalse(result);
    }

    @Test
    void ratingValidationFailed() {
        RequestValidation requestValidation = new RequestValidation();
        boolean result = requestValidation.isRatingExists("52");
        assertFalse(result);
    }

    @Test
    void yearValidationFailed() {
        RequestValidation requestValidation = new RequestValidation();
        boolean result = requestValidation.isYearExists("2020-202123");
        assertFalse(result);
    }
}