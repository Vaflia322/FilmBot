package bot;
import java.io.FileReader;
import java.net.URLEncoder;
import org.json.JSONObject;
import org.json.JSONArray;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
public class ApiFilm {
    public String takeFilms(String type, String request) throws Exception {
        String URLRequest;
        String result = "";
        Checker checker = new Checker();
        switch (type){
            case ("название"):
                if (!checker.checkForName(request)){
                    return "Вы ввели некорректное название, оно должно быть на РУССКОМ ЯЗЫКЕ";
                }
                request = URLEncoder.encode(request,"utf-8");
                URLRequest = "/search?query="+request;
                break;
            case ("жанр"):
                if (!checker.checkForGenre(request)){
                    return "Вы ввели некорректный жанр, пожалуйста выберите жанр из списка предложенных\n" +
                            "Чтобы его получить введите СПИСОК";
                }
                request = URLEncoder.encode(request,"utf-8");
                URLRequest = "?genres.name=" + request;
                break;
            case ("список"):
                if (!checker.checkForGenre(request)){
                    return "Вы ввели некорректный жанр, пожалуйста выберите жанр из списка предложенных\n" +
                            "Чтобы его получить введите СПИСОК";
                }
                request = URLEncoder.encode(request,"utf-8");
                URLRequest = "?genres.name=" + request;
                break;
            case("рейтинг"):
                if (!checker.checkForRating(request)){
                    return "Вы ввели некорректный рейтинг, введите рейтинг от 1 до 10\n" +
                            "Также можете указать диапазон,например 7.3-9";
                }
                URLRequest = "?rating.kp=" +request;
                break;
            case("год"):
                if (!checker.checkForYear(request)){
                    return "Вы ввели некорректный год, введите год, либо диапазон, напрмер 2018,2020-2023";
                }
                URLRequest = "?year=" +request;
                break;
            case("случайный"):
                URLRequest = "/random?";
                break;
            default:
                URLRequest = "/random?";
        }
        BufferedReader fileReader = new BufferedReader(new FileReader("api-key.txt"));
        String apiKey = fileReader.readLine();
        URL url = new URL("https://api.kinopoisk.dev/v1.4/movie" +URLRequest + "&notNullFields=name"+"&notNullFields=description" +"&notNullFields=rating.kp");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("X-API-Key",apiKey );
        int responseCode = con.getResponseCode();
        if (responseCode!=200){
            return "Сервис временно не доступен";
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
        if (!request.equals("случайный")) {
            JSONObject response = new JSONObject(reader.readLine());
            JSONArray films = new JSONArray(response.getJSONArray("docs"));
            if (films.isEmpty()){
                return "С данными характеристиками не нашлось фильма";
            }
            for (int i = 0; i < 3; i++) {
                JSONObject outFilm = films.getJSONObject(i);
                result += "Название: " + outFilm.get("name") + "\n";
                result += "Описание: " + outFilm.get("description") + "\n";
                JSONObject rating = (outFilm.getJSONObject("rating"));
                result += "Рейтинг кинопоиска: " + rating.get("kp").toString() + "\n";
                result += "\n";
            }
        }
        else {
            JSONObject response = new JSONObject(reader.readLine());
            JSONObject rating = (response.getJSONObject("rating"));
            result += "Название: " + response.get("name") + "\n";
            result += "Описание: " + response.get("description") + "\n";
            result += "Рейтинг кинопоиска: " + rating.get("kp").toString() + "\n";
        }
        reader.close();
        return result;
    }
}

