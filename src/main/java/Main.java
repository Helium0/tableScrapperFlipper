import interfaces.ConnectionProvider;
import java.sql.DriverManager;


public class Main {
    public static void main(String[] args) {

        ConnectionProvider connectionProvider = (url, user, password) -> DriverManager.getConnection(url,user,password);

        DataScrapper dataScrapperApp = new DataScrapper(connectionProvider);
        dataScrapperApp.scrapDataFromWebsiteAndSaveToExcelAndDatabase();
    }
}
