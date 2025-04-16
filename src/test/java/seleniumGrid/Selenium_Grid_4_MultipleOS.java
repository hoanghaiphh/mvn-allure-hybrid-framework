package seleniumGrid;

import commons.BaseTest;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class Selenium_Grid_4_MultipleOS extends BaseTest {

    private WebDriver driver;

    @Parameters({"browser", "os"})
    @BeforeClass
    public void beforeClass(String browserName, String osName) {
        driver = initDriverSeleniumGrid(browserName, osName);
        configBrowserAndOpenUrl(driver, "https://www.facebook.com/");
    }

    @Test
    public void TC_00() throws InterruptedException {
        Thread.sleep(5000);
    }

}
