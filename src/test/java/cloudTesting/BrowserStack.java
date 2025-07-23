package cloudTesting;

import commons.BaseTest;
import commons.GlobalConstants;
import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageObjects.techpanda.HomePO;
import pageObjects.techpanda.LoginPO;
import pageObjects.techpanda.RegisterPO;
import pageObjects.techpanda.myAccount.AccountDashboardPO;
import pageObjects.techpanda.myAccount.AccountInfoPO;
import testData.UserInfoPOJO;
import utilities.DataGenerator;

public class BrowserStack extends BaseTest {
    private HomePO homePage;
    private LoginPO loginPage;
    private RegisterPO registerPage;
    private AccountDashboardPO accountDashboardPage;
    private AccountInfoPO accountInfoPage;

    private UserInfoPOJO userInfo;

    @Parameters({"platform", "browserName", "browserVersion", "osName", "osVersion"})
    @BeforeClass
    public void beforeClass(String platform, String browserName, String browserVersion, String osName, String osVersion) {
        initDriver(platform, browserName, browserVersion, osName, osVersion);
        configBrowserAndOpenUrl(GlobalConstants.TECHPANDA);
        homePage = getPage(HomePO.class);

        userInfo = UserInfoPOJO.getUserInfo();
        DataGenerator faker = DataGenerator.create("vi");
        String firstname = faker.getFirstname();
        String lastname = faker.getLastname();
        String email = getRandomEmailByCurrentState(firstname + lastname);
        userInfo.setFirstName(firstname);
        userInfo.setLastName(lastname);
        userInfo.setEmailAddress(email);
        userInfo.setPassword(faker.getPassword());
    }

    @Description("TC_01 Register")
    @Test
    public void TC_01_Register() {
        homePage.openRegisterPage();
        registerPage = getPage(RegisterPO.class);

        registerPage.registerAccountWithInfo(userInfo);
        accountDashboardPage = getPage(AccountDashboardPO.class);

        Assert.assertEquals(accountDashboardPage.getRegistrationSuccessMsg(),
                "Thank you for registering with Main Website Store.");
    }

    @Description("TC_02 Login")
    @Test
    public void TC_02_Login() {
        accountDashboardPage.logoutFromSystem();
        homePage = getPage(HomePO.class);

        homePage.openLoginPage();
        loginPage = getPage(LoginPO.class);

        loginPage.loginToSystemWithInfo(userInfo);
        accountDashboardPage = getPage(AccountDashboardPO.class);

        String fullName = userInfo.getFirstName() + " " + userInfo.getLastName();
        Assert.assertTrue(accountDashboardPage.headerWelcomeMsgContains(fullName));
        Assert.assertEquals(accountDashboardPage.getWelcomeMsg(), "Hello, " + fullName + "!");

        String contactInfo = accountDashboardPage.getContactInfo();
        Assert.assertTrue(contactInfo.contains(fullName));
        Assert.assertTrue(contactInfo.contains(userInfo.getEmailAddress()));
    }

    @Description("TC_03 Account Information")
    @Test
    public void TC_03_Account_Information() {
        accountDashboardPage.switchToAccountInfoPage();
        accountInfoPage = getPage(AccountInfoPO.class);

        Assert.assertEquals(accountInfoPage.getAccountFirstname(), userInfo.getFirstName());
        Assert.assertEquals(accountInfoPage.getAccountLastname(), userInfo.getLastName());
        Assert.assertEquals(accountInfoPage.getAccountEmail(), userInfo.getEmailAddress());
    }

}
