package seleniumGrid;

import commons.BaseTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class Multiple_OS extends BaseTest {

    @Parameters({"platform", "browserName", "osName"})
    @BeforeClass
    public void beforeClass(String platform, String browserName, String osName) {
        initDriver(platform, browserName, osName);
        configBrowserAndOpenUrl("https://www.facebook.com/");
    }

    @Test
    public void TC_00() throws InterruptedException {
        Thread.sleep(5000);
    }

}
