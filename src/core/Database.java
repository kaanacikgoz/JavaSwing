package core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    public static Connection connection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/example",
                    "postgres",
                    "1234" // It's not real password
            );
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return connection;
    }

}
