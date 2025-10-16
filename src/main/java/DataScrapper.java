import db.Database;
import db.DatabaseHelper;
import interfaces.ConnectionProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import pages.TablePage;
import utils.Config;
import java.io.*;
import java.sql.*;

public class DataScrapper {

    private WebDriver driver;
    private Database database;
    private DatabaseHelper databaseHelper;
    private final ConnectionProvider connectionProvider;
    private final String FILE_NAME = "TABLE_NEW.xlsx";
    private final String SHEET_NAME = "MyTable";
    private final String BROWSER = "chrome";
    private final Logger logger = LogManager.getLogger(DataScrapper.class);

    public DataScrapper(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
        this.database = new Database(connectionProvider);
        this.databaseHelper = new DatabaseHelper();
    }


    public void scrapDataFromWebsiteAndSaveToExcelAndDatabase() {
        logger.info("*** APPLICATION HAS STARTED ***");
        driver = DriverManagement.getWebDriver(BROWSER);
        driver.manage().window().maximize();
        driver.get(Config.getWebsiteURL());

        try (Workbook workbook = new XSSFWorkbook();
             FileOutputStream fileOutputStream = new FileOutputStream(FILE_NAME);
             Connection connection = database.connectionToDatabase()) {
             TablePage tablePage = new TablePage(driver);

             Sheet sheet = workbook.createSheet(SHEET_NAME);
             tablePage.writeScrappedTableInToExcelFile(sheet);
             workbook.write(fileOutputStream);
             databaseHelper.saveExcelDataToDatabase(connection,sheet);
        } catch (Exception e) {
            logger.info("Application failed", e);
            throw new RuntimeException(e);
        } finally {
            DriverManagement.quitDriver();
            logger.info("*** APPLICATION HAS ENDED ***");
        }
    }
}