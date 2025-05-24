package factoryPlatform;

import commons.EnumList;
import commons.GlobalConstants;
import factoryBrowser.*;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class LambdaTest implements PlatformFactory {

    private String browserName, browserVersion, osName;

    public LambdaTest(String browserName, String browserVersion, String osName) {
        this.browserName = browserName;
        this.browserVersion = browserVersion;
        this.osName = osName;
    }

    @Override
    public WebDriver initDriver() {
        EnumList.Browser browserList = EnumList.Browser.valueOf(browserName.toUpperCase());
        MutableCapabilities capabilities = switch (browserList) {
            case FIREFOX -> new FirefoxDriverManager(osName, browserVersion).getOptions();
            case CHROME -> new ChromeDriverManager(osName, browserVersion).getOptions();
            case EDGE -> new EdgeDriverManager(osName, browserVersion).getOptions();
            case SAFARI -> new SafariDriverManager(osName, browserVersion).getOptions();
            default -> throw new IllegalArgumentException("Browser " + browserName.toUpperCase() + " is not valid!");
        };

        HashMap<String, Object> ltOptions = new HashMap<>();
        ltOptions.put("username", GlobalConstants.LAMBDATEST_USERNAME);
        ltOptions.put("accessKey", GlobalConstants.LAMBDATEST_ACCESS_KEY);
        ltOptions.put("build", "Build " + new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        ltOptions.put("name", "Run on " + osName + " - " + browserName + " " + browserVersion);
        ltOptions.put("resolution", "1920x1080");
        ltOptions.put("project", "live.techpanda.org");
        ltOptions.put("selenium_version", "4.29.0");
        ltOptions.put("w3c", true);
        capabilities.setCapability("LT:Options", ltOptions);

        try {
            URL lambdaTestUrl = new URL(GlobalConstants.LAMBDATEST_URL);
            return new RemoteWebDriver(lambdaTestUrl, capabilities);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
