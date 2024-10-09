package bot;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.io.BufferedReader;
public class CommandStorage {
    private final HashSet<String> genres = new HashSet<String>();

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

    public HashSet<String> getGenres() {
        return genres;
    }
    private final String[] parsingCommand = {"-help", "подскажи фильм", "стоп"};
    private final String[] tellFilmArgCommand = {"название", "жанр", "год", "рейтинг", "случайный"};

    public boolean isMyCommandFullStorage(String command){
        return isMyCommandParsing(command) || isMyCommandTellFilmArg(command);
    }
    public boolean isMyCommandParsing(String command) {
        for (int i = 0;i<3;i++){
            if (parsingCommand[i].equals(command)){
                return true;
            }
        }
        return false;
    }
    public boolean isMyCommandTellFilmArg(String command){
        for (int i = 0;i<5;i++){
            if (tellFilmArgCommand[i].equals(command)){
                return true;
            }
        }
        return false;
    }
    public String parsing(String command){
        switch (command) {
            case ("-help"):
                return "Для получения справки введите -help, если вам нужно подсказать фильм введите подскажи фильм\n" +
                        "Чтобы прекратить работу бота введите стоп";
            case ("подскажи фильм"):
                return "Я могу подсказать фильм по названию, жанру, рейтингу, году или же выдать случайный фильм\n" +
                        "Для того чтобы я мог это сделать введите слова название, жанр, рейтинг, год или же случайный";
            case ("стоп"):
                return "Обращайтесь еще!";
            default:
                return "Для получения справки введите -help, если вам нужно подсказать фильм введите подскажи фильм\n" +
                        "Чтобы прекратить работу бота введите стоп";
        }
    }
    public String parsingFilms(String command) {
        switch (command) {
            case ("название"):
                return "Введите название фильма на русском языке";
            case ("жанр"):
                return "Введите жанр фильма, либо введите список для получения всех жанров";
            case("рейтинг"):
                return "Введите рейтинг, либо диапазон рейтинга, например 7, 7.2-8.3";
            case("год"):
                return "Введите год, либо диапазон, напрмер 2018,2020-2023";
            case("случайный"):
                return "Ваш случайный фильм";
            default:
                return "Вы ввели некорректную характеристику";

        }

    }
}