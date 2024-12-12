package bot;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection {
    private static String URL;
    private static String USER;
    private static String PASSWORD;

    {
        try {
            BufferedReader URLReader = new BufferedReader(new FileReader("url.txt"));
            URL = URLReader.readLine();
            BufferedReader USERReader = new BufferedReader(new FileReader("user.txt"));
            USER = USERReader.readLine();
            BufferedReader PASSWORDReader = new BufferedReader(new FileReader("password.txt"));
            PASSWORD = PASSWORDReader.readLine();
        } catch (IOException e) {
            throw new RuntimeException("Файл не найден");
        }
    }

    public Connection connect() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.err.println("Ошибка подключения: " + e.getMessage());
            return null;
        }
    }
}

