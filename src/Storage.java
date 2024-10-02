public class Storage {
    private String parsingCommand = "-help подскажи фильм стоп";
    private String tellFilmArgCommand = "название жанр список год рейтинг случайный";
    private String fullStorage = parsingCommand+tellFilmArgCommand;
    public String getFullStorage(){
        return fullStorage;
    }
    public String getParsingCommand() {
        return parsingCommand;
    }
    public String getTellFilmArgCommand(){
        return tellFilmArgCommand;
    }
    public String parsing(String command) throws Exception {
        switch (command) {
            case ("-help"):
                return "Для получения справки введите -help, если вам нужно подсказать фильм введите подскажи фильм\n" +
                        "Чтобы прекратить работу бота введите стоп";
            case ("подскажи фильм"):
                return "Я могу подсказать фильм по названию, жанру, рейтингу, году или же выдать случайный фильм\n" +
                        "Для того чтобы я мог это сделать введите слова название, жанр, рейтинг, год или же случайный\n" +
                        "А также вы можете ввести список и получите список всех жанров";
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
                return "Введите жанр фильма";
            case("список"):
                Checker caller = new Checker();
                return caller.getStringGenres() + "\n Введите жанр из этого списка";
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