package interfaces;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionProvider {

    Connection getDatabaseConnection(String url, String user, String password) throws SQLException;
}
