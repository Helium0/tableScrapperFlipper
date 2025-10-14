import db.Database;
import org.testng.annotations.Test;
import java.sql.Connection;
import java.sql.SQLException;

import static org.testng.Assert.*;


public class DataBaseTests {

    private Database database = new Database();


    @Test
    public void databaseConnectionShouldBeOpen() throws SQLException {
        Connection connection = database.connectionToDatabase();

        assertFalse(connection.isClosed());
        connection.close();
    }

    @Test
    public void databaseConnectionShouldBeClosed() throws SQLException {
        Connection connection = database.connectionToDatabase();

        connection.isClosed();
        assertTrue(connection.isClosed());
    }


}
