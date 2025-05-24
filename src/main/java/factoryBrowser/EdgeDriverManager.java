package factoryBrowser;

import lombok.Getter;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.CapabilityType;

public class EdgeDriverManager implements BrowserFactory {

    @Getter
    private EdgeOptions options = new EdgeOptions();

    public EdgeDriverManager() {
    }

    public EdgeDriverManager(String mode) {
        if (mode.equalsIgnoreCase("headless")) {
            options.addArguments("--headless=new");
            options.addArguments("--window-size=1024,768");
        }
    }

    public EdgeDriverManager(Platform osName) {
        options.setCapability(CapabilityType.PLATFORM_NAME, osName);
    }

    public EdgeDriverManager(String osName, String browserVersion) {
        options.setPlatformName(osName);
        options.setBrowserVersion(browserVersion);
    }

    @Override
    public WebDriver initDriver() {
        return new EdgeDriver(options);
    }

}
