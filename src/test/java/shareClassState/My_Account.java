package shareClassState;

import commons.BaseTest;
import commons.GlobalConstants;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageObjects.nopcommerce.PageGenerator;
import pageObjects.nopcommerce.user.HomePageObject;
import pageObjects.nopcommerce.user.myAccount.CustomerInfoPageObject;
import reportConfigs.SoftVerification;
import testData.UserInfoPOJO;

import java.util.Set;

public class My_Account extends BaseTest {
    private CustomerInfoPageObject customerInfoPage;
    private HomePageObject homePage;

    private WebDriver driver;

    @Parameters("browser")
    @BeforeClass
    public void beforeClass(String browserName) {
        driver = initDriver(browserName);
        openUrl(driver, GlobalConstants.NOPCOMMERCE_LOCAL);
        homePage = PageGenerator.getHomePage(driver);

        Set<Cookie> cookies = Register_And_Login.getCookiesThreadLocal().get();
        homePage.setCookies(driver, cookies);
        homePage.refreshCurrentPage(driver);
        Assert.assertTrue(homePage.isHeaderLinkByTextDisplayed("My account"));
    }

    @Description("User_03_MyAccount")
    @Severity(SeverityLevel.NORMAL)
    @Test
    public void User_03_MyAccount() {
        customerInfoPage = (CustomerInfoPageObject) homePage.clickOnHeaderLink("My account");

        SoftVerification soft = SoftVerification.getSoftVerification();
        UserInfoPOJO userInfo = Register_And_Login.getUserInfoThreadLocal().get();

        soft.verifyTrue(customerInfoPage.isGenderMaleSelected());
        soft.verifyEquals(customerInfoPage.getValueInFirstnameTextbox(), userInfo.getFirstName());
        soft.verifyEquals(customerInfoPage.getValueInLastnameTextbox(), userInfo.getLastName());
        soft.verifyEquals(customerInfoPage.getValueInCompanyTextbox(), userInfo.getCompanyName());
    }
}
