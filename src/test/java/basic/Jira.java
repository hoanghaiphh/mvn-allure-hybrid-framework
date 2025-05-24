package basic;

import commons.BaseTest;
import commons.GlobalConstants;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import jiraConfig.JiraCreateIssue;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import pageObjects.nopcommerce.PageGenerator;
import pageObjects.nopcommerce.HomePO;
import pageObjects.nopcommerce.LoginPO;
import pageObjects.nopcommerce.RegisterPO;
import pageObjects.nopcommerce.myAccount.CustomerInfoPO;
import reportConfigs.SoftVerification;
import testData.UserInfoPOJO;
import utilities.FakerConfig;

@Listeners(jiraConfig.JiraListener.class)
@Feature("User")
public class Jira extends BaseTest {
    private CustomerInfoPO customerInfoPage;
    private HomePO homePage;
    private RegisterPO registerPage;
    private LoginPO loginPage;

    private WebDriver driver;
    private UserInfoPOJO userInfo;
    private SoftVerification soft;

    @Parameters({"platform", "browserName", "browserVersion", "osName", "osVersion"})
    @BeforeClass
    public void beforeClass(String platform, String browserName,
                            @Optional String browserVersion, @Optional String osName, @Optional String osVersion) {
        driver = initDriver(platform, browserName, browserVersion, osName, osVersion);
        configBrowserAndOpenUrl(driver, GlobalConstants.NOPCOMMERCE_LOCAL);
        homePage = PageGenerator.getHomePage(driver);

        userInfo = UserInfoPOJO.getUserInfo();

        FakerConfig fakerVi = FakerConfig.getData("vi");
        String firstName = fakerVi.getFirstname();
        String lastName = fakerVi.getLastname();
        userInfo.setFirstName(firstName);
        userInfo.setLastName(lastName);
        userInfo.setEmailAddress(getRandomEmailByCurrentState(firstName + lastName));
        FakerConfig fakerDefault = FakerConfig.getData();
        userInfo.setCompanyName(fakerDefault.getCompanyName());
        userInfo.setPassword(fakerDefault.getPassword());

        soft = SoftVerification.getSoftVerification();
    }

    @JiraCreateIssue(isCreateIssue = true)
    @Description("User_01_Register")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void User_01_Register() {
        registerPage = (RegisterPO) homePage.clickOnHeaderLink("Register");
        registerPage.addUserInfo(userInfo);
        registerPage.clickOnRegisterButton();

        soft.verifyEquals(registerPage.getRegisterSuccessMessage(), "Your registration completed..."); // failed
    }

    @JiraCreateIssue(isCreateIssue = true)
    @Description("User_02_Login")
    @Severity(SeverityLevel.NORMAL)
    @Test
    public void User_02_Login() {
        homePage = (HomePO) registerPage.clickOnHeaderLink("Log out");
        loginPage = (LoginPO) homePage.clickOnHeaderLink("Log in");
        homePage = loginPage.loginToSystem(userInfo);

        soft.verifyFalse(homePage.isMyAccountLinkDisplayed()); // failed
    }

    @JiraCreateIssue(isCreateIssue = true)
    @Description("User_03_MyAccount")
    @Severity(SeverityLevel.MINOR)
    @Test
    public void User_03_MyAccount() {
        customerInfoPage = (CustomerInfoPO) homePage.clickOnHeaderLink("My account");

        soft.verifyTrue(customerInfoPage.isGenderMaleSelected());
        soft.verifyEquals(customerInfoPage.getValueInFirstnameTextbox(), "userInfo.getFirstName()"); // failed
        soft.verifyEquals(customerInfoPage.getValueInLastnameTextbox(), userInfo.getLastName());
        soft.verifyEquals(customerInfoPage.getValueInCompanyTextbox(), "userInfo.getCompanyName()"); // failed
    }

}
