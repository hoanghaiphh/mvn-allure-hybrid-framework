package seleniumGrid;

import commons.BasePage;
import commons.BaseTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class Selenium_Grid_4_MultipleOS extends BaseTest {

    @Parameters({"browser", "os"})
    @BeforeClass
    public void beforeClass(String browserName, String osName) {
        driver = initDriverAndOpenUrl(browserName, osName, "https://www.facebook.com/");
        BasePage.getBasePage().sleepInSeconds(5);
    }

    @Test
    public void TC_00() {

    }

}
