package commons;

import factoryPlatform.*;
import lombok.Getter;
import org.aeonbits.owner.ConfigFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import utilities.OwnerConfig;
import utilities.PropertiesConfig;

import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.regex.Pattern;

public class BaseTest {

    @Getter
    private static ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    protected WebDriver initDriver(String platform, String browserName, String... args) {
        EnumList.Platform platformList = EnumList.Platform.valueOf(platform.toUpperCase());

        WebDriver driver = null;
        switch (platformList) {
            case LOCAL: {
                if (args.length != 0) throw new IllegalArgumentException("Platform parameters are not valid !!!");
                driver = new LocalManager(browserName).initDriver();
                break;
            }
            case GRID: {
                if (args.length != 1) throw new IllegalArgumentException("Platform parameters are not valid !!!");
                String osName = args[0];
                driver = new GridManager(browserName, osName).initDriver();
                break;
            }
            case DOCKER: {
                if (args.length != 1) throw new IllegalArgumentException("Platform parameters are not valid !!!");
                String portNumber = args[0];
                driver = new DockerManager(browserName, portNumber).initDriver();
                break;
            }
            case BROWSER_STACK: {
                if (args.length != 3) throw new IllegalArgumentException("Platform parameters are not valid !!!");
                String browserVersion = args[0], osName = args[1], osVersion = args[2];
                driver = new BrowserStackManager(browserName, browserVersion, osName, osVersion).initDriver();
                break;
            }
            case SAUCE_LABS: {
                if (args.length != 2) throw new IllegalArgumentException("Platform parameters are not valid !!!");
                String browserVersion = args[0], osName = args[1];
                driver = new SauceLabsManager(browserName, browserVersion, osName).initDriver();
                break;
            }
            case LAMBDA_TEST: {
                if (args.length != 2) throw new IllegalArgumentException("Platform parameters are not valid !!!");
                String browserVersion = args[0], osName = args[1];
                driver = new LambdaTestManager(browserName, browserVersion, osName).initDriver();
                break;
            }
        }

        driverThreadLocal.set(driver);
        return driverThreadLocal.get();
    }

    protected void configBrowserAndOpenUrl(WebDriver driver, String url) {
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

    protected String getCurrentBrowserName() {
        WebDriver driver = driverThreadLocal.get();
        String browserName = ((RemoteWebDriver) driver).getCapabilities().getBrowserName().toUpperCase();
        if (browserName.contains("EDGE")) browserName = "EDGE";
        return browserName;
    }

    protected long getRandomNumber(int min, int max) {
        Random rnd = new Random();
        return min + rnd.nextInt(max - min);
    }

    protected long getEpochTimeMillis() {
        return Calendar.getInstance().getTimeInMillis();
    }

    protected String getRandomEmailByCurrentState(String prefix) {
        String timestamp = new SimpleDateFormat("_yyMMdd.HHmmss_").format(new Date());
        String browserName = getCurrentBrowserName().toLowerCase();
        return removeDiacritics(prefix) + timestamp + browserName + "@gmail.com";
    }

    private String removeDiacritics(String str) {
        str = str.replace("Đ", "D").replace("đ", "d");
        String normalized = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(normalized).replaceAll("").toLowerCase();
    }

}
