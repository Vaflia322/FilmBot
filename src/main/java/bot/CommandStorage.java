package bot;
public class CommandStorage {
    private String[] genres = {
            "аниме",
            "биография",
            "боевик",
            "вестерн",
            "военный",
            "детектив",
            "детский",
            "для взрослых",
            "документальный",
            "драма",
            "игра",
            "история",
            "комедия",
            "концерт",
            "короткометражка",
            "криминал",
            "мелодрама",
            "музыка",
            "мультфильм",
            "мюзикл",
            "новости",
            "приключения",
            "реальное ТВ",
            "семейный",
            "спорт",
            "ток-шоу",
            "триллер",
            "ужасы",
            "фантастика",
            "фильм-нуар",
            "фэнтези",
            "церемония"
    };

    public String[] getStringGenres() {
        return genres;
    }
    private final String parsingCommand = "-help подскажи фильм стоп";
    private final String tellFilmArgCommand = "название жанр год рейтинг случайный";
    private final String fullCommand =parsingCommand + tellFilmArgCommand;

    public boolean isMyCommandFullStorage(String command){
        return (fullCommand).contains(command);
    }
    public boolean isMyCommandParsing(String command) {
        return parsingCommand.contains(command);
    }
    public boolean isMyCommandTellFilmArg(String command){
        return tellFilmArgCommand.contains(command);
    }
    public String parsing(String command) throws Exception {
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
    public String tellFilmArg(String command) throws Exception {
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