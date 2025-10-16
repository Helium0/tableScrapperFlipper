package db;

import interfaces.ConnectionProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.Config;

import java.sql.Connection;
import java.sql.SQLException;

public class Database {

    private ConnectionProvider connectionProvider;
    private final Logger logger = LogManager.getLogger(Database.class);

    public Database(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }


    public Connection connectionToDatabase() throws SQLException {
        logger.info("*** CONNECTING TO DATABASE ***");
        try {
            return connectionProvider.getDatabaseConnection(
                    Config.getDataBaseURL(),
                    Config.getDatabaseUser(),
                    Config.getDatabasePassword());
        } catch (SQLException e) {
            logger.error("*** FAILED TO CONNECT TO DATABASE ***");
            throw new SQLException("Failed to connect to database" + Config.getDataBaseURL(), e);
        }
    }
}
