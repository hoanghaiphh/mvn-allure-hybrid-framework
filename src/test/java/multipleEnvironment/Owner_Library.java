package multipleEnvironment;

import commons.BaseTest;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class Owner_Library extends BaseTest {

    private WebDriver driver;

    @Parameters({"platform", "browserName"})
    @BeforeClass
    public void beforeClass(String platform, String browserName) {
        driver = initDriver(platform, browserName);
        configBrowserAndOpenUrl(driver, getEnvironmentOwner().getUrl());
    }

    @Test
    public void TC_01() {

    }

}
