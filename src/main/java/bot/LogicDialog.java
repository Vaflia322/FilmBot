package bot;

import java.util.Set;

public class LogicDialog {
    private void exit(String command){
        if (command.equals("стоп")){
            System.exit(0);
        }
    }
    public void startDialog(String command) {
        ApiFilm requestToAPI = new ApiFilm();
        CommandStorage commandStorage = new CommandStorage();
        WorkWithConsole workWithConsole = new WorkWithConsole();
        workWithConsole.print("Привет! Я фильм бот.");
        while(true){
            if (!commandStorage.isCommand(command)){
                workWithConsole.print(commandStorage.parsing("-help"));
                command = workWithConsole.takeArg();
                continue;
            }
            if (commandStorage.isSupportedCommand(command)){
                workWithConsole.print(commandStorage.parsing(command));
                exit(command);
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
                    exit(tellFilmCommand);
                }
                else {
                    String films = requestToAPI.takeFilms(command, tellFilmCommand);
                    workWithConsole.print(films);
                }
            }
            command = workWithConsole.takeArg();
        }
    }
}
