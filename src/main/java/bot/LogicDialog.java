package bot;

import org.json.JSONObject;
import org.json.JSONArray;
import java.util.Set;

public class LogicDialog {
    private final ApiFilm apiFilm;
    private final ConsoleInterface workWithConsole;
    public LogicDialog(ApiFilm apiFilm, ConsoleInterface workWithConsole){
        this.apiFilm = apiFilm;
        this.workWithConsole = workWithConsole;

    }
    private boolean exit(String command){
        if (command.equals("стоп")){
            return false;
        }
        return true;
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
                    JSONObject response = apiFilm.takeFilms(Type.valueOf(command), tellFilmCommand);
                    StringBuilder result = new StringBuilder();
                    if(response.has("Error")){
                        workWithConsole.print(response.getString("Error"));
                    }
                    else if (!command.equals("случайный")) {
                        JSONArray films = new JSONArray(response.getJSONArray("docs"));
                        if (films.isEmpty()){
                            workWithConsole.print("Не нашлось фильмов с такими характеристиками");
                        }
                        else {
                            int outputMovieNumber = 0;
                            final int totalFilms = films.length();
                            command = "еще";
                            while (command.equals("еще")) {
                                if (outputMovieNumber > totalFilms) {
                                    result.append("Фильмы кончились");
                                    workWithConsole.print(result.toString());
                                    break;
                                }
                                JSONObject outFilm = films.getJSONObject(outputMovieNumber);
                                result.append("Название: ").append(outFilm.get("name")).append("\n");
                                result.append("Описание: ").append(outFilm.get("description")).append("\n");
                                JSONObject rating = (outFilm.getJSONObject("rating"));
                                result.append("Рейтинг кинопоиска: ").append(rating.get("kp").toString()).append("\n");
                                result.append("Если вы хотите получить еще один фильм с такой характеристикой введите еще иначе введите хватит");
                                workWithConsole.print(result.toString());
                                command = workWithConsole.takeArg();
                                outputMovieNumber++;
                                result = new StringBuilder();
                            }
                            workWithConsole.print("Как скажете");
                        }
                    }
                    else {
                        JSONObject rating = (response.getJSONObject("rating"));
                        result.append("Название: ").append(response.get("name")).append("\n");
                        result.append("Описание: ").append(response.get("description")).append("\n");
                        result.append("Рейтинг кинопоиска: ").append(rating.get("kp").toString());
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
