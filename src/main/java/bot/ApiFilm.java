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

    public Object takeFilms(String type, String request){
        StringBuilder urlRequest = new StringBuilder().append("https://api.kinopoisk.dev/v1.4/movie");
        StringBuilder result = new StringBuilder();
        RequestValidation requestValidation = new RequestValidation();
        switch (type){
            case ("название"):
                if (!requestValidation.isNameExists(request)){
                    return result.append("Вы ввели некорректное название, оно должно быть на РУССКОМ ЯЗЫКЕ");
                }
                urlRequest.append("/search?query=");
                break;
            case ("жанр"):
                if (!requestValidation.isGenreExists(request)){
                    return result.append("Вы ввели некорректный жанр");
                }
                urlRequest.append("?genres.name=");
                break;
            case("рейтинг"):
                if (!requestValidation.isRatingExists(request)){
                    return result.append("Вы ввели некорректный рейтинг, введите рейтинг от 1 до 10\n").append("Также можете указать диапазон,например 7.3-9");
                }
                urlRequest.append("?rating.kp=");
                break;
            case("год"):
                if (!requestValidation.isYearExists(request)){
                    return result.append("Вы ввели некорректный год, введите год, либо диапазон, напрмер 2018,2020-2023");
                }
                urlRequest.append("?year=");
                break;
            case("случайный"):
                request = "";
                urlRequest.append("/random?");
                break;
            default:
                return result.append("Некорректный запрос");
        }
        request = URLEncoder.encode(request, StandardCharsets.UTF_8);
        urlRequest.append(request).append("&notNullFields=name&notNullFields=description&notNullFields=rating.kp");
        final URL url;
        try{
            url = new URL(urlRequest.toString());
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
        if (!type.equals("случайный")) {
            JSONArray films = new JSONArray(response.getJSONArray("docs"));
            if (films.isEmpty()){
                return result.append("С данными характеристиками не нашлось фильма");
            }
            JSONObject outFilm = films.getJSONObject(0);
            result.append("Название: ").append(outFilm.get("name")).append("\n");
            result.append("Описание: ").append(outFilm.get("description")).append("\n");
            JSONObject rating = (outFilm.getJSONObject("rating"));
            result.append("Рейтинг кинопоиска: ").append(rating.get("kp").toString());
        }
        else {
            JSONObject rating = (response.getJSONObject("rating"));
            result.append("Название: ").append(response.get("name")).append("\n");
            result.append("Описание: ").append(response.get("description")).append("\n");
            result.append("Рейтинг кинопоиска: ").append(rating.get("kp").toString());
        }
        try{
            reader.close();
        }
        catch(Exception e){
            return "Ошибка при закрытии потока чтения";
        }
        return result;
    }
}

