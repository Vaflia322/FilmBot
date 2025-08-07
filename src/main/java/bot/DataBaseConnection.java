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
            BufferedReader bufferedReader = new BufferedReader(new FileReader("base.txt"));
            URL = bufferedReader.readLine();
            USER = bufferedReader.readLine();
            PASSWORD = bufferedReader.readLine();
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

