package bot;

import java.util.Queue;
import java.util.Set;

public class LogicDialog {
    private final ApiFilm apiFilm;
    private final Dialog dialog;
    private boolean isRunning = true;

    public LogicDialog(ApiFilm apiFilm, Dialog dialog) {
        this.apiFilm = apiFilm;
        this.dialog = dialog;
    }

    public boolean getIsRunning() {
        return isRunning;
    }

    private boolean exit(String command) {
        return !command.equals("стоп");
    }

    public void startDialog(User user) {
        CommandStorage commandStorage = new CommandStorage();
        UserMessage userMessage = dialog.takeArg(user);
        String command = userMessage.getMessage();
        dialog.print(user, "Привет! Я фильм бот.");
        while (isRunning) {
            if (!commandStorage.isCommand(command)) {
                dialog.print(user, commandStorage.parsingSupportedCommand("-help"));
                command = dialog.takeArg(user).getMessage();
                continue;
            }
            if (commandStorage.isSupportedCommand(command)) {
                dialog.print(user, commandStorage.parsingSupportedCommand(command));
                isRunning = exit(command);
            }
            if (commandStorage.isSupportedFilmsCommand(command)) {
                dialog.print(user, commandStorage.parsingSupportedFilmsCommand(command));
                String tellFilmCommand;
                if (!command.contains("случайный")) {
                    tellFilmCommand = dialog.takeArg(user).getMessage();
                    if (tellFilmCommand.contains("список")) {
                        Set<String> genres = commandStorage.getGenres();
                        String stringGenres = String.join(", ", genres);
                        dialog.print(user, stringGenres);
                        tellFilmCommand = dialog.takeArg(user).getMessage();
                    }
                } else {
                    tellFilmCommand = "случайный";
                }
                if (tellFilmCommand.equals("стоп") || tellFilmCommand.equals("-help")) {
                    dialog.print(user, commandStorage.parsingSupportedCommand(tellFilmCommand));
                    isRunning = exit(tellFilmCommand);
                } else {
                    ApiObject response = apiFilm.takeFilms(TypeOfFilmRequest.commandToEnum(command), tellFilmCommand);
                    switch (response) {
                        case Fault fault -> {
                            dialog.print(user, fault.getError());
                        }
                        case Movies movies -> {
                            Queue<Film> films = movies.getFilms();
                            StringBuilder result = new StringBuilder();
                            if (!command.equals("случайный")) {
                                if (films.isEmpty()) {
                                    dialog.print(user, "Не нашлось фильмов с такими характеристиками");
                                } else {
                                    command = "еще";
                                    while (command.equals("еще")) {
                                        if (films.isEmpty()) {
                                            dialog.print(user, "Фильмы кончились");
                                            break;
                                        }
                                        result.append(films.remove());
                                        result.append("Если вы хотите получить еще один фильм с такой характеристикой введите еще иначе введите хватит");
                                        dialog.print(user, result.toString());
                                        command = dialog.takeArg(user).getMessage();
                                        result = new StringBuilder();
                                    }
                                    dialog.print(user, "Жду дальнейших команд");
                                }
                            } else {
                                dialog.print(user, films.remove().toString());
                            }
                        }
                        default -> dialog.print(user, "Неизвестная ошибка");
                    }
                }
            }
            if (!isRunning) {
                continue;
            }
            command = dialog.takeArg(user).getMessage();
        }
    }
}

