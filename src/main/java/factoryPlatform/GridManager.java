package factoryPlatform;

import commons.EnumList;
import factoryBrowser.*;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.InetAddress;
import java.net.URL;

public class GridManager implements PlatformFactory {

    private String browserName, osName;

    public GridManager(String browserName, String osName) {
        this.browserName = browserName;
        this.osName = osName;
    }

    @Override
    public WebDriver initDriver() {
        EnumList.OS osList = EnumList.OS.valueOf(osName.toUpperCase());
        Platform platform = switch (osList) {
            case WINDOWS -> Platform.WINDOWS;
            case MAC -> Platform.MAC;
            case LINUX -> Platform.LINUX;
        };

        EnumList.Browser browserList = EnumList.Browser.valueOf(browserName.toUpperCase());
        Capabilities capabilities = switch (browserList) {
            case FIREFOX -> new FirefoxDriverManager(platform).getOptions();
            case CHROME -> new ChromeDriverManager(platform).getOptions();
            case EDGE -> new EdgeDriverManager(platform).getOptions();
            case SAFARI -> new SafariDriverManager(platform).getOptions();
            default -> throw new BrowserNotSupportedException(browserName);
        };

        try {
            InetAddress ip = InetAddress.getLocalHost();
            URL gridUrl = new URL("http://" + ip.getHostAddress() + ":4444");
            return new RemoteWebDriver(gridUrl, capabilities);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
