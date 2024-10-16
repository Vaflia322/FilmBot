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
    private final String apiKey;
    private final String queryBasis = "https://api.kinopoisk.dev/v1.4/movie";
    {
        try {
            fileReader = new BufferedReader(new FileReader("api-key.txt"));
            apiKey = fileReader.readLine();
        } catch (IOException e) {
            throw new RuntimeException("Файл не найден");
        }
    }


    private final RequestValidation requestValidation = new RequestValidation();
    public Error error = new Error();
    public Films films = new Films();
    public ApiObject takeFilms(Type type, String request){
        final StringBuilder urlRequest = new StringBuilder().append(queryBasis);
        //JSONObject result = new JSONObject();
        switch (type){
            case название:
                if (!requestValidation.isNameExists(request)){
                    error.setError("Вы ввели некорректное название, оно должно быть на РУССКОМ ЯЗЫКЕ");
                    return error;
                }
                urlRequest.append("/search?query=");
                break;
            case жанр:
                if (!requestValidation.isGenreExists(request)){
                    error.setError("Вы ввели некорректный жанр");
                    return error;
                }
                urlRequest.append("?genres.name=");
                break;
            case рейтинг:
                if (!requestValidation.isRatingExists(request)){
                    error.setError("Вы ввели некорректный рейтинг, введите рейтинг от 1 до 10\nТакже можете указать диапазон,например 7.3-9");
                    return error;
                }
                urlRequest.append("?rating.kp=");
                break;
            case год:
                if (!requestValidation.isYearExists(request)){
                    error.setError("Вы ввели некорректный год, введите год, либо диапазон, напрмер 2018,2020-2023");
                    return error;
                }
                urlRequest.append("?year=");
                break;
            case случайный:
                request = "";
                urlRequest.append("/random?");
                break;
            default:
                error.setError("Некорректный запрос");
                return error;
        }
        request = URLEncoder.encode(request, StandardCharsets.UTF_8);
        urlRequest.append(request).append("&notNullFields=name&notNullFields=description&notNullFields=rating.kp");
        final URL url;
        try{
            url = new URL(urlRequest.toString());
        }
        catch (MalformedURLException e){
            error.setError("Ошибка при создании URL");
            return error;
        }
        HttpURLConnection con;
        try{
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
        }
        catch (Exception e){
            error.setError("Ошибка при установке соединения");
            return error;
        }
        con.setRequestProperty("X-API-Key",apiKey );
        int responseCode;
        try{
            responseCode = con.getResponseCode();
        }
        catch (Exception e){
            error.setError("Ошибка при получении запроса со стороны API");
            return error;
        }
        if (responseCode!=200){
            error.setError("Сервис временно не доступен");
            return error;
        }
        BufferedReader reader;
        try{
            reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
        }
        catch (Exception e){
            error.setError("Ошибка при прочтении запроса к API");
            return error;
        }
        JSONObject response;
        try{
            response = new JSONObject(reader.readLine());
        }
        catch(Exception e){
            error.setError("Ошибка при прочтении запроса к API");
            return error;
        }

        try{
            reader.close();
        }
        catch(Exception e){
            error.setError("Ошибка при закрытии потока чтения");
        }
        if (!type.equals(Type.случайный)){
            JSONArray docs = new JSONArray(response.getJSONArray("docs"));
            for (int i = 0; i< docs.length(); i++) {
                JSONObject film = docs.getJSONObject(i);
                String name = film.getString("name");
                String description = film.getString("description");
                JSONObject ratings = (film.getJSONObject("rating"));
                String rating = ratings.get("kp").toString();
                films.addFilm(i+1,name,description,rating);
            }
        }
        else{
            JSONObject ratings = (response.getJSONObject("rating"));
            String name = response.getString("name");
            String description = response.getString("description");
            String rating = ratings.get("kp").toString();
            films.addFilm(1,name,description,rating);

        }
        return films;
    }
}

