import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.logging.Level;

import static org.testng.Assert.assertFalse;

public class JavaScriptErrorTests {

    private WebDriver driver;

    @BeforeMethod
    public void setUp() {
        ChromeOptions chromeOptions = new ChromeOptions();
        LoggingPreferences loggingPreferences = new LoggingPreferences();
        loggingPreferences.enable(LogType.BROWSER, Level.ALL);
        chromeOptions.setCapability(ChromeOptions.LOGGING_PREFS, loggingPreferences);
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver(chromeOptions);
        driver.manage().window().maximize();
        driver.get("https://theinternet.przyklady.javastart.pl/javascript_error");
    }

    @Test
    public void javaScriptAlertPromptTest() {

        LogEntries browserLogs = driver.manage().logs().get(LogType.BROWSER);
        boolean isErrorLog = false;

        for (LogEntry browserLog : browserLogs) {
            if (isErrorPresent(browserLog)) {
                System.out.println("Error message detected: " + browserLog.getMessage());
                isErrorLog = true;
            }
        }
        assertFalse(isErrorLog);
    }

    private boolean isErrorPresent(LogEntry browserLog) {
        return browserLog.getMessage().contains("TypeError");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}