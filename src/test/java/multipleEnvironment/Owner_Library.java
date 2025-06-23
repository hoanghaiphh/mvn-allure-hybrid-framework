package multipleEnvironment;

import commons.BaseTest;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utilities.OwnerConfig;

public class Owner_Library extends BaseTest {

    private OwnerConfig env;
    private WebDriver driver;

    @Parameters({"platform", "browserName"})
    @BeforeClass
    public void beforeClass(String platform, String browserName) {
        env = OwnerConfig.getEnvironmentOwner();
        driver = initDriver(platform, browserName);
        configBrowserAndOpenUrl(driver, env.getAppUrl());
    }

    @Test
    public void TC_01() {

    }

}
