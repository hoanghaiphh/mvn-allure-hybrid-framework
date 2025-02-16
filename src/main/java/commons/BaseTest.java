package commons;

import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import lombok.Getter;
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
import org.testng.annotations.BeforeSuite;

import java.io.File;
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


    /*** Init browser and navigate to URL ***/

    protected WebDriver getBrowserDriver(String browserName) {
        BrowserList browserList = BrowserList.valueOf(browserName.toUpperCase());
        switch (browserList) {
            case FIREFOX:
                driver = new FirefoxDriver();
                break;
            case CHROME:
                driver = new ChromeDriver();
                break;
            case EDGE:
                driver = new EdgeDriver();
                break;
            case FIREFOX_HEADLESS:
                driver = new FirefoxDriver(new FirefoxOptions().addArguments("--headless"));
                break;
            case CHROME_HEADLESS:
                driver = new ChromeDriver(new ChromeOptions().addArguments("--headless=new"));
                break;
            case EDGE_HEADLESS:
                driver = new EdgeDriver(new EdgeOptions().addArguments("--headless=new"));
                break;
            case CHROME_PROFILE:
                driver = new ChromeDriver(new ChromeOptions()
                        .addArguments("--user-data-dir=C:\\Users\\HAIPH\\AppData\\Local\\Google\\Chrome\\User Data\\")
                        .addArguments("--profile-directory=Default"));
                break;
            default:
                throw new RuntimeException("Browser is not valid");
        }
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(GlobalConstants.LONG_TIMEOUT));
        return driver;
    }

    protected WebDriver openBrowserWithUrl(String browserName, String url) {
        driver = getBrowserDriver(browserName);
        driver.get(url);
        return driver;
    }


    /*** Before & After running ***/

    @BeforeSuite
    protected void clearReport() {
        try {
            File[] listOfFiles = new File(GlobalConstants.ALLURE_RESULTS_FOLDER_PATH).listFiles();
            for (File file : listOfFiles) {
                if (file.isFile() && !file.getName().equals("environment.properties")) {
                    file.delete();
                }
            }
        } catch (Exception e) {
            log.error("An error occurred while clearing the report", e);
        }
    }

    @AfterClass(alwaysRun = true)
    protected void closeBrowserDriver() {
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
    }


    /*** Modify verification methods ***/

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


    /*** Generate random test data ***/

    protected long getRandomNumber(int min, int max) {
        Random rnd = new Random();
        return min + rnd.nextInt(max - min);
    }

    protected long getRandomNumberByDateTime() {
        return Calendar.getInstance().getTimeInMillis(); // Unix Epoch
    }

    protected String getRandomEmailByTimestamp(String prefix, WebDriver driver) {
        SimpleDateFormat sdf = new SimpleDateFormat("_yyyyMMdd_HHmmss_");
        String timestamp = sdf.format(new Date());
        return removeDiacritics(prefix) + timestamp
                + BasePage.getCurrentBrowserName(driver).toLowerCase() + "@gmail.com";
    }

    private String removeDiacritics(String str) {
        str = str.replace("Đ", "D").replace("đ", "d");
        String normalized = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(normalized).replaceAll("").toLowerCase();
    }

}
