import org.openqa.selenium.WebDriver;

public class DriverManagement {

    private static WebDriver webDriver;


    private DriverManagement() {

    }

    public static WebDriver getWebDriver(String browser) {
        if(webDriver == null) {
            webDriver = BrowserFactory.getWebrowser(browser);
        }
        return webDriver;
    }

    public static void quitDriver() {
        if(webDriver != null) {
            webDriver.quit();
            webDriver = null;
        }
    }
}
