package bot;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


class RequestValidationTest {
    private final RequestValidation requestValidation = new RequestValidation();

    @Test
    void genreValidation() {
        boolean result = requestValidation.isGenreExists("драма");
        assertTrue(result);
    }

    @Test
    void nameValidation() {
        boolean result = requestValidation.isNameExists("Гадкий я");
        assertTrue(result);
    }

    @Test
    void ratingValidation() {
        boolean result = requestValidation.isRatingExists("8");
        assertTrue(result);
    }

    @Test
    void yearValidation() {
        boolean result = requestValidation.isYearExists("2020-2021");
        assertTrue(result);
    }

    @Test
    void genreValidationFailed() {
        boolean result = requestValidation.isGenreExists("любой");
        assertFalse(result);
    }

    @Test
    void nameValidationFailed() {
        boolean result = requestValidation.isNameExists("Shrek");
        assertFalse(result);
    }

    @Test
    void ratingValidationFailed() {
        boolean result = requestValidation.isRatingExists("52");
        assertFalse(result);
    }

    @Test
    void yearValidationFailed() {
        boolean result = requestValidation.isYearExists("2020-202123");
        assertFalse(result);
    }
}