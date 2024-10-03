package bot;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Checker {
    private String[] genres = {
            "аниме",
            "биография",
            "боевик",
            "вестерн",
            "военный",
            "детектив",
            "детский",
            "для взрослых",
            "документальный",
            "драма",
            "игра",
            "история",
            "комедия",
            "концерт",
            "короткометражка",
            "криминал",
            "мелодрама",
            "музыка",
            "мультфильм",
            "мюзикл",
            "новости",
            "приключения",
            "реальное ТВ",
            "семейный",
            "спорт",
            "ток-шоу",
            "триллер",
            "ужасы",
            "фантастика",
            "фильм-нуар",
            "фэнтези",
            "церемония"
    };

    public String getStringGenres() {
        return String.join(", ", genres);
    }

    public boolean checkForGenre(String genre) {
        for (int i = 0; i < genres.length; i++) {
            if (genres[i].equals(genre)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkForName(String name) {
        Pattern pattern = Pattern.compile(
                "[" +                   //начало списка допустимых символов
                        "а-яА-ЯёЁ" +    //буквы русского алфавита
                        "\\d" +         //цифры
                        "\\s" +         //знаки-разделители (пробел, табуляция и т.д.)
                        "\\p{Punct}" +  //знаки пунктуации
                        "]" +                   //конец списка допустимых символов
                        "*");                   //допускается наличие указанных символов в любом количестве

        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }
    public boolean checkForRating(String number){
        Pattern pattern = Pattern.compile(
                "[" +                   //начало списка допустимых символов
                        "\\d" +         //цифры
                        "\\p{Punct}" +  //знаки пунктуации
                        "]" +                   //конец списка допустимых символов
                        "*");                   //допускается наличие указанных символов в любом количестве

        Matcher matcher = pattern.matcher(number);
        String checkNumber = "";
        double doubleNumber;
        if (matcher.matches()) {
            for (int i = 0; i < number.length(); i++) {
                if (number.charAt(i) == '-') {
                    if (!checkNumber.equals("")) {
                        doubleNumber = Double.parseDouble(checkNumber);
                        if (doubleNumber < 1 || doubleNumber > 10) {
                            return false;
                        }
                    }
                    checkNumber = "";
                    continue;
                }
                checkNumber += number.charAt(i);
            }
            doubleNumber = Double.parseDouble(checkNumber);
            if (doubleNumber < 1 || doubleNumber > 10) {
                return false;
            }
        }
        return matcher.matches();
    }
    public boolean checkForYear(String number) {
        Pattern pattern = Pattern.compile(
                "[" +                   //начало списка допустимых символов
                        "\\d" +         //цифры
                        "\\p{Punct}" +  //знаки пунктуации
                        "]" +                   //конец списка допустимых символов
                        "*");                   //допускается наличие указанных символов в любом количестве

        Matcher matcher = pattern.matcher(number);
        String checkNumber = "";
        int intNumber;
        if (matcher.matches()) {
            for (int i = 0; i < number.length(); i++) {
                if (number.charAt(i) == '-') {
                    if (!checkNumber.equals("")) {
                        intNumber = Integer.valueOf(checkNumber);
                        if (intNumber < 1900 || intNumber > 2024) {
                            return false;
                        }
                    }
                    checkNumber = "";
                    continue;
                }
                checkNumber += number.charAt(i);
            }
            intNumber = Integer.valueOf(checkNumber);
            if (intNumber < 1900 || intNumber > 2024) {
                return false;
            }
        }
        return matcher.matches();
    }

}
