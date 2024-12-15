package bot;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class FilmsDataBaseQueries {
    DataBaseConnection dataBaseConnection = new DataBaseConnection();

    public Boolean checkFilmExists(String name) {
        String existsSQL = "SELECT EXISTS(SELECT 1 FROM films WHERE name = ?)";

        try (Connection connection = dataBaseConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(existsSQL)) {

            preparedStatement.setString(1, name);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getBoolean(1);
            } else {
                return false;
            }

        } catch (SQLException e) {
            return null;
        }
    }

    public void addFilm(Film film) {
        if (checkFilmExists(film.name())) {
            return;
        }
        String insertSQL = "INSERT INTO films (name, rating, year, genre, description) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = dataBaseConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
            preparedStatement.setString(1, film.name());
            preparedStatement.setFloat(2, Float.parseFloat(film.rating()));
            preparedStatement.setInt(3, Integer.parseInt(film.year()));
            preparedStatement.setArray(4, connection.createArrayOf("text", film.genres()
                    .toArray(new String[0])));
            preparedStatement.setString(5, film.description());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ApiObject getFilms(TypeOfFilmRequest typeOfFilmRequest, String request) {
        Movies movies = new Movies();
        String query = "";
        RequestValidation requestValidation = new RequestValidation();
        switch (typeOfFilmRequest) {
            case NAME:
                if (!requestValidation.isNameExists(request)) {
                    return new Fault("Вы ввели некорректное название, оно должно быть на РУССКОМ ЯЗЫКЕ");
                }
                query = "SELECT * FROM films WHERE name = " + request;
                break;
            case GENRE:
                if (!requestValidation.isGenreExists(request)) {
                    return new Fault("Вы ввели некорректный жанр");

                }
                query = "SELECT * FROM films WHERE " + "\'" + request + "\'" + " = ANY(genre)";
                break;
            case RATING:
                if (!requestValidation.isRatingExists(request)) {
                    return new Fault("Вы ввели некорректный рейтинг, введите рейтинг от 1 до 10\n"
                            + "Также можете указать диапазон,например 7.3-9");
                }
                if (request.contains("-")) {
                    String minRating = request.split("-")[0];
                    String maxRating = request.split("-")[1];
                    query = "SELECT * FROM films WHERE rating BETWEEN " + minRating + " AND " + maxRating;
                } else {
                    query = "SELECT * FROM films WHERE rating = " + request;
                }
                break;
            case YEAR:
                if (!requestValidation.isYearExists(request)) {
                    return new Fault("Вы ввели некорректный год, введите год, либо диапазон, "
                            + "напрмер 2018,2020-2023");
                }
                if (request.contains("-")) {
                    String minYear = request.split("-")[0];
                    String maxYear = request.split("-")[1];
                    query = "SELECT * FROM films WHERE rating BETWEEN " + minYear + " AND " + maxYear;
                } else {
                    query = "SELECT * FROM films WHERE year = " + request;
                }
                break;
        }
        try (Connection connection = dataBaseConnection.connect();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                Float rating = resultSet.getFloat("rating");
                Integer year = resultSet.getInt("year");
                Array genreArray = resultSet.getArray("genre");
                List<String> genre = (genreArray != null)
                        ? Arrays.asList((String[]) genreArray.getArray())
                        : List.of();
                String description = resultSet.getString("description");
                movies.addFilm(new Film(name, description, String.valueOf(rating), genre, String.valueOf(year)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (movies.getFilms().isEmpty() || movies.getFilms().size() < 10) {
            return null;
        }
        return movies;
    }
}
