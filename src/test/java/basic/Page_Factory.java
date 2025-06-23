package basic;

import commons.BaseTest;
import commons.GlobalConstants;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageFactory.CustomerInfoPageFactory;
import pageFactory.HomePageFactory;
import pageFactory.LoginPageFactory;
import pageFactory.RegisterPageFactory;
import reportConfigs.SoftVerification;
import testData.UserInfoPOJO;
import utilities.FakerConfig;

@Feature("User")
public class Page_Factory extends BaseTest {
    private CustomerInfoPageFactory customerInfoPage;
    private HomePageFactory homePage;
    private RegisterPageFactory registerPage;
    private LoginPageFactory loginPage;

    private WebDriver driver;
    private UserInfoPOJO userInfo;
    private SoftVerification soft;

    @Parameters({"platform", "browserName"})
    @BeforeClass
    public void beforeClass(String platform, String browserName) {
        driver = initDriver(platform, browserName);
        configBrowserAndOpenUrl(driver, GlobalConstants.NOPCOMMERCE_LOCAL);
        homePage = new HomePageFactory(driver);

        userInfo = UserInfoPOJO.getUserInfo();
        FakerConfig faker = FakerConfig.getData("vi");
        String firstName = faker.getFirstname();
        String lastName = faker.getLastname();
        userInfo.setFirstName(firstName);
        userInfo.setLastName(lastName);
        userInfo.setEmailAddress(getRandomEmailByCurrentState(firstName + lastName));
        userInfo.setPassword(faker.getPassword());
        userInfo.setCompanyName(faker.getCompanyName());

        soft = SoftVerification.getSoftVerification();
    }

    @Description("User_01_Register")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void User_01_Register() {
        registerPage = homePage.clickOnRegisterLink();
        registerPage.registerUser(userInfo);

        soft.verifyEquals(registerPage.getRegisterSuccessMessage(), "Your registration completed");
    }

    @Description("User_02_Login")
    @Severity(SeverityLevel.NORMAL)
    @Test
    public void User_02_Login() {
        homePage = registerPage.clickOnLogoutLink();
        loginPage = homePage.clickOnLoginLink();
        homePage = loginPage.loginToSystem(userInfo);

        soft.verifyTrue(homePage.isMyAccountLinkDisplayed());
    }

    @Description("User_03_MyAccount")
    @Severity(SeverityLevel.MINOR)
    @Test
    public void User_03_MyAccount() {
        customerInfoPage = homePage.clickOnMyAccountLink();

        soft.verifyTrue(customerInfoPage.isGenderMaleSelected());
        soft.verifyEquals(customerInfoPage.getValueInFirstnameTextbox(), userInfo.getFirstName());
        soft.verifyEquals(customerInfoPage.getValueInLastnameTextbox(), userInfo.getLastName());
        soft.verifyEquals(customerInfoPage.getValueInCompanyTextbox(), userInfo.getCompanyName());
    }
}
