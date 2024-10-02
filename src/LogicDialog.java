public class LogicDialog {
    private static void breaker(String command){
        if (command.equals("стоп")){
            System.exit(0);
        }
    }
    public static void startDialog(String command) throws Exception {
        Storage parser = new Storage();
        ConsoleBot consoleBot = new ConsoleBot();
        consoleBot.outConsole("Привет! Я фильм бот.");
        while(true){
            if (!parser.getFullStorage().contains(command)){
                consoleBot.outConsole(parser.parsing("-help"));
            }
            if (parser.getParsingCommand().contains(command)){
                consoleBot.outConsole(parser.parsing(command));
                breaker(command);
            }
            if (parser.getTellFilmArgCommand().contains(command)){
                consoleBot.outConsole(parser.tellFilmArg(command));
                String tellFilmCommand;
                if (!command.contains("случайный")){
                    tellFilmCommand = consoleBot.takeArg();
                }
                else{
                    tellFilmCommand = "случайный";
                }
                if (tellFilmCommand.equals("стоп") || tellFilmCommand.equals("-help")){
                    consoleBot.outConsole(parser.parsing(tellFilmCommand));
                    breaker(tellFilmCommand);
                }
                else {
                    ApiFilm requestToAPI = new ApiFilm();
                    String films = requestToAPI.takeFilms(command, tellFilmCommand);
                    consoleBot.outConsole(films);
                }
            }
            command = consoleBot.takeArg();
        }
    }
}
