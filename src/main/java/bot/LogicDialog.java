package bot;

import java.util.HashSet;

public class LogicDialog {
    private void exit(String command){
        if (command.equals("стоп")){
            System.exit(0);
        }
    }
    public void startDialog(String command) throws Exception {
        ApiFilm requestToAPI = new ApiFilm();
        CommandStorage commandStorage = new CommandStorage();
        WorkWithConsole workWithConsole = new WorkWithConsole();
        workWithConsole.print("Привет! Я фильм бот.");
        while(true){
            if (!commandStorage.isMyCommandFullStorage(command)){
                workWithConsole.print(commandStorage.parsing("-help"));
                command = workWithConsole.takeArg();
                continue;
            }
            if (commandStorage.isMyCommandParsing(command)){
                workWithConsole.print(commandStorage.parsing(command));
                exit(command);
            }
            if (commandStorage.isMyCommandTellFilmArg(command)){
                workWithConsole.print(commandStorage.parsingFilms(command));
                String tellFilmCommand;
                if (!command.contains("случайный")){
                    tellFilmCommand = workWithConsole.takeArg();
                    if (tellFilmCommand.contains("список")){
                        HashSet<String> genres = commandStorage.getGenres();
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
