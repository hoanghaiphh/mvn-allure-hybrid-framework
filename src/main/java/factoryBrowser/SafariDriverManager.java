package factoryBrowser;

import commons.GlobalConstants;
import lombok.Getter;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

public class SafariDriverManager implements BrowserFactory {

    @Getter
    private SafariOptions options = new SafariOptions();

    public SafariDriverManager() {
        checkOS();
    }

    public SafariDriverManager(Platform osName) {
        checkOS();
        options.setCapability(CapabilityType.PLATFORM_NAME, osName);
    }

    public SafariDriverManager(String osName, String browserVersion) {
        checkOS();
        options.setPlatformName(osName);
        options.setBrowserVersion(browserVersion);
    }

    @Override
    public WebDriver initDriver() {
        return new SafariDriver(options);
    }

    private void checkOS() {
        if (!GlobalConstants.OS_NAME.toUpperCase().contains("MAC")) throw new BrowserNotSupportedException("Safari");
    }

}
