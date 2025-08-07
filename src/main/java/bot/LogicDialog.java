package bot;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class LogicDialog {
    private final ApiFilm apiFilm;
    private final Dialog dialog;
    private final CommandStorage commandStorage = new CommandStorage();
    private final UsersDataBaseQueries usersDataBaseQueries = new UsersDataBaseQueries();
    private final FilmsDataBaseQueries filmsDataBaseQueries = new FilmsDataBaseQueries();
    private final Map<UserState, MultiFunction> userStateMultiFunctionMap = new HashMap<>();

    {
        userStateMultiFunctionMap.put(UserState.SHOW_BLACK_LIST, args -> showList((User) args[0], "blacklist"));
        userStateMultiFunctionMap.put(UserState.SHOW_WISH_LIST, args -> showList((User) args[0], "wishlist"));
        userStateMultiFunctionMap.put(UserState.SHOW_VIEWED_LIST, args -> showList((User) args[0], "viewed"));
        userStateMultiFunctionMap.put(UserState.ADD_VIEWED_LIST, args -> addViewedList((User) args[0],
                (String) args[1]));
        userStateMultiFunctionMap.put(UserState.ADD_WISH_LIST, args -> addWishList((User) args[0]));
        userStateMultiFunctionMap.put(UserState.ADD_BLACK_LIST, args -> addBlackList((User) args[0]));
        userStateMultiFunctionMap.put(UserState.CHARACTERISTIC_TYPE, args -> characteristicType((User) args[0],
                (String) args[1]));
        userStateMultiFunctionMap.put(UserState.GET_GENRE, args -> getGenre((User) args[0]));
        userStateMultiFunctionMap.put(UserState.REQUEST, args -> requestToApi((User) args[0]));
        userStateMultiFunctionMap.put(UserState.GET_FILMS, args -> printFilms((User) args[0], (String) args[1]));
    }

    public LogicDialog(ApiFilm apiFilm, Dialog dialog) {
        this.apiFilm = apiFilm;
        this.dialog = dialog;
    }

    public void statusProcessing(User user, UserState state, String command) {
        if (!userStateMultiFunctionMap.containsKey(state)) {
            baseCommand(user, command);
        } else {
            MultiFunction function = userStateMultiFunctionMap.get(state);
            function.apply(user, command);
        }
    }

    public void showList(User user, String typeList) {
        Map<String, Set<String>> data = usersDataBaseQueries.getUserData(user.getUserID());
        Set<String> list = data.get(typeList);
        String stringList = String.join(", ", list);
        dialog.print(user, stringList);
    }

    public void addBlackList(User user) {
        usersDataBaseQueries.addFilmToBlackList(user.getLastFilm(), user.getUserID());
        dialog.print(user, "\nФильм добавлен в черный список");
    }

    public void addWishList(User user) {
        usersDataBaseQueries.addFilmToWishList(user.getLastFilm(), user.getUserID());
        dialog.print(user, "\nФильм добавлен в список желаемых");
    }

    public void addViewedList(User user, String film) {
        film = film.replace("добавить просмотренный ", "");
        usersDataBaseQueries.addFilmToViewed(film, user.getUserID());
        dialog.print(user, "\nФильм добавлен в список просмотренных");
    }

    public UserState makeState(User user, String command) {
        if (!usersDataBaseQueries.checkUserExists(user.getUserID())) {
            usersDataBaseQueries.createUser(user.getUserID());
        }
        if (TypeOfFilmRequest.commandToEnum(command) != null) {
            user.setApiRequest("characteristicType", command);
            return UserState.CHARACTERISTIC_TYPE;
        }
        if (command.equals("добавить в черный список") || command.equals("добавить в чёрный список")) {
            return UserState.ADD_BLACK_LIST;
        }
        if (command.equals("добавить в желаемые к просмотру")) {
            return UserState.ADD_WISH_LIST;
        }
        if (command.contains("добавить просмотренный")) {
            return UserState.ADD_VIEWED_LIST;
        }
        if (command.equals("черный список")) {
            return UserState.SHOW_BLACK_LIST;
        }
        if (command.equals("просмотренные")) {
            return UserState.SHOW_VIEWED_LIST;
        }
        if (command.equals("желаемые к просмотру")) {
            return UserState.SHOW_WISH_LIST;
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
        ApiObject response = filmsDataBaseQueries.getFilms(TypeOfFilmRequest.commandToEnum(command), tellFilmCommand);
        if (response == null) {
            response = apiFilm.takeFilms(TypeOfFilmRequest.commandToEnum(command), tellFilmCommand);
        }
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
                    for (Film film : films) {
                        if (!filmsDataBaseQueries.checkFilmExists(film.name())) {
                            filmsDataBaseQueries.addFilm(film);
                        }
                    }
                    printFilms(user, "еще");
                }
            }
            default -> dialog.print(user, "Неизвестная ошибка");
        }
    }

    private void printFilms(User user, String command) {
        Queue<Film> films = user.getFilms();
        Map<String, Set<String>> data = usersDataBaseQueries.getUserData(user.getUserID());
        Set<String> blacklist = data.get("blacklist");
        if (!user.getApiRequest().get("request").equals("случайный")) {
            if (films.isEmpty()) {
                dialog.print(user, "Фильмы кончились");
            }
            if (command.equals("еще")) {
                Film film = films.remove();
                if (blacklist.contains(film.name())) {
                    while (blacklist.contains(film.name())) {
                        film = films.remove();
                    }
                }
                user.setLastFilm(film);
                dialog.print(user, film
                        + "\nЕсли вы хотите получить еще один фильм"
                        + " с такой характеристикой введите еще"
                        + "\n\nЕсли вы хотите добавить фильм в желаемые к просмотру"
                        + " введите добавить в желаемые к просмотру"
                        + "\n\nЕсли вы хотите добавить фильм в черный список"
                        + " введите добавить в черный список"
                        + " иначе введите хватит");
            } else {
                dialog.print(user, "Жду дальнейших команд");
                user.cleanMap();
            }
        } else {
            dialog.print(user, films.remove().toString());
        }
    }
}

