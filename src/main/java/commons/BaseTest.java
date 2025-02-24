package commons;

import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import lombok.Getter;
import org.aeonbits.owner.ConfigFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import utilities.EnvironmentConfig;
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
    protected WebDriver driver;

    protected final Logger log;

    protected BaseTest() {
        log = LogManager.getLogger(getClass());
    }

    protected WebDriver openBrowserAndNavigateToUrl(String browserName, String url) {
        driver = createDriver(browserName);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(GlobalConstants.LONG_TIMEOUT));
        driver.get(url);
        return driver;
    }

    private WebDriver createDriver(String browserName) {
        BrowserList browserList = BrowserList.valueOf(browserName.toUpperCase());
        return switch (browserList) {
            case FIREFOX -> new FirefoxDriver();
            case CHROME -> new ChromeDriver();
            case EDGE -> new EdgeDriver();
            case FIREFOX_HEADLESS -> new FirefoxDriver(new FirefoxOptions().addArguments("--headless"));
            case CHROME_HEADLESS -> new ChromeDriver(new ChromeOptions().addArguments("--headless=new"));
            case EDGE_HEADLESS -> new EdgeDriver(new EdgeOptions().addArguments("--headless=new"));
            case CHROME_PROFILE -> new ChromeDriver(new ChromeOptions()
                    .addArguments("--user-data-dir=C:\\Users\\HAIPH\\AppData\\Local\\Google\\Chrome\\User Data\\")
                    .addArguments("--profile-directory=Default"));
            default -> throw new RuntimeException("Browser is not valid");
        };
    }

    protected PropertiesConfig getEnvironment() {
        String env = System.getProperty("env", "test").toLowerCase();
        return PropertiesConfig.getProperties(env);
    }

    protected EnvironmentConfig getEnvironmentOwner() {
        String env = System.getProperty("env", "test").toLowerCase();
        ConfigFactory.setProperty("environment", env);
        return ConfigFactory.create(EnvironmentConfig.class);
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
        if (driver != null) {
            String driverInstanceName = driver.toString();
            driver.manage().deleteAllCookies();
            driver.quit();
            log.info("{} was quited.", driverInstanceName);
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

    /*protected void closeBrowserDriver() {
        String driverInstanceName = driver.toString();
        log.info("OS name = {}", GlobalConstants.OS_NAME);
        log.info("Driver instance name = {}", driverInstanceName);

        String browserDriverName = null;
        if (driverInstanceName.contains("Chrome")) {
            browserDriverName = "chromedriver";
        } else if (driverInstanceName.contains("Firefox")) {
            browserDriverName = "geckodriver";
        } else if (driverInstanceName.contains("Edge")) {
            browserDriverName = "msedgedriver";
        } else if (driverInstanceName.contains("Opera")) {
            browserDriverName = "operadriver";
        } else if (driverInstanceName.contains("Safari")) {
            browserDriverName = "safaridriver";
        }

        String cmd = null;
        if (GlobalConstants.OS_NAME.contains("Window")) {
            cmd = "taskkill /F /FI \"IMAGENAME eq " + browserDriverName + "*\"";
        } else {
            cmd = "pkill " + browserDriverName;
        }

        if (driver != null) {
            driver.manage().deleteAllCookies();
            driver.quit();
        }

        try {
            Process process = Runtime.getRuntime().exec(cmd);
            process.waitFor();
        } catch (Exception e) {
            log.error("An error occurred while closing the browser driver", e);
        }
    }*/

    protected boolean verifyTrue(boolean condition) {
        boolean status = true;
        try {
            Assert.assertTrue(condition);
            verificationPassed("");
        } catch (Throwable t) {
            status = false;
            VerificationFailures.getFailures().addFailureForTest(Reporter.getCurrentTestResult(), t);
            Reporter.getCurrentTestResult().setThrowable(t);
            verificationFailed(t.getMessage());
        }
        return status;
    }

    protected boolean verifyFalse(boolean condition) {
        boolean status = true;
        try {
            Assert.assertFalse(condition);
            verificationPassed("");
        } catch (Throwable t) {
            status = false;
            VerificationFailures.getFailures().addFailureForTest(Reporter.getCurrentTestResult(), t);
            Reporter.getCurrentTestResult().setThrowable(t);
            verificationFailed(t.getMessage());
        }
        return status;
    }

    protected boolean verifyEquals(Object actual, Object expected) {
        boolean status = true;
        try {
            Assert.assertEquals(actual, expected);
            verificationPassed(": value = " + expected);
        } catch (Throwable t) {
            status = false;
            VerificationFailures.getFailures().addFailureForTest(Reporter.getCurrentTestResult(), t);
            Reporter.getCurrentTestResult().setThrowable(t);
            verificationFailed(t.getMessage());
        }
        return status;
    }

    @Step("=== VERIFICATION PASSED{0}")
    private void verificationPassed(Object value) {
    }

    @Step("=== VERIFICATION FAILED: {0}")
    private void verificationFailed(String failureMessage) {
        allureAttachScreenshot();
    }

    @Attachment(value = "verification failure screenshot", type = "image/png")
    private byte[] allureAttachScreenshot() {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    protected static class DataGeneration {

        public static long getRandomNumber(int min, int max) {
            Random rnd = new Random();
            return min + rnd.nextInt(max - min);
        }

        public static long getRandomNumberByDateTime() {
            return Calendar.getInstance().getTimeInMillis(); // Unix Epoch
        }

        public static String getRandomEmailByTimestamp(String prefix, WebDriver driver) {
            SimpleDateFormat sdf = new SimpleDateFormat("_yyyyMMdd_HHmmss_");
            String timestamp = sdf.format(new Date());
            return removeDiacritics(prefix) + timestamp
                    + BasePage.getCurrentBrowserName(driver).toLowerCase() + "@gmail.com";
        }

        private static String removeDiacritics(String str) {
            str = str.replace("Đ", "D").replace("đ", "d");
            String normalized = Normalizer.normalize(str, Normalizer.Form.NFD);
            Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
            return pattern.matcher(normalized).replaceAll("").toLowerCase();
        }
    }

}
