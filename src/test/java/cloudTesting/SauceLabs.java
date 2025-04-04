package cloudTesting;

import commons.BaseTest;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class SauceLabs extends BaseTest {

    private WebDriver driver;

    @Parameters({"browser", "browserVersion", "platform"})
    @BeforeClass
    public void beforeClass(String browserName, String browserVersion, String platform) {
        driver = initDriver(browserName, browserVersion, platform);
        configBrowserAndOpenUrl(driver, "https://www.facebook.com/");
    }

    @Test
    public void TC_01() throws InterruptedException {
        Thread.sleep(1000);
    }

    @Test
    public void TC_02() {
        Assert.assertTrue(false);
    }

}
