package multipleEnvironment;

import commons.BaseTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class Multiple_Environment_Properties extends BaseTest {

    @Parameters("browser")
    @BeforeClass
    public void beforeClass(String browserName) {
        driver = initDriverAndOpenUrl(
                browserName,
                getEnvironment().getPropertyValue("App.Url"));
    }

    @Test
    public void TC_01() {

    }

}
