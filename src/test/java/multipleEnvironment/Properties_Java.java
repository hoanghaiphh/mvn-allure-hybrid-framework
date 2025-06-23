package multipleEnvironment;

import commons.BaseTest;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utilities.PropertiesConfig;

public class Properties_Java extends BaseTest {

    private PropertiesConfig env;
    private WebDriver driver;

    @Parameters({"platform", "browserName"})
    @BeforeClass
    public void beforeClass(String platform, String browserName) {
        env = PropertiesConfig.getEnvironmentProperties();
        driver = initDriver(platform, browserName);
        configBrowserAndOpenUrl(driver, env.getPropertyValue("app.Url"));
    }

    @Test
    public void TC_01() {

    }

}
