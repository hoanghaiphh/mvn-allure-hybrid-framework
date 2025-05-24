package seleniumGrid;

import commons.EnumList;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.net.InetAddress;
import java.net.URL;

public class Selenium_Grid_4 {
    private WebDriver driver;

    @Parameters("browser")
    @BeforeClass
    public void beforeClass(String browserName) {
        EnumList.Browser browserList = EnumList.Browser.valueOf(browserName.toUpperCase());
        Capabilities capability = switch (browserList) {
            case FIREFOX -> new FirefoxOptions();
            case CHROME -> new ChromeOptions();
            case EDGE -> new EdgeOptions();
            default -> throw new RuntimeException("Browser is not valid!");
        };

        try {
            InetAddress ip = InetAddress.getLocalHost();
            URL url = new URL("http://" + ip.getHostAddress() + ":4444");
            driver = new RemoteWebDriver(url, capability);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void TC_02_Hub_Node() throws InterruptedException {
        driver.get("https://www.facebook.com/");
        Thread.sleep(5000);
        driver.quit();
    }

}
