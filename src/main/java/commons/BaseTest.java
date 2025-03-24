package commons;

import lombok.Getter;
import org.aeonbits.owner.ConfigFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import utilities.OwnerConfig;
import utilities.PropertiesConfig;

import java.net.InetAddress;
import java.net.URL;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;

public class BaseTest {
    @Getter
    private static ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    protected WebDriver initDriver(String browserName) {
        BrowserList browserList = BrowserList.valueOf(browserName.toUpperCase());
        WebDriver driver = switch (browserList) {
            case FIREFOX -> new FirefoxDriver();
            case CHROME -> new ChromeDriver();
            case EDGE -> new EdgeDriver();
            case SAFARI -> new SafariDriver();
            case FIREFOX_HEADLESS -> new FirefoxDriver(new FirefoxOptions().addArguments("--headless"));
            case CHROME_HEADLESS -> new ChromeDriver(new ChromeOptions().addArguments("--headless=new"));
            case EDGE_HEADLESS -> new EdgeDriver(new EdgeOptions().addArguments("--headless=new"));
        };
        driverThreadLocal.set(driver);
        return driverThreadLocal.get();
    }

    protected WebDriver initDriver(String browserName, String osName) {
        OSList osList = OSList.valueOf(osName.toUpperCase());
        Platform platform = switch (osList) {
            case WINDOWS -> Platform.WINDOWS;
            case MAC -> Platform.MAC;
            case LINUX -> Platform.LINUX;
        };

        Capabilities capabilities;
        BrowserList browserList = BrowserList.valueOf(browserName.toUpperCase());
        switch (browserList) {
            case FIREFOX:
                FirefoxOptions fOptions = new FirefoxOptions();
                fOptions.setCapability(CapabilityType.PLATFORM_NAME, platform);
                capabilities = fOptions;
                break;
            case CHROME:
                ChromeOptions cOptions = new ChromeOptions();
                cOptions.setCapability(CapabilityType.PLATFORM_NAME, platform);
                capabilities = cOptions;
                break;
            case EDGE:
                EdgeOptions eOptions = new EdgeOptions();
                eOptions.setCapability(CapabilityType.PLATFORM_NAME, platform);
                capabilities = eOptions;
                break;
            case SAFARI:
                SafariOptions sOptions = new SafariOptions();
                sOptions.setCapability(CapabilityType.PLATFORM_NAME, platform);
                capabilities = sOptions;
                break;
            default:
                throw new IllegalArgumentException("Browser is not valid!");
        }

        WebDriver driver;
        try {
            InetAddress ip = InetAddress.getLocalHost();
            URL gridUrl = new URL("http://" + ip.getHostAddress() + ":4444");
            driver = new RemoteWebDriver(gridUrl, capabilities);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        driverThreadLocal.set(driver);
        return driverThreadLocal.get();
    }

    protected void openUrl(WebDriver driver, String url) {
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(GlobalConstants.LONG_TIMEOUT));
        driver.get(url);
    }

    @BeforeSuite
    protected void clearReports() {
        try {
            Path folderPath = Paths.get(GlobalConstants.ALLURE_RESULTS_FOLDER_PATH);
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(folderPath, entry ->
                    Files.isRegularFile(entry) && !entry.getFileName().toString().equals("environment.properties"))) {
                for (Path entry : stream) {
                    Files.delete(entry);
                }
            }
        } catch (Exception e) {
            log.error("An error occurred while clearing the report", e);
        }
    }

    @AfterClass(alwaysRun = true)
    protected void quitDriver() {
        WebDriver driver = driverThreadLocal.get();
        if (driver != null) {
            String driverInstanceName = driver.toString();
            driver.manage().deleteAllCookies();
            driver.quit();
            log.info("{} was quited.", driverInstanceName);
            driverThreadLocal.remove();
        }
    }

    @AfterSuite
    protected void killAllDrivers() {
        String[] browserDrivers = {"chromedriver", "geckodriver", "msedgedriver", "safaridriver"};
        for (String browserDriver : browserDrivers) {
            String cmd = GlobalConstants.OS_NAME.contains("Window") ?
                    "taskkill /F /FI \"IMAGENAME eq " + browserDriver + "*\"" :
                    "pkill " + browserDriver;
            try {
                Process process = Runtime.getRuntime().exec(cmd);
                process.waitFor();
            } catch (Exception e) {
                log.error("An error occurred while killing browser drivers", e);
            }
        }
        log.info("All browser drivers were killed.");
    }

    protected PropertiesConfig getEnvironmentProperties() {
        String env = System.getProperty("env", "test").toLowerCase();
        return PropertiesConfig.getProperties(env);
    }

    protected OwnerConfig getEnvironmentOwner() {
        String env = System.getProperty("env", "test").toLowerCase();
        ConfigFactory.setProperty("environment", env);
        return ConfigFactory.create(OwnerConfig.class);
    }

    protected final Logger log;

    protected BaseTest() {
        log = LogManager.getLogger(getClass());
    }

}
