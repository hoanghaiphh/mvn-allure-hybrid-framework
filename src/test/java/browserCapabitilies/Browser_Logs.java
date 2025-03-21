package browserCapabitilies;

import commons.GlobalConstants;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chromium.ChromiumDriverLogLevel;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeDriverService;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxDriverLogLevel;
import org.openqa.selenium.firefox.FirefoxDriverService;
import org.openqa.selenium.firefox.GeckoDriverService;
import org.testng.annotations.Test;

import java.io.File;

public class Browser_Logs {
    private WebDriver driver;

    @Test
    public void logsToFile() {
        FirefoxDriverService fService = new GeckoDriverService.Builder()
                .withLogFile(new File(GlobalConstants.BROWSER_LOGS_FOLDER_PATH + "firefoxLogs.log"))
                .build();
        driver = new FirefoxDriver(fService);
        driver.get("https://www.facebook.com/");
        driver.quit();

        ChromeDriverService cService = new ChromeDriverService.Builder()
                .withLogFile(new File(GlobalConstants.BROWSER_LOGS_FOLDER_PATH + "chromeLogs.log"))
                .build();
        driver = new ChromeDriver(cService);
        driver.get("https://www.facebook.com/");
        driver.quit();

        EdgeDriverService eService = new EdgeDriverService.Builder()
                .withLogFile(new File(GlobalConstants.BROWSER_LOGS_FOLDER_PATH + "edgeLogs.log"))
                .build();
        driver = new EdgeDriver(eService);
        driver.get("https://www.facebook.com/");
        driver.quit();
    }

    @Test
    public void logsToConsole() {
        FirefoxDriverService fService = new GeckoDriverService.Builder()
                .withLogOutput(System.out)
                .build();
        driver = new FirefoxDriver(fService);
        driver.get("https://www.facebook.com/");
        driver.quit();

        ChromeDriverService cService = new ChromeDriverService.Builder()
                .withLogOutput(System.out)
                .build();
        driver = new ChromeDriver(cService);
        driver.get("https://www.facebook.com/");
        driver.quit();

        EdgeDriverService eService = new EdgeDriverService.Builder()
                .withLogOutput(System.out)
                .build();
        driver = new EdgeDriver(eService);
        driver.get("https://www.facebook.com/");
        driver.quit();
    }

    @Test
    public void logsWithLevel() {
        System.setProperty(
                GeckoDriverService.GECKO_DRIVER_LOG_PROPERTY,
                GlobalConstants.BROWSER_LOGS_FOLDER_PATH + "firefoxPropertyLogs.log");
        FirefoxDriverService fService = new GeckoDriverService.Builder()
                .withLogLevel(FirefoxDriverLogLevel.DEBUG)
                .build();
        driver = new FirefoxDriver(fService);
        driver.get("https://www.facebook.com/");
        driver.quit();

        System.setProperty(
                ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY,
                GlobalConstants.BROWSER_LOGS_FOLDER_PATH + "chromePropertyLogs.log");
        ChromeDriverService cService = new ChromeDriverService.Builder()
                .withLogLevel(ChromiumDriverLogLevel.DEBUG)
                .build();
        driver = new ChromeDriver(cService);
        driver.get("https://www.facebook.com/");
        driver.quit();

        System.setProperty(
                EdgeDriverService.EDGE_DRIVER_LOG_PROPERTY,
                GlobalConstants.BROWSER_LOGS_FOLDER_PATH + "edgePropertyLogs.log");
        EdgeDriverService eService = new EdgeDriverService.Builder()
                .withLoglevel(ChromiumDriverLogLevel.DEBUG)
                .build();
        driver = new EdgeDriver(eService);
        driver.get("https://www.facebook.com/");
        driver.quit();
    }

    /*public void disableBrowserDriverLogs() {
         Selenium version 4.x: auto disable log in console when running test
         Selenium version 3.x:
         System.setProperty(FirefoxDriver.SystemProperty.DRIVER_USE_MARIONETTE, "true");
         System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, logFilePath);
         System.setProperty("webdriver.chrome.args", "--disable-logging");
         System.setProperty("webdriver.chrome.silentOutput", "true");
    }*/

}
