package shareClassState;

import commons.BaseTest;
import commons.GlobalConstants;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageObjects.nopcommerce.PageGenerator;
import pageObjects.nopcommerce.HomePO;
import pageObjects.nopcommerce.myAccount.CustomerInfoPO;
import reportConfigs.SoftVerification;
import testData.UserInfoPOJO;

public class MyAccount extends BaseTest {
    private CustomerInfoPO customerInfoPage;
    private HomePO homePage;

    private WebDriver driver;
    private SoftVerification soft;

    @Parameters({"platform", "browserName", "browserVersion", "osName", "osVersion"})
    @BeforeClass
    public void beforeClass(String platform, String browserName,
                            @Optional String browserVersion, @Optional String osName, @Optional String osVersion) {
        driver = initDriver(platform, browserName, browserVersion, osName, osVersion);
        configBrowserAndOpenUrl(driver, GlobalConstants.NOPCOMMERCE_LOCAL);
        homePage = PageGenerator.getHomePage(driver);

        homePage.loginByCookies(Register_And_Login.getCookiesThreadLocal().get());

        soft = SoftVerification.getSoftVerification();
        soft.verifyTrue(homePage.isHeaderLinkByTextDisplayed("My account"));
    }

    @Description("User_03_MyAccount")
    @Severity(SeverityLevel.NORMAL)
    @Test
    public void User_03_MyAccount() {
        customerInfoPage = (CustomerInfoPO) homePage.clickOnHeaderLink("My account");

        UserInfoPOJO userInfo = Register_And_Login.getUserInfoThreadLocal().get();

        soft.verifyTrue(customerInfoPage.isGenderMaleSelected());
        soft.verifyEquals(customerInfoPage.getValueInFirstnameTextbox(), userInfo.getFirstName());
        soft.verifyEquals(customerInfoPage.getValueInLastnameTextbox(), userInfo.getLastName());
        soft.verifyEquals(customerInfoPage.getValueInCompanyTextbox(), userInfo.getCompanyName());
    }
}
