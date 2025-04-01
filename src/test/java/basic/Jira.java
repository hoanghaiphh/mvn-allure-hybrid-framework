package basic;

import commons.BaseTest;
import commons.GlobalConstants;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import jiraConfig.JiraCreateIssue;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageObjects.nopcommerce.PageGenerator;
import pageObjects.nopcommerce.user.HomePageObject;
import pageObjects.nopcommerce.user.LoginPageObject;
import pageObjects.nopcommerce.user.RegisterPageObject;
import pageObjects.nopcommerce.user.myAccount.CustomerInfoPageObject;
import reportConfigs.SoftVerification;
import testData.UserInfoPOJO;
import utilities.FakerConfig;
import utilities.RandomData;

@Listeners(jiraConfig.JiraListener.class)
@Feature("User")
public class Jira extends BaseTest {
    private CustomerInfoPageObject customerInfoPage;
    private HomePageObject homePage;
    private RegisterPageObject registerPage;
    private LoginPageObject loginPage;

    private WebDriver driver;
    private UserInfoPOJO userInfo;

    @Parameters("browser")
    @BeforeClass
    public void beforeClass(String browserName) {
        driver = initDriver(browserName);
        openUrl(driver, GlobalConstants.NOPCOMMERCE_LOCAL);
        homePage = PageGenerator.getHomePage(driver);

        userInfo = UserInfoPOJO.getUserInfo();

        FakerConfig fakerVi = FakerConfig.getData("vi");
        String firstName = fakerVi.getFirstname();
        String lastName = fakerVi.getLastname();
        userInfo.setFirstName(firstName);
        userInfo.setLastName(lastName);
        userInfo.setEmailAddress(
                RandomData.getRandomData().getRandomEmailByTimestamp(firstName + lastName, driver));
        FakerConfig fakerDefault = FakerConfig.getData();
        userInfo.setCompanyName(fakerDefault.getCompanyName());
        userInfo.setPassword(fakerDefault.getPassword());
    }

    @JiraCreateIssue(isCreateIssue = true)
    @Description("User_01_Register")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void User_01_Register() {
        registerPage = (RegisterPageObject) homePage.clickOnHeaderLink("Register");
        registerPage.addUserInfo(userInfo);
        registerPage.clickOnRegisterButton();

        Assert.assertEquals(registerPage.getRegisterSuccessMessage(), "Your registration completed..."); // failed
    }

    @JiraCreateIssue(isCreateIssue = true)
    @Description("User_02_Login")
    @Severity(SeverityLevel.NORMAL)
    @Test
    public void User_02_Login() {
        homePage = (HomePageObject) registerPage.clickOnHeaderLink("Log out");
        loginPage = (LoginPageObject) homePage.clickOnHeaderLink("Log in");
        homePage = loginPage.loginToSystem(userInfo);

        Assert.assertFalse(homePage.isMyAccountLinkDisplayed()); // failed
    }

    @JiraCreateIssue(isCreateIssue = true)
    @Description("User_03_MyAccount")
    @Severity(SeverityLevel.MINOR)
    @Test
    public void User_03_MyAccount() {
        customerInfoPage = (CustomerInfoPageObject) homePage.clickOnHeaderLink("My account");

        SoftVerification soft = SoftVerification.getSoftVerification();
        soft.verifyTrue(customerInfoPage.isGenderMaleSelected());
        soft.verifyEquals(customerInfoPage.getValueInFirstnameTextbox(), "userInfo.getFirstName()"); // failed
        soft.verifyEquals(customerInfoPage.getValueInLastnameTextbox(), userInfo.getLastName());
        soft.verifyEquals(customerInfoPage.getValueInCompanyTextbox(), "userInfo.getCompanyName()"); // failed
    }

}
