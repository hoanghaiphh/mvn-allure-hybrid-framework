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
    }

    public SafariDriverManager(Platform osName) {
        options.setCapability(CapabilityType.PLATFORM_NAME, osName);
    }

    public SafariDriverManager(String osName, String browserVersion) {
        options.setPlatformName(osName);
        options.setBrowserVersion(browserVersion);
    }

    @Override
    public WebDriver initDriver() {
        if (!GlobalConstants.OS_NAME.toUpperCase().contains("MAC"))
            throw new IllegalStateException("Safari is not supported on " + GlobalConstants.OS_NAME);
        return new SafariDriver(options);
    }

}
