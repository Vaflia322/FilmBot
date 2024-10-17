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
    private final Fault fault = new Fault();
    private final Movies movies = new Movies();
    public ApiObject takeFilms(TypeOfFilm typeOfFilm, String request){
        final StringBuilder urlRequest = new StringBuilder().append(queryBasis);
        switch (typeOfFilm){
            case NAME:
                if (!requestValidation.isNameExists(request)){
                    fault.setError("Вы ввели некорректное название, оно должно быть на РУССКОМ ЯЗЫКЕ");
                    return fault;
                }
                urlRequest.append("/search?query=");
                break;
            case GENRE:
                if (!requestValidation.isGenreExists(request)){
                    fault.setError("Вы ввели некорректный жанр");
                    return fault;
                }
                urlRequest.append("?genres.name=");
                break;
            case RATING:
                if (!requestValidation.isRatingExists(request)){
                    fault.setError("Вы ввели некорректный рейтинг, введите рейтинг от 1 до 10\nТакже можете указать диапазон,например 7.3-9");
                    return fault;
                }
                urlRequest.append("?rating.kp=");
                break;
            case YEAR:
                if (!requestValidation.isYearExists(request)){
                    fault.setError("Вы ввели некорректный год, введите год, либо диапазон, напрмер 2018,2020-2023");
                    return fault;
                }
                urlRequest.append("?year=");
                break;
            case RANDOM:
                request = "";
                urlRequest.append("/random?");
                break;
            default:
                fault.setError("Некорректный запрос");
                return fault;
        }
        request = URLEncoder.encode(request, StandardCharsets.UTF_8);
        urlRequest.append(request).append("&notNullFields=name&notNullFields=description&notNullFields=rating.kp");
        final URL url;
        try{
            url = new URL(urlRequest.toString());
        }
        catch (MalformedURLException e){
            fault.setError("Ошибка при создании URL");
            return fault;
        }
        HttpURLConnection con;
        try{
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
        }
        catch (Exception e){
            fault.setError("Ошибка при установке соединения");
            return fault;
        }
        con.setRequestProperty("X-API-Key",apiKey );
        int responseCode;
        try{
            responseCode = con.getResponseCode();
        }
        catch (Exception e){
            fault.setError("Ошибка при получении запроса со стороны API");
            return fault;
        }
        if (responseCode!=200){
            fault.setError("Сервис временно не доступен");
            return fault;
        }
        BufferedReader reader;
        try{
            reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
        }
        catch (Exception e){
            fault.setError("Ошибка при прочтении запроса к API");
            return fault;
        }
        JSONObject response;
        try{
            response = new JSONObject(reader.readLine());
        }
        catch(Exception e){
            fault.setError("Ошибка при прочтении запроса к API");
            return fault;
        }

        try{
            reader.close();
        }
        catch(Exception e){
            fault.setError("Ошибка при закрытии потока чтения");
        }
        if (!typeOfFilm.equals(TypeOfFilm.RANDOM)){
            JSONArray docs = new JSONArray(response.getJSONArray("docs"));
            for (int i = 0; i< docs.length(); i++) {
                JSONObject film = docs.getJSONObject(i);
                String name = film.getString("name");
                String description = film.getString("description");
                JSONObject ratings = (film.getJSONObject("rating"));
                String rating = ratings.get("kp").toString();
                movies.addFilm(name,description,rating);
            }
        }
        else{
            JSONObject ratings = (response.getJSONObject("rating"));
            String name = response.getString("name");
            String description = response.getString("description");
            String rating = ratings.get("kp").toString();
            movies.addFilm(name,description,rating);

        }
        return movies;
    }
}

