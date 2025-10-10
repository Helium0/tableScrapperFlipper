import db.Database;
import db.DatabaseHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;
import pages.TablePage;
import utils.Config;
import java.io.*;
import java.sql.*;

public class RunTime {

    private WebDriver driver;
    private TablePage tablePage;
    private Database database;
    private DatabaseHelper databaseHelper;
    private final String FILE_NAME = "TABLE_NEW.xlsx";
    private final String SHEET_NAME = "MyTable";
    private final String BROWSER = "chrome";
    private final Logger logger = LogManager.getLogger(RunTime.class);


    @Test
    public void scrapDataFromWebsiteAndSaveToExcelAndDatabase() throws IOException, SQLException {
        logger.info("*** APPLICATION HAS STARTED ***");
        driver = DriverManagement.getWebDriver(BROWSER);
        driver.manage().window().maximize();
        driver.get(Config.getWebsiteURL());
        tablePage = new TablePage(driver);
        database = new Database();
        databaseHelper = new DatabaseHelper();

        try (Workbook workbook = new XSSFWorkbook();
             FileOutputStream fileOutputStream = new FileOutputStream(FILE_NAME);
             Connection connection = database.connectionToDatabase()) {

            Sheet sheet = workbook.createSheet(SHEET_NAME);
            tablePage.writeScrappedTableInToExcelFile(sheet);
            workbook.write(fileOutputStream);
            databaseHelper.saveExcelDataToDatabase(connection,sheet);
        } catch (Exception e) {
            logger.info("*** APPLICATION HAS ENDED ***");
            throw e;
        } finally {
            DriverManagement.quitDriver();
        }
    }
}
