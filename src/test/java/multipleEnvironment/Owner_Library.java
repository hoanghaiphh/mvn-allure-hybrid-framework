package multipleEnvironment;

import commons.BaseTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utilities.OwnerConfig;

public class Owner_Library extends BaseTest {

    @Parameters({"platform", "browserName"})
    @BeforeClass
    public void beforeClass(String platform, String browserName) {
        OwnerConfig env = OwnerConfig.getEnvironmentOwner();
        initDriver(platform, browserName);
        configBrowserAndOpenUrl(env.getAppUrl());
    }

    @Test
    public void TC_01() {

    }

}
