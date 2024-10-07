package bot;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import org.json.JSONObject;
import org.json.JSONArray;

import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;



public class ApiFilm {
    private final BufferedReader fileReader;

    {
        try {
            fileReader = new BufferedReader(new FileReader("api-key.txt"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private final String apiKey;

    {
        try {
            apiKey = fileReader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String takeFilms(String type, String request) throws Exception {
        String URLRequest;
        StringBuilder result = new StringBuilder();
        RequestAuthentication requestAuthentication = new RequestAuthentication();
        switch (type){
            case ("название"):
                if (!requestAuthentication.isNameExists(request)){
                    return "Вы ввели некорректное название, оно должно быть на РУССКОМ ЯЗЫКЕ";
                }
                request = URLEncoder.encode(request, StandardCharsets.UTF_8);
                URLRequest = "/search?query="+request;
                break;
            case ("жанр"):
                if (!requestAuthentication.isGenreExists(request)){
                    return "Вы ввели некорректный жанр";
                }
                request = URLEncoder.encode(request, StandardCharsets.UTF_8);
                URLRequest = "?genres.name=" + request;
                break;
            case ("список"):
                request = URLEncoder.encode(request, StandardCharsets.UTF_8);
                URLRequest = "?genres.name=" + request;
                break;
            case("рейтинг"):
                if (!requestAuthentication.isRatingExists(request)){
                    return "Вы ввели некорректный рейтинг, введите рейтинг от 1 до 10\n" +
                            "Также можете указать диапазон,например 7.3-9";
                }
                URLRequest = "?rating.kp=" +request;
                break;
            case("год"):
                if (!requestAuthentication.isYearExists(request)){
                    return "Вы ввели некорректный год, введите год, либо диапазон, напрмер 2018,2020-2023";
                }
                URLRequest = "?year=" +request;
                break;
            case("случайный"):
                URLRequest = "/random?";
                break;
            default:
                return "Некорректный запрос";
        }
        final URL url;
        try{
            url = new URL("https://api.kinopoisk.dev/v1.4/movie" +URLRequest + "&notNullFields=name"+"&notNullFields=description" +"&notNullFields=rating.kp");
        }
        catch (MalformedURLException e){
            return "Ошибка при создании URL";
        }
        HttpURLConnection con;
        try{
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
        }
        catch (Exception e){
            return "Ошибка при установке соединения";
        }
        con.setRequestProperty("X-API-Key",apiKey );
        int responseCode;
        try{
            responseCode = con.getResponseCode();
        }
        catch (Exception e){
            return "Ошибка при получении запроса со стороны API";
        }
        if (responseCode!=200){
            return "Сервис временно не доступен";
        }
        BufferedReader reader;
        try{
            reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
        }
        catch (Exception e){
            return "Ошибка при прочтении запроса к API";
        }
        JSONObject response;
        try{
            response = new JSONObject(reader.readLine());
        }
        catch(Exception e){
            return "Ошибка при прочтении запроса к API";
        }
        if (!request.equals("случайный")) {
            JSONArray films = new JSONArray(response.getJSONArray("docs"));
            if (films.isEmpty()){
                return "С данными характеристиками не нашлось фильма";
            }
            for (int i = 0; i < 3; i++) {
                JSONObject outFilm = films.getJSONObject(i);
                result.append("Название: ").append(outFilm.get("name")).append("\n");
                result.append("Описание: ").append(outFilm.get("description")).append("\n");
                JSONObject rating = (outFilm.getJSONObject("rating"));
                result.append("Рейтинг кинопоиска: ").append(rating.get("kp").toString()).append("\n");
                result.append("\n");
            }
        }
        else {
            JSONObject rating = (response.getJSONObject("rating"));
            result.append("Название: ").append(response.get("name")).append("\n");
            result.append("Описание: ").append(response.get("description")).append("\n");
            result.append("Рейтинг кинопоиска: ").append(rating.get("kp").toString()).append("\n");
        }
        try{
            reader.close();
        }
        catch(Exception e){
            return "Ошибка при закрытии потока чтения";
        }
        return result.toString();
    }
}

