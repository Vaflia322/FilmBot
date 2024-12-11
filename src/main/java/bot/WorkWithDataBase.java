package bot;

import java.sql.*;
import java.util.*;

public class WorkWithDataBase {
    DataBaseConnection dataBaseConnection = new DataBaseConnection();

    public void createUser(long userID) {
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        String insertSQL = "INSERT INTO users (userid, blacklist, viewed, wishlist) VALUES (?, ?, ?, ?)";

        try (Connection connection = dataBaseConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {


            String[] blacklist = {};


            String[] viewed = {};
            String[] wishlist = {};


            preparedStatement.setLong(1, userID);
            preparedStatement.setArray(2, connection.createArrayOf("text", blacklist));
            preparedStatement.setArray(3, connection.createArrayOf("text", viewed));
            preparedStatement.setArray(4, connection.createArrayOf("text", wishlist));


            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Boolean checkUserExists(long userID) {
        String existsSQL = "SELECT EXISTS(SELECT 1 FROM users WHERE userid = ?)";

        try (Connection connection = dataBaseConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(existsSQL)) {

            preparedStatement.setLong(1, userID);

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

    public void addFilmToBlackList(Film film, long userID) {
        String updateSQL = "UPDATE users SET blacklist = array_append(blacklist, ?) WHERE userid = ?";

        try (Connection connection = dataBaseConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {

            String newItem = film.name();

            preparedStatement.setString(1, newItem);
            preparedStatement.setLong(2, userID);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addFilmToViewed(String filmName, long userID) {
        String updateSQL = "UPDATE users SET viewed = array_append(viewed, ?) WHERE userid = ?";

        try (Connection connection = dataBaseConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {

            preparedStatement.setString(1, filmName);
            preparedStatement.setLong(2, userID);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addFilmToWishList(Film film, long userID) {
        String updateSQL = "UPDATE users SET wishlist = array_append(wishlist, ?) WHERE userid = ?";

        try (Connection connection = dataBaseConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {

            String newItem = film.name();

            preparedStatement.setString(1, newItem);
            preparedStatement.setLong(2, userID);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Map<String, Set<String>> getUserData(long userID) {
        String selectSQL = "SELECT * FROM users WHERE userID = ?";
        Map<String, Set<String>> result = new HashMap<>();
        try (Connection connection = dataBaseConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {

            preparedStatement.setLong(1, userID);

            ResultSet resultSet = preparedStatement.executeQuery();


            if (resultSet.next()) {
                Array blackListArray = resultSet.getArray("blacklist");
                Set<String> blacklist = (blackListArray != null) ? new HashSet<>(List.of((String[])
                        blackListArray.getArray())) : new HashSet<>();


                Array viewedArray = resultSet.getArray("viewed");
                Set<String> viewed = (viewedArray != null) ? new HashSet<>(List.of((String[])
                        viewedArray.getArray())) : new HashSet<>();


                Array wishlistArray = resultSet.getArray("wishlist");
                Set<String> wishlist = (wishlistArray != null) ? new HashSet<>(List.of((String[])
                        wishlistArray.getArray())) : new HashSet<>();
                result.put("blacklist", blacklist);
                result.put("viewed", viewed);
                result.put("wishlist", wishlist);
            }
            return result;

        } catch (SQLException e) {
            return null;
        }
    }
}
