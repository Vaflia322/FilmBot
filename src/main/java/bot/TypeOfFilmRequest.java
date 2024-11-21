package bot;

import java.util.Arrays;

public enum TypeOfFilmRequest {
    NAME("название"),
    GENRE("жанр"),
    RATING("рейтинг"),
    YEAR("год"),
    RANDOM("случайный");
    private final String russianName;

    TypeOfFilmRequest(String russianName) {
        this.russianName = russianName;
    }

    public static TypeOfFilmRequest commandToEnum(String command) {
        return Arrays.stream(TypeOfFilmRequest.values())
                .filter(type -> type.getRussianName().equalsIgnoreCase(command))
                .findFirst()
                .orElse(null);
    }

    private String getRussianName() {
        return russianName;
    }
}
