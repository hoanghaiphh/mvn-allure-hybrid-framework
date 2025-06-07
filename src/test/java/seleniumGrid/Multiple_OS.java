package seleniumGrid;

import commons.BaseTest;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class Multiple_OS extends BaseTest {

    private WebDriver driver;

    @Parameters({"platform", "browserName", "osName"})
    @BeforeClass
    public void beforeClass(String platform, String browserName, String osName) {
        driver = initDriver(platform, browserName, osName);
        configBrowserAndOpenUrl(driver, "https://www.facebook.com/");
    }

    @Test
    public void TC_00() throws InterruptedException {
        Thread.sleep(5000);
    }

}
