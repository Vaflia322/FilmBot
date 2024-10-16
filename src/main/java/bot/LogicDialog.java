package bot;

import org.json.JSONObject;
import org.json.JSONArray;

import java.util.Map;
import java.util.Set;

public class LogicDialog {
    private final ApiFilm apiFilm;
    private final ConsoleInterface workWithConsole;
    public LogicDialog(ApiFilm apiFilm, ConsoleInterface workWithConsole){
        this.apiFilm = apiFilm;
        this.workWithConsole = workWithConsole;

    }
    private boolean exit(String command){
        return !command.equals("стоп");
    }
    public void startDialog(String command) {

        CommandStorage commandStorage = new CommandStorage();
        workWithConsole.print("Привет! Я фильм бот.");
        boolean isExit = true;
        while(isExit){
            if (!commandStorage.isCommand(command)){
                workWithConsole.print(commandStorage.parsingSupportedCommand("-help"));
                command = workWithConsole.takeArg();
                continue;
            }
            if (commandStorage.isSupportedCommand(command)){
                workWithConsole.print(commandStorage.parsingSupportedCommand(command));
                isExit = exit(command);
            }
            if (commandStorage.isSupportedFilmsCommand(command)){
                workWithConsole.print(commandStorage.parsingSupportedFilmsCommand(command));
                String tellFilmCommand;
                if (!command.contains("случайный")){
                    tellFilmCommand = workWithConsole.takeArg();
                    if (tellFilmCommand.contains("список")){
                        Set<String> genres = commandStorage.getGenres();
                        String stringGenres = String.join(", ", genres);
                        workWithConsole.print(stringGenres);
                        tellFilmCommand = workWithConsole.takeArg();
                    }
                }
                else{
                    tellFilmCommand = "случайный";
                }
                if (tellFilmCommand.equals("стоп") || tellFilmCommand.equals("-help")){
                    workWithConsole.print(commandStorage.parsingSupportedCommand(tellFilmCommand));
                    isExit = exit(tellFilmCommand);
                }
                else {
                    var response = apiFilm.takeFilms(Type.valueOf(command), tellFilmCommand);
                    Map<Integer,String> data = response.fetchData();
                    StringBuilder result = new StringBuilder();
                    if(data.containsKey(-1)) {
                        workWithConsole.print(data.get(-1));
                    }
                    else if (!command.equals("случайный")) {
                        if (!data.containsKey(1)){
                            workWithConsole.print("Не нашлось фильмов с такими характеристиками");
                        }
                        else {
                            int outputMovieNumber = 1;
                            final int totalFilms = data.size();
                            command = "еще";
                            while (command.equals("еще")) {
                                if (outputMovieNumber > totalFilms) {
                                    result.append("Фильмы кончились");
                                    workWithConsole.print(result.toString());
                                    break;
                                }
                                result.append(data.get(outputMovieNumber));
                                result.append("Если вы хотите получить еще один фильм с такой характеристикой введите еще иначе введите хватит");
                                workWithConsole.print(result.toString());
                                command = workWithConsole.takeArg();
                                outputMovieNumber++;
                                result = new StringBuilder();
                            }
                            workWithConsole.print("Жду дальнейших команд");
                        }
                    }
                    else {
                        result.append(data.get(1));
                        workWithConsole.print(result.toString());
                    }
                }
            }
            if (!isExit){
                continue;
            }
            command = workWithConsole.takeArg();
        }
    }
}
