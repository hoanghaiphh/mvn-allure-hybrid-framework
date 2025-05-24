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

    protected WebDriver initDriver(String platform, String browserName,
                                   String browserVersion, String osName, String osVersion) {

        EnumList.Platform platformList = EnumList.Platform.valueOf(platform.toUpperCase());
        WebDriver driver = switch (platformList) {
            case LOCAL -> new LocalDevice(browserName).initDriver();
            case GRID -> new SeleniumGrid4(browserName, osName).initDriver();
            case BROWSER_STACK -> new BrowserStack(browserName, browserVersion, osName, osVersion).initDriver();
            case SAUCE_LABS -> new SauceLabs(browserName, browserVersion, osName).initDriver();
            case LAMBDA_TEST -> new LambdaTest(browserName, browserVersion, osName).initDriver();
        };

        driverThreadLocal.set(driver);
        return driverThreadLocal.get();
    }

    /*protected WebDriver initDriver(String browserName) {
        EnumList.Browser browserList = EnumList.Browser.valueOf(browserName.toUpperCase());
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

    protected WebDriver initDriverSeleniumGrid(String browserName, String osName) {
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

    protected WebDriver initDriverBrowserStack(String browserName, String browserVersion, String osName, String osVersion) {
        MutableCapabilities capabilities = new MutableCapabilities();
        HashMap<String, Object> bstackOptions = new HashMap<>();
        capabilities.setCapability("browserName", browserName);
        bstackOptions.put("os", osName);
        bstackOptions.put("osVersion", osVersion);
        bstackOptions.put("browserVersion", browserVersion);
        bstackOptions.put("userName", GlobalConstants.BROWSERSTACK_USERNAME);
        bstackOptions.put("accessKey", GlobalConstants.BROWSERSTACK_ACCESS_KEY);
        bstackOptions.put("noBlankPolling", true);
        capabilities.setCapability("bstack:options", bstackOptions);

        WebDriver driver;
        try {
            URL browserStackUrl = new URL(GlobalConstants.BROWSERSTACK_URL);
            driver = new RemoteWebDriver(browserStackUrl, capabilities);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        driverThreadLocal.set(driver);
        return driverThreadLocal.get();
    }

    protected WebDriver initDriverSauceLabs(String browserName, String browserVersion, String platform) {
        MutableCapabilities capabilities;
        BrowserList browserList = BrowserList.valueOf(browserName.toUpperCase());
        switch (browserList) {
            case FIREFOX:
                FirefoxOptions fOptions = new FirefoxOptions();
                fOptions.setPlatformName(platform);
                fOptions.setBrowserVersion(browserVersion);
                capabilities = fOptions;
                break;
            case CHROME:
                ChromeOptions cOptions = new ChromeOptions();
                cOptions.setPlatformName(platform);
                cOptions.setBrowserVersion(browserVersion);
                capabilities = cOptions;
                break;
            case EDGE:
                EdgeOptions eOptions = new EdgeOptions();
                eOptions.setPlatformName(platform);
                eOptions.setBrowserVersion(browserVersion);
                capabilities = eOptions;
                break;
            case SAFARI:
                SafariOptions sOptions = new SafariOptions();
                sOptions.setPlatformName(platform);
                sOptions.setBrowserVersion(browserVersion);
                capabilities = sOptions;
                break;
            default:
                throw new IllegalArgumentException("Browser is not valid!");
        }

        HashMap<String, String> sauceOptions = new HashMap<>();
        sauceOptions.put("username", GlobalConstants.SAUCELABS_USERNAME);
        sauceOptions.put("accessKey", GlobalConstants.SAUCELABS_ACCESS_KEY);
        sauceOptions.put("build", "Build " + new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        sauceOptions.put("name", "Run on " + platform + " - " + browserName + " " + browserVersion);
        capabilities.setCapability("sauce:options", sauceOptions);

        WebDriver driver;
        try {
            URL sauceLabsUrl = new URL(GlobalConstants.SAUCELABS_URL);
            driver = new RemoteWebDriver(sauceLabsUrl, capabilities);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        driverThreadLocal.set(driver);
        return driverThreadLocal.get();
    }

    protected WebDriver initDriverLambdaTest(String browserName, String browserVersion, String platform) {
        MutableCapabilities capabilities;
        BrowserList browserList = BrowserList.valueOf(browserName.toUpperCase());
        switch (browserList) {
            case FIREFOX:
                FirefoxOptions fOptions = new FirefoxOptions();
                fOptions.setPlatformName(platform);
                fOptions.setBrowserVersion(browserVersion);
                capabilities = fOptions;
                break;
            case CHROME:
                ChromeOptions cOptions = new ChromeOptions();
                cOptions.setPlatformName(platform);
                cOptions.setBrowserVersion(browserVersion);
                capabilities = cOptions;
                break;
            case EDGE:
                EdgeOptions eOptions = new EdgeOptions();
                eOptions.setPlatformName(platform);
                eOptions.setBrowserVersion(browserVersion);
                capabilities = eOptions;
                break;
            case SAFARI:
                SafariOptions sOptions = new SafariOptions();
                sOptions.setPlatformName(platform);
                sOptions.setBrowserVersion(browserVersion);
                capabilities = sOptions;
                break;
            default:
                throw new IllegalArgumentException("Browser is not valid!");
        }

        HashMap<String, Object> ltOptions = new HashMap<>();
        ltOptions.put("username", GlobalConstants.LAMBDATEST_USERNAME);
        ltOptions.put("accessKey", GlobalConstants.LAMBDATEST_ACCESS_KEY);
        ltOptions.put("build", "Build " + new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        ltOptions.put("name", "Run on " + platform + " - " + browserName + " " + browserVersion);
        ltOptions.put("resolution", "1920x1080");
        ltOptions.put("project", "live.techpanda.org");
        ltOptions.put("selenium_version", "4.29.0");
        ltOptions.put("w3c", true);
        capabilities.setCapability("LT:Options", ltOptions);

        WebDriver driver;
        try {
            URL lambdaTestUrl = new URL(GlobalConstants.LAMBDATEST_URL);
            driver = new RemoteWebDriver(lambdaTestUrl, capabilities);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        driverThreadLocal.set(driver);
        return driverThreadLocal.get();
    }*/

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
