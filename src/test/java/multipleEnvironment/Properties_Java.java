package multipleEnvironment;

import commons.BaseTest;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class Properties_Java extends BaseTest {

    private WebDriver driver;

    @Parameters({"platform", "browserName", "browserVersion", "osName", "osVersion"})
    @BeforeClass
    public void beforeClass(String platform, String browserName,
                            @Optional String browserVersion, @Optional String osName, @Optional String osVersion) {
        driver = initDriver(platform, browserName, browserVersion, osName, osVersion);
        configBrowserAndOpenUrl(driver, getEnvironmentProperties().getPropertyValue("App.Url"));
    }

    @Test
    public void TC_01() {

    }

}
