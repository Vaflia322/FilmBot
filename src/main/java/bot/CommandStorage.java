package bot;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
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

    private final Map<String, String> supportedCommand = new HashMap<>(Map.of(
            "/help", "Для получения справки введите /help, если вам нужно подсказать фильм введите подскажи фильм\n"
                    + "Для того чтобы вывести список фильмов которые вы добавили в черный список введите"
                    + " черный список\n"
                    + "Для того чтобы вывести список просмотренных фильмов введите просмотренные\n"
                    + "Для того чтобы вывести список фильмов желаемых к просмотру введите желаемые к просмотру\n"
                    + "Для того чтобы добавить просмотренный филь введите добавить просмотренный"
                    + " \"название фильма\"\n"
                    + "Чтобы прекратить работу бота введите стоп",
            "подскажи фильм", "Я могу подсказать фильм по названию, жанру, рейтингу, году или же выдать случайный фильм\n"
                    + "Для того чтобы я мог это сделать введите слова название, жанр, рейтинг, "
                    + "год или же случайный",
            "стоп", "Обращайтесь еще!"
    ));
    private final Map<String, String> supportedFilmsCommand = new HashMap<>(Map.of(
            "название", "Введите название фильма на русском языке",
            "жанр", "Введите жанр фильма, либо введите список для получения всех жанров",
            "год", "Введите год, либо диапазон, напрмер 2018,2020-2023",
            "рейтинг", "Введите рейтинг, либо диапазон рейтинга, например 7, 7.2-8.3",
            "случайный", "Ваш случайный фильм"
    ));


    public boolean isCommand(String command) {
        return isSupportedCommand(command) || isSupportedFilmsCommand(command);
    }

    public boolean isSupportedCommand(String command) {
        return supportedCommand.containsKey(command);
    }

    public boolean isSupportedFilmsCommand(String command) {
        return supportedFilmsCommand.containsKey(command);
    }

    public String parsingSupportedCommand(String command) {
        if (isSupportedCommand(command)) {
            return supportedCommand.get(command);
        }
        return "Для получения справки введите /help";
    }

    public String parsingSupportedFilmsCommand(String command) {
        if (isSupportedFilmsCommand(command)) {
            return supportedFilmsCommand.get(command);
        }
        return "Вы ввели некорректную характеристику";
    }
}