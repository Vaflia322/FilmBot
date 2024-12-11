package bot;

import java.util.Queue;
import java.util.Set;

public class LogicDialog {
    private final ApiFilm apiFilm;
    private final Dialog dialog;
    private final CommandStorage commandStorage = new CommandStorage();

    public LogicDialog(ApiFilm apiFilm, Dialog dialog) {
        this.apiFilm = apiFilm;
        this.dialog = dialog;
    }

    public void statusProcessing(User user, UserState state, String command) {
        switch (state) {
            case CHARACTERISTIC_TYPE:
                characteristicType(user, command);
                break;
            case GET_GENRE:
                getGenre(user);
                break;
            case REQUEST:
                requestToApi(user);
                break;
            case GET_FILMS:
                printFilms(user, command);
                break;
            default:
                baseCommand(user, command);
                break;


        }
    }

    public UserState makeState(User user, String command) {
        if (TypeOfFilmRequest.commandToEnum(command) != null) {
            user.setApiRequest("characteristicType", command);
            return UserState.CHARACTERISTIC_TYPE;
        }
        if ("список".equals(command)) {
            return UserState.GET_GENRE;
        }
        if (user.getApiRequest().containsKey("characteristicType")) {
            user.setApiRequest("request", command);
            return UserState.REQUEST;
        }
        if (command.equals("еще") || command.equals("хватит")) {
            return UserState.GET_FILMS;
        }
        if (command.equals("стоп")) {
            return UserState.END;
        }
        return UserState.BASE_COMMAND;
    }

    private void baseCommand(User user, String command) {
        if (!commandStorage.isCommand(command)) {
            dialog.print(user, commandStorage.parsingSupportedCommand("-help"));
        }
        if (commandStorage.isSupportedCommand(command)) {
            dialog.print(user, commandStorage.parsingSupportedCommand(command));
        }
        if (commandStorage.isSupportedFilmsCommand(command)) {
            dialog.print(user, commandStorage.parsingSupportedFilmsCommand(command));
        }
    }

    private void getGenre(User user) {
        Set<String> genres = commandStorage.getGenres();
        String stringGenres = String.join(", ", genres);
        dialog.print(user, stringGenres);
    }

    private void characteristicType(User user, String command) {
        dialog.print(user, commandStorage.parsingSupportedFilmsCommand(command));
    }

    private void requestToApi(User user) {
        String command = user.getApiRequest().get("characteristicType");
        String tellFilmCommand = user.getApiRequest().get("request");
        user.getApiRequest().remove("characteristicType");
        ApiObject response = apiFilm.takeFilms(TypeOfFilmRequest.commandToEnum(command), tellFilmCommand);
        switch (response) {
            case Fault fault -> {
                dialog.print(user, fault.getError());
            }
            case Movies movies -> {
                Queue<Film> films = movies.getFilms();
                user.setFilms(films);
                if (films.isEmpty()) {
                    dialog.print(user, "Не нашлось фильмов с такими характеристиками");
                } else {
                    printFilms(user, "еще");
                }
            }
            default -> dialog.print(user, "Неизвестная ошибка");
        }
    }

    private void printFilms(User user, String command) {
        Queue<Film> films = user.getFilms();
        if (!user.getApiRequest().get("request").equals("случайный")) {
            if (films.isEmpty()) {
                dialog.print(user, "Фильмы кончились");
            }
            if (command.equals("еще")) {
                dialog.print(user, films.remove().toString()
                        + "Если вы хотите получить еще один фильм"
                        + " с такой характеристикой введите еще иначе введите хватит");
            } else {
                dialog.print(user, "Жду дальнейших команд");
                user.cleanMap();
            }
        } else {
            dialog.print(user, films.remove().toString());
        }
    }
}

