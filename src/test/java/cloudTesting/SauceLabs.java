package cloudTesting;

import commons.BaseTest;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utilities.CommonUtils;

public class SauceLabs extends BaseTest {

    private WebDriver driver;

    @Parameters({"browser", "browserVersion", "platform"})
    @BeforeClass
    public void beforeClass(String browserName, String browserVersion, String platform) {
        driver = initDriver(browserName, browserVersion, platform);
        openUrl(driver, "https://www.facebook.com/");

        CommonUtils.sleepInSeconds(1);
    }

    @Test
    public void TC_00() {

    }

}
