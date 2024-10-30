package bot;

import java.util.Queue;
import java.util.Set;

public class LogicDialog {
    private final ApiFilm apiFilm;
    private final Dialog workWithConsole;
    public LogicDialog(ApiFilm apiFilm, Dialog workWithConsole) {
        this.apiFilm = apiFilm;
        this.workWithConsole = workWithConsole;

    }

    private boolean exit(String command) {
        return !command.equals("стоп");
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
                    ApiObject response = apiFilm.takeFilms(TypeOfFilmRequest.commandToEnum(command), tellFilmCommand);
                    switch (response) {
                        case Fault fault -> {
                            workWithConsole.print(fault.getError());
                        }
                        case Movies movies -> {
                            Queue<Film> films = movies.getFilms();
                            StringBuilder result = new StringBuilder();
                            if (!command.equals("случайный")) {
                                if (films.isEmpty()) {
                                    workWithConsole.print("Не нашлось фильмов с такими характеристиками");
                                } else {
                                    command = "еще";
                                    while (command.equals("еще")) {
                                        if (films.isEmpty()) {
                                            workWithConsole.print("Фильмы кончились");
                                            break;
                                        }
                                        result.append(films.remove());
                                        result.append("Если вы хотите получить еще один фильм с такой характеристикой введите еще иначе введите хватит");
                                        workWithConsole.print(result.toString());
                                        command = workWithConsole.takeArg();
                                        result = new StringBuilder();
                                    }
                                    workWithConsole.print("Жду дальнейших команд");
                                }
                            } else {
                                workWithConsole.print(films.remove().toString());
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


