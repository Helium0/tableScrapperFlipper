import db.Database;
import interfaces.ConnectionProvider;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.Config;

import java.sql.Connection;
import java.sql.SQLException;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.testng.Assert.*;


public class DataBaseTests {

    private ConnectionProvider mockConnectionProvider;
    private Database database;

    @BeforeMethod
    public void setUp() {
        mockConnectionProvider = mock(ConnectionProvider.class);
        database = new Database(mockConnectionProvider);
    }


    @Test
    public void databaseConnectionShouldBeOpen() throws SQLException {
        Connection mockConnection = mock(Connection.class);

        when(mockConnection.isClosed()).thenReturn(false);
        when(mockConnectionProvider.getDatabaseConnection(anyString(),anyString(),anyString()))
                .thenReturn(mockConnection);

        Connection connection = database.connectionToDatabase();
        assertFalse(connection.isClosed());
        verify(mockConnectionProvider).getDatabaseConnection(
                Config.getDataBaseURL(),
                Config.getDatabaseUser(),
                Config.getDatabasePassword()
        );
    }

    @Test
    public void databaseConnectionShouldBeClosed() throws SQLException {
        Connection mockConnection = mock(Connection.class);
        when(mockConnection.isClosed())
                .thenReturn(false)
                .thenReturn(true);

        when(mockConnectionProvider.getDatabaseConnection(anyString(), anyString(), anyString()))
                .thenReturn(mockConnection);

        Connection connection = database.connectionToDatabase();
        assertFalse(connection.isClosed());
        connection.close();
        assertTrue(connection.isClosed());
        verify(mockConnection).close();
    }

    @Test(expectedExceptions = SQLException.class)
    public void shouldThrowExceptionWhenConnectsFail() throws SQLException {
        when(mockConnectionProvider.getDatabaseConnection(anyString(),anyString(),anyString()))
                .thenThrow(new SQLException("Connection failed"));

        database.connectionToDatabase();

    }
}
