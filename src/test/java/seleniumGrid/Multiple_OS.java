package seleniumGrid;

import commons.BaseTest;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class Multiple_OS extends BaseTest {

    private WebDriver driver;

    @Parameters({"platform", "browserName", "browserVersion", "osName", "osVersion"})
    @BeforeClass
    public void beforeClass(String platform, String browserName,
                            @Optional String browserVersion, @Optional String osName, @Optional String osVersion) {
        driver = initDriver(platform, browserName, browserVersion, osName, osVersion);
        configBrowserAndOpenUrl(driver, "https://www.facebook.com/");
    }

    @Test
    public void TC_00() throws InterruptedException {
        Thread.sleep(5000);
    }

}
