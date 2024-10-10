package bot;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Set;

public class RequestValidation {
    CommandStorage commandStorage = new CommandStorage();
    Set<String> genres = commandStorage.getGenres();
    Pattern patternName = Pattern.compile(
            "[" +                   //начало списка допустимых символов
                    "а-яА-ЯёЁ" +    //буквы русского алфавита
                    "\\d" +         //цифры
                    "\\s" +         //знаки-разделители (пробел, табуляция и т.д.)
                    "\\p{Punct}" +  //знаки пунктуации
                    "]" +                   //конец списка допустимых символов
                    "*");                   //допускается наличие указанных символов в любом количестве
    Pattern patternNumber = Pattern.compile(
            "[" +                   //начало списка допустимых символов
                    "\\d" +         //цифры
                    "\\p{Punct}" +  //знаки пунктуации
                    "]" +                   //конец списка допустимых символов
                    "*");                   //допускается наличие указанных символов в любом количестве
    public boolean isGenreExists(String genreAuth) {
        return (genres.contains(genreAuth));
    }

    public boolean isNameExists(String name) {
        Matcher matcher = patternName.matcher(name);
        return matcher.matches();
    }
    public boolean isRatingExists(String number){
        Matcher matcher = patternNumber.matcher(number);
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
    public boolean isYearExists(String number) {
        Matcher matcher = patternNumber.matcher(number);
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
