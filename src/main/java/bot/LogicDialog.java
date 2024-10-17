package bot;

import java.util.ArrayList;
import java.util.Set;

public class LogicDialog {
    private final ApiFilm apiFilm;
    private final ConsoleInterface workWithConsole;

    public LogicDialog(ApiFilm apiFilm, ConsoleInterface workWithConsole) {
        this.apiFilm = apiFilm;
        this.workWithConsole = workWithConsole;

    }

    private boolean exit(String command) {
        return !command.equals("стоп");
    }
    private TypeOfFilm commandToEnum(String command){
        switch(command){
            case("жанр"):
                return TypeOfFilm.GENRE;
            case("рейтинг"):
                return TypeOfFilm.RATING;
            case("случайный"):
                return TypeOfFilm.RANDOM;
            case("год"):
                return TypeOfFilm.YEAR;
            case("название"):
                return TypeOfFilm.NAME;
            default:
                throw new IllegalStateException("Unexpected value: " + command);
        }
    }
    public void startDialog(String command) {

        CommandStorage commandStorage = new CommandStorage();
        workWithConsole.print("Привет! Я фильм бот.");
        boolean isRunning = true;
        while (isRunning) {
            if (!commandStorage.isCommand(command)) {
                workWithConsole.print(commandStorage.parsingSupportedCommand("-help"));
                command = workWithConsole.takeArg();
                continue;
            }
            if (commandStorage.isSupportedCommand(command)) {
                workWithConsole.print(commandStorage.parsingSupportedCommand(command));
                isRunning = exit(command);
            }
            if (commandStorage.isSupportedFilmsCommand(command)) {
                workWithConsole.print(commandStorage.parsingSupportedFilmsCommand(command));
                String tellFilmCommand;
                if (!command.contains("случайный")) {
                    tellFilmCommand = workWithConsole.takeArg();
                    if (tellFilmCommand.contains("список")) {
                        Set<String> genres = commandStorage.getGenres();
                        String stringGenres = String.join(", ", genres);
                        workWithConsole.print(stringGenres);
                        tellFilmCommand = workWithConsole.takeArg();
                    }
                } else {
                    tellFilmCommand = "случайный";
                }
                if (tellFilmCommand.equals("стоп") || tellFilmCommand.equals("-help")) {
                    workWithConsole.print(commandStorage.parsingSupportedCommand(tellFilmCommand));
                    isRunning = exit(tellFilmCommand);
                } else {
                    var response = apiFilm.takeFilms(commandToEnum(command), tellFilmCommand);
                    switch (response) {
                        case Fault fault -> {
                            workWithConsole.print(fault.getError());
                        }
                        case Movies movies -> {
                            ArrayList<String> films = movies.getFilms();
                            StringBuilder result = new StringBuilder();
                            if (!command.equals("случайный")) {
                                if (films.isEmpty()) {
                                    workWithConsole.print("Не нашлось фильмов с такими характеристиками");
                                } else {
                                    int outputMovieNumber = 0;
                                    final int totalFilms = films.size();
                                    command = "еще";
                                    while (command.equals("еще")) {
                                        if (outputMovieNumber > totalFilms) {
                                            result.append("Фильмы кончились");
                                            workWithConsole.print(result.toString());
                                            break;
                                        }
                                        result.append(films.get(outputMovieNumber));
                                        result.append("Если вы хотите получить еще один фильм с такой характеристикой введите еще иначе введите хватит");
                                        workWithConsole.print(result.toString());
                                        command = workWithConsole.takeArg();
                                        outputMovieNumber++;
                                        result = new StringBuilder();
                                    }
                                    workWithConsole.print("Жду дальнейших команд");
                                }
                            } else {
                                result.append(films.getFirst());
                                workWithConsole.print(result.toString());
                            }
                        }
                        default -> workWithConsole.print("Неизвестная ошибка");
                    }
                }
            }
            if (!isRunning) {
                continue;
            }
            command = workWithConsole.takeArg();
        }
    }
}


