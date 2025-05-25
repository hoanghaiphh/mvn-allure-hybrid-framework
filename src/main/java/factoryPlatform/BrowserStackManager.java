package factoryPlatform;

import commons.GlobalConstants;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.util.HashMap;

public class BrowserStackManager implements PlatformFactory {

    private String browserName, browserVersion, osName, osVersion;

    public BrowserStackManager(String browserName, String browserVersion, String osName, String osVersion) {
        this.browserName = browserName;
        this.browserVersion = browserVersion;
        this.osName = osName;
        this.osVersion = osVersion;
    }

    @Override
    public WebDriver initDriver() {
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

        try {
            URL browserStackUrl = new URL(GlobalConstants.BROWSERSTACK_URL);
            return new RemoteWebDriver(browserStackUrl, capabilities);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
