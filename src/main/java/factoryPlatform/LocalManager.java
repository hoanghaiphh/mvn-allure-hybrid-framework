package factoryPlatform;

import commons.EnumList;
import factoryBrowser.*;
import org.openqa.selenium.WebDriver;

public class LocalManager implements PlatformFactory {

    private String browserName;

    public LocalManager(String browserName) {
        this.browserName = browserName;
    }

    @Override
    public WebDriver initDriver() {
        EnumList.Browser browserList = EnumList.Browser.valueOf(browserName.toUpperCase());
        return switch (browserList) {
            case FIREFOX -> new FirefoxDriverManager().initDriver();
            case CHROME -> new ChromeDriverManager().initDriver();
            case EDGE -> new EdgeDriverManager().initDriver();
            case SAFARI -> new SafariDriverManager().initDriver();
            case FIREFOX_HEADLESS -> new FirefoxDriverManager("headless").initDriver();
            case CHROME_HEADLESS -> new ChromeDriverManager("headless").initDriver();
            case EDGE_HEADLESS -> new EdgeDriverManager("headless").initDriver();
        };
    }

}
