package nopcommerce.user;

import commons.BaseTest;
import org.aeonbits.owner.ConfigFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utilities.EnvironmentConfig;

public class Multiple_Environment_Owner extends BaseTest {

    @Parameters({"browser", "environment"})
    @BeforeClass
    public void beforeClass(String browserName, String environment) {
        ConfigFactory.setProperty("environment", environment);
        EnvironmentConfig envConfig = ConfigFactory.create(EnvironmentConfig.class);

        driver = openBrowserAndNavigateToUrl(browserName, envConfig.getUrl());

        log.info("App.Url = {}", envConfig.getUrl());
        log.info("App.User = {}", envConfig.getUsername());
        log.info("App.Pass = {}", envConfig.getPassword());
    }

    @Test
    public void TC_01() {

    }

}
