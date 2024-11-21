package bot;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class CommandStorage {
    private final Set<String> genres = new HashSet<>();

    {
        BufferedReader genresReader;
        try {
            genresReader = new BufferedReader(new FileReader("genres.txt"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            String line = genresReader.readLine();
            while (line != null) {
                genres.add(line);
                line = genresReader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Set<String> getGenres() {
        return genres;
    }

    private final Set<String> supportedCommand = new HashSet<>(Set.of("-help", "подскажи фильм", "стоп"));
    private final Set<String> supportedFilmsCommand = new HashSet<>(Set.of("название", "жанр", "год", "рейтинг",
            "случайный"));

    public boolean isCommand(String command) {
        return isSupportedCommand(command) || isSupportedFilmsCommand(command);
    }

    public boolean isSupportedCommand(String command) {
        return supportedCommand.contains(command);
    }

    public boolean isSupportedFilmsCommand(String command) {
        return supportedFilmsCommand.contains(command);
    }

    public String parsingSupportedCommand(String command) {
        switch (command) {
            case ("-help"):
                return "Для получения справки введите -help, если вам нужно подсказать фильм введите подскажи фильм\n"
                        + "Чтобы прекратить работу бота введите стоп";
            case ("подскажи фильм"):
                return "Я могу подсказать фильм по названию, жанру, рейтингу, году или же выдать случайный фильм\n"
                        + "Для того чтобы я мог это сделать введите слова название, жанр, рейтинг, "
                        + "год или же случайный";
            case ("стоп"):
                return "Обращайтесь еще!";
            default:
                return "Для получения справки введите -help, если вам нужно подсказать фильм введите подскажи фильм\n"
                        + "Чтобы прекратить работу бота введите стоп";
        }
    }

    public String parsingSupportedFilmsCommand(String command) {
        switch (command) {
            case ("название"):
                return "Введите название фильма на русском языке";
            case ("жанр"):
                return "Введите жанр фильма, либо введите список для получения всех жанров";
            case ("рейтинг"):
                return "Введите рейтинг, либо диапазон рейтинга, например 7, 7.2-8.3";
            case ("год"):
                return "Введите год, либо диапазон, напрмер 2018,2020-2023";
            case ("случайный"):
                return "Ваш случайный фильм";
            default:
                return "Вы ввели некорректную характеристику";

        }

    }
}