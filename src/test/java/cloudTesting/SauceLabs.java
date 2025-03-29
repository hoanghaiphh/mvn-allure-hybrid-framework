package cloudTesting;

import commons.BasePage;
import commons.BaseTest;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class SauceLabs extends BaseTest {

    private WebDriver driver;

    @Parameters({"browser", "browserVersion", "platform"})
    @BeforeClass
    public void beforeClass(String browserName, String browserVersion, String platform) {
        driver = initDriver(browserName, browserVersion, platform);
        openUrl(driver, "https://www.facebook.com/");

        BasePage.getBasePage().sleepInSeconds(1);
    }

    @Test
    public void TC_00() {

    }

}
