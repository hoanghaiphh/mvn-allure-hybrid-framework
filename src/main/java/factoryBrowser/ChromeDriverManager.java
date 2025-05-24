package factoryBrowser;

import lombok.Getter;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;

public class ChromeDriverManager implements BrowserFactory {

    @Getter
    private ChromeOptions options = new ChromeOptions();

    public ChromeDriverManager() {
    }

    public ChromeDriverManager(String mode) {
        if (mode.equalsIgnoreCase("headless")) {
            options.addArguments("--headless=new");
            options.addArguments("--window-size=1024,768");
        }
    }

    public ChromeDriverManager(Platform osName) {
        options.setCapability(CapabilityType.PLATFORM_NAME, osName);
    }

    public ChromeDriverManager(String osName, String browserVersion) {
        options.setPlatformName(osName);
        options.setBrowserVersion(browserVersion);
    }

    @Override
    public WebDriver initDriver() {
        return new ChromeDriver(options);
    }

}
