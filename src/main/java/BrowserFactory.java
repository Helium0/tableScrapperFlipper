import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class BrowserFactory {


    public static WebDriver getWebrowser(String browser) {
        switch (browser.toLowerCase()) {
            case "chrome": return new ChromeDriver();
            case "fireforx": return new FirefoxDriver();
            case "edge": return new InternetExplorerDriver();
            default:
                throw new RuntimeException(browser + "browser failed. Provide correct one");
        }
    }
}
