package nopcommerce.user;

import commons.BaseTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utilities.PropertiesConfig;

public class Multiple_Environment_Properties extends BaseTest {

    @Parameters("browser")
    @BeforeClass
    public void beforeClass(String browserName) {
        PropertiesConfig propertiesConfig = PropertiesConfig.getProperties("test");

        driver = openBrowserAndNavigateToUrl(browserName, propertiesConfig.getPropertyValue("App.Url"));

        log.info("App.Url = {}", propertiesConfig.getPropertyValue("App.Url"));
        log.info("App.User = {}", propertiesConfig.getPropertyValue("App.User"));
        log.info("App.Pass = {}", propertiesConfig.getPropertyValue("App.Pass"));
    }

    @Test
    public void TC_01() {

    }

}
