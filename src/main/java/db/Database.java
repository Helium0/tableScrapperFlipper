package db;

import utils.Config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    public static Connection connectionToDatabase() throws SQLException {
        try {
            return DriverManager.getConnection(
                    Config.getDataBaseURL(),
                    Config.getDatabaseUser(),
                    Config.getDatabasePassword());
        } catch (SQLException e) {
            throw new SQLException("Failed to connect to database" + Config.getDataBaseURL(), e);
        }
    }
}
