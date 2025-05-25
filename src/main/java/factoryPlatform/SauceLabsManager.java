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

public class SauceLabsManager implements PlatformFactory {

    private String browserName, browserVersion, osName;

    public SauceLabsManager(String browserName, String browserVersion, String osName) {
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
            default -> throw new BrowserNotSupportedException(browserName);
        };

        HashMap<String, String> sauceOptions = new HashMap<>();
        sauceOptions.put("username", GlobalConstants.SAUCELABS_USERNAME);
        sauceOptions.put("accessKey", GlobalConstants.SAUCELABS_ACCESS_KEY);
        sauceOptions.put("build", "Build " + new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        sauceOptions.put("name", "Run on " + osName + " - " + browserName + " " + browserVersion);
        capabilities.setCapability("sauce:options", sauceOptions);

        try {
            URL sauceLabsUrl = new URL(GlobalConstants.SAUCELABS_URL);
            return new RemoteWebDriver(sauceLabsUrl, capabilities);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
