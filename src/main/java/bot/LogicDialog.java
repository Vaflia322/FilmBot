package bot;

import java.util.Queue;
import java.util.Set;

public class LogicDialog {
    private final ApiFilm apiFilm;
    private final Dialog dialog;
    public LogicDialog(ApiFilm apiFilm, Dialog dialog) {
        this.apiFilm = apiFilm;
        this.dialog = dialog;

    }

    private boolean exit(String command) {
        return !command.equals("стоп");
    }
    public void startDialog(String command) {

        CommandStorage commandStorage = new CommandStorage();
        dialog.print("Привет! Я фильм бот.");
        boolean isRunning = true;
        while (isRunning) {
            if (!commandStorage.isCommand(command)) {
                dialog.print(commandStorage.parsingSupportedCommand("-help"));
                command = dialog.takeArg();
                continue;
            }
            if (commandStorage.isSupportedCommand(command)) {
                dialog.print(commandStorage.parsingSupportedCommand(command));
                isRunning = exit(command);
            }
            if (commandStorage.isSupportedFilmsCommand(command)) {
                dialog.print(commandStorage.parsingSupportedFilmsCommand(command));
                String tellFilmCommand;
                if (!command.contains("случайный")) {
                    tellFilmCommand = dialog.takeArg();
                    if (tellFilmCommand.contains("список")) {
                        Set<String> genres = commandStorage.getGenres();
                        String stringGenres = String.join(", ", genres);
                        dialog.print(stringGenres);
                        tellFilmCommand = dialog.takeArg();
                    }
                } else {
                    tellFilmCommand = "случайный";
                }
                if (tellFilmCommand.equals("стоп") || tellFilmCommand.equals("-help")) {
                    dialog.print(commandStorage.parsingSupportedCommand(tellFilmCommand));
                    isRunning = exit(tellFilmCommand);
                } else {
                    ApiObject response = apiFilm.takeFilms(TypeOfFilmRequest.commandToEnum(command), tellFilmCommand);
                    switch (response) {
                        case Fault fault -> {
                            dialog.print(fault.getError());
                        }
                        case Movies movies -> {
                            Queue<Film> films = movies.getFilms();
                            StringBuilder result = new StringBuilder();
                            if (!command.equals("случайный")) {
                                if (films.isEmpty()) {
                                    dialog.print("Не нашлось фильмов с такими характеристиками");
                                } else {
                                    command = "еще";
                                    while (command.equals("еще")) {
                                        if (films.isEmpty()) {
                                            dialog.print("Фильмы кончились");
                                            break;
                                        }
                                        result.append(films.remove());
                                        result.append("Если вы хотите получить еще один фильм с такой характеристикой введите еще иначе введите хватит");
                                        dialog.print(result.toString());
                                        command = dialog.takeArg();
                                        result = new StringBuilder();
                                    }
                                    dialog.print("Жду дальнейших команд");
                                }
                            } else {
                                dialog.print(films.remove().toString());
                            }
                        }
                        default -> dialog.print("Неизвестная ошибка");
                    }
                }
            }
            if (!isRunning) {
                continue;
            }
            command = dialog.takeArg();
        }
    }
}


