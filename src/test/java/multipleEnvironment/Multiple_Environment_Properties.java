package multipleEnvironment;

import commons.BaseTest;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class Multiple_Environment_Properties extends BaseTest {

    private WebDriver driver;

    @Parameters("browser")
    @BeforeClass
    public void beforeClass(String browserName) {
        driver = initDriver(browserName);
        openUrl(driver, getEnvironmentProperties().getPropertyValue("App.Url"));
    }

    @Test
    public void TC_01() {

    }

}
