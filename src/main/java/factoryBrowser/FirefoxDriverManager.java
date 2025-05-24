package factoryBrowser;

import lombok.Getter;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.CapabilityType;

public class FirefoxDriverManager implements BrowserFactory {

    @Getter
    private FirefoxOptions options = new FirefoxOptions();

    public FirefoxDriverManager() {
    }

    public FirefoxDriverManager(String mode) {
        if (mode.equalsIgnoreCase("headless")) {
            options.addArguments("--headless");
            options.addArguments("--width=1024");
            options.addArguments("--height=768");
        }
    }

    public FirefoxDriverManager(Platform osName) {
        options.setCapability(CapabilityType.PLATFORM_NAME, osName);
    }

    public FirefoxDriverManager(String osName, String browserVersion) {
        options.setPlatformName(osName);
        options.setBrowserVersion(browserVersion);
    }

    @Override
    public WebDriver initDriver() {
        return new FirefoxDriver(options);
    }

}
