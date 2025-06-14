package factoryPlatform;

import commons.EnumList;
import factoryBrowser.*;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;

public class DockerManager implements PlatformFactory {

    private String browserName, portNumber;

    public DockerManager(String browserName, String portNumber) {
        this.browserName = browserName;
        this.portNumber = portNumber;
    }

    @Override
    public WebDriver initDriver() {
        EnumList.Browser browserList = EnumList.Browser.valueOf(browserName.toUpperCase());
        Capabilities capabilities = switch (browserList) {
            case FIREFOX -> new FirefoxDriverManager().getOptions();
            case CHROME -> new ChromeDriverManager().getOptions();
            case EDGE -> new EdgeDriverManager().getOptions();
            default -> throw new BrowserNotSupportedException(browserName);
        };

        try {
            return new RemoteWebDriver(new URL("http://localhost:" + portNumber + "/wd/hub"), capabilities);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
