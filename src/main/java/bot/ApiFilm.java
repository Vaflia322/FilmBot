package bot;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


public class ApiFilm {
    private final BufferedReader fileReader;
    private final String apiKey;
    FilmsDataBaseQueries filmsDataBaseQueries = new FilmsDataBaseQueries();
    private final static String BASE_URL = "https://api.kinopoisk.dev/v1.4/movie";

    {
        try {
            fileReader = new BufferedReader(new FileReader("api-key.txt"));
            apiKey = fileReader.readLine();
        } catch (IOException e) {
            throw new RuntimeException("Файл не найден");
        }
    }

    private final RequestValidation requestValidation = new RequestValidation();

    private Film getFilmFromJSONObject(JSONObject docs) {
        String name = docs.getString("name");
        String description = docs.getString("description");
        JSONObject ratings = (docs.getJSONObject("rating"));
        String rating = ratings.get("kp").toString();
        String year = String.valueOf(docs.getInt("year"));
        JSONArray genres = docs.getJSONArray("genres");
        List<String> genre = new ArrayList<>();
        for (int i = 0; i < genres.length(); i++) {
            JSONObject object = genres.getJSONObject(i);
            genre.add(object.getString("name"));
        }
        Film film = new Film(name, description, rating, genre, year);
        filmsDataBaseQueries.addFilm(film);
        return film;
    }

    public ApiObject takeFilms(TypeOfFilmRequest typeOfFilmRequest, String request) {
        final Movies movies = new Movies();
        final StringBuilder urlRequest = new StringBuilder().append(BASE_URL);
        switch (typeOfFilmRequest) {
            case NAME:
                if (!requestValidation.isNameExists(request)) {
                    return new Fault("Вы ввели некорректное название, оно должно быть на РУССКОМ ЯЗЫКЕ");
                }
                urlRequest.append("/search?query=");
                break;
            case GENRE:
                if (!requestValidation.isGenreExists(request)) {
                    return new Fault("Вы ввели некорректный жанр");

                }
                urlRequest.append("?genres.name=");
                break;
            case RATING:
                if (!requestValidation.isRatingExists(request)) {
                    return new Fault("Вы ввели некорректный рейтинг, введите рейтинг от 1 до 10\n"
                            + "Также можете указать диапазон,например 7.3-9");
                }
                urlRequest.append("?rating.kp=");
                break;
            case YEAR:
                if (!requestValidation.isYearExists(request)) {
                    return new Fault("Вы ввели некорректный год, введите год, либо диапазон, "
                            + "напрмер 2018,2020-2023");
                }
                urlRequest.append("?year=");
                break;
            case RANDOM:
                request = "";
                urlRequest.append("/random?");
                break;
            default:
                return new Fault("Некорректный запрос");
        }
        request = URLEncoder.encode(request, StandardCharsets.UTF_8);
        urlRequest.append(request).append("&notNullFields=name&notNullFields=description&notNullFields=rating.kp");
        final URL url;
        try {
            url = new URL(urlRequest.toString());
        } catch (MalformedURLException e) {
            return new Fault("Ошибка при создании URL");
        }
        HttpURLConnection con;
        try {
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
        } catch (Exception e) {
            return new Fault("Ошибка при установке соединения");
        }
        con.setRequestProperty("X-API-Key", apiKey);
        int responseCode;
        try {
            responseCode = con.getResponseCode();
        } catch (Exception e) {
            return new Fault("Ошибка при получении запроса со стороны API");
        }
        if (responseCode != 200) {
            return new Fault("Сервис временно не доступен");
        }
        BufferedReader reader;
        try {
            reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
        } catch (Exception e) {
            return new Fault("Ошибка при прочтении запроса к API");
        }
        JSONObject response;
        try {
            response = new JSONObject(reader.readLine());
        } catch (Exception e) {
            return new Fault("Ошибка при прочтении запроса к API");
        }

        try {
            reader.close();
        } catch (Exception e) {
            return new Fault("Ошибка при закрытии потока чтения");
        }
        if (!typeOfFilmRequest.equals(TypeOfFilmRequest.RANDOM)) {
            JSONArray docs = new JSONArray(response.getJSONArray("docs"));
            for (int i = 0; i < docs.length(); i++) {
                JSONObject film = docs.getJSONObject(i);
                movies.addFilm(getFilmFromJSONObject(film));
            }
        } else {
            movies.addFilm(getFilmFromJSONObject(response));

        }
        return movies;
    }
}

