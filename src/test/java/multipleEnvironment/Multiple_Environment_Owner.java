package multipleEnvironment;

import commons.BaseTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class Multiple_Environment_Owner extends BaseTest {

    @Parameters("browser")
    @BeforeClass
    public void beforeClass(String browserName) {
        driver = initDriverAndOpenUrl(
                browserName,
                getEnvironmentOwner().getUrl());
    }

    @Test
    public void TC_01() {

    }

}
