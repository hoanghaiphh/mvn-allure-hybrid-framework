package shareClassState;

import commons.BaseTest;
import commons.GlobalConstants;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.openqa.selenium.Cookie;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageObjects.nopcommerce.HomePO;
import pageObjects.nopcommerce.myAccount.CustomerInfoPO;
import reportConfigs.SoftVerification;
import testData.UserInfoPOJO;

import java.util.Set;

public class MyAccount extends BaseTest {
    private CustomerInfoPO customerInfoPage;
    private HomePO homePage;

    private static final SoftVerification VERIFY = SoftVerification.getSoftVerification();

    @Parameters({"platform", "browserName"})
    @BeforeClass
    public void beforeClass(String platform, String browserName) {
        Set<Cookie> cookies = Register_And_Login.cookiesThreadLocal.get();
        if (cookies == null || cookies.isEmpty()) {
            Assert.fail("Pre-requisite failed or cookies not found. Skipping MyAccount tests.");
        }

        initDriver(platform, browserName);
        configBrowserAndOpenUrl(GlobalConstants.NOPCOMMERCE_LOCAL);
        homePage = getPage(HomePO.class);

        homePage.loginByCookies(Register_And_Login.cookiesThreadLocal.get());

        VERIFY.verifyTrue(homePage.isHeaderLinkByTextDisplayed("My account"));
    }

    @Description("User_03_MyAccount")
    @Severity(SeverityLevel.NORMAL)
    @Test
    public void User_03_MyAccount() {
        homePage.clickOnHeaderLink("My account");
        customerInfoPage = getPage(CustomerInfoPO.class);

        UserInfoPOJO userInfo = Register_And_Login.userInfoThreadLocal.get();

        VERIFY.verifyTrue(customerInfoPage.isGenderMaleSelected());
        VERIFY.verifyEquals(customerInfoPage.getValueInFirstnameTextbox(), userInfo.getFirstName());
        VERIFY.verifyEquals(customerInfoPage.getValueInLastnameTextbox(), userInfo.getLastName());
        VERIFY.verifyEquals(customerInfoPage.getValueInCompanyTextbox(), userInfo.getCompanyName());
    }
}
