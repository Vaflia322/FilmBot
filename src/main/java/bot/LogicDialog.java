package bot;


import java.util.Set;

public class LogicDialog {
    private final ApiFilm apiFilm;
    public LogicDialog(ApiFilm apiFilm){
        this.apiFilm = apiFilm;

    }
    private boolean exit(String command,boolean flag){
        if (command.equals("стоп")){
            flag = false;
        }
        return flag;
    }
    public void startDialog(String command) {
        CommandStorage commandStorage = new CommandStorage();
        WorkWithConsole workWithConsole = new WorkWithConsole();
        workWithConsole.print("Привет! Я фильм бот.");
        boolean flag = true;
        while(flag){
            if (!commandStorage.isCommand(command)){
                workWithConsole.print(commandStorage.parsing("-help"));
                command = workWithConsole.takeArg();
                continue;
            }
            if (commandStorage.isSupportedCommand(command)){
                workWithConsole.print(commandStorage.parsing(command));
                flag = exit(command,flag);
            }
            if (commandStorage.isSupportedFilmsCommand(command)){
                workWithConsole.print(commandStorage.parsingFilms(command));
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
                    workWithConsole.print(commandStorage.parsing(tellFilmCommand));
                    flag = exit(tellFilmCommand,flag);
                }
                else {
                    Object films = apiFilm.takeFilms(command, tellFilmCommand);
                    workWithConsole.print(films.toString());
                }
            }
            if (!flag){
                continue;
            }
            command = workWithConsole.takeArg();
        }
    }
}
