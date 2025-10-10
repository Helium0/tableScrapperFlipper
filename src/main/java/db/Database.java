package db;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.Config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private final Logger logger = LogManager.getLogger(Database.class);

    public Connection connectionToDatabase() throws SQLException {
        logger.info("*** CONNECTING TO DATABASE ***");
        try {
            return DriverManager.getConnection(
                    Config.getDataBaseURL(),
                    Config.getDatabaseUser(),
                    Config.getDatabasePassword());
        } catch (SQLException e) {
            logger.error("*** FAILED TO CONNECT TO DATABASE ***");
            throw new SQLException("Failed to connect to database" + Config.getDataBaseURL(), e);
        }
    }
}
