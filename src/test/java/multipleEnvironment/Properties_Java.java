package multipleEnvironment;

import commons.BaseTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utilities.PropertiesConfig;

public class Properties_Java extends BaseTest {

    private static final PropertiesConfig ENV = PropertiesConfig.getEnvironmentProperties();

    @Parameters({"platform", "browserName"})
    @BeforeClass
    public void beforeClass(String platform, String browserName) {
        initDriver(platform, browserName);
        configBrowserAndOpenUrl(ENV.getPropertyValue("app.Url"));
    }

    @Test
    public void TC_01() {

    }

}
