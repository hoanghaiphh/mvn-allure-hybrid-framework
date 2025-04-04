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
import pageObjects.nopcommerce.PageGenerator;
import pageObjects.nopcommerce.HomePO;
import pageObjects.nopcommerce.LoginPO;
import pageObjects.nopcommerce.RegisterPO;
import pageObjects.nopcommerce.myAccount.CustomerInfoPO;
import reportConfigs.SoftVerification;
import testData.UserInfoPOJO;
import utilities.FakerConfig;

@Feature("User")
public class Pattern_Object extends BaseTest {
    private CustomerInfoPO customerInfoPage;
    private HomePO homePage;
    private RegisterPO registerPage;
    private LoginPO loginPage;

    private WebDriver driver;
    private UserInfoPOJO userInfo;
    private SoftVerification soft;

    @Parameters("browser")
    @BeforeClass
    public void beforeClass(String browserName) {
        driver = initDriver(browserName);
        configBrowserAndOpenUrl(driver, GlobalConstants.NOPCOMMERCE_LOCAL);
        homePage = PageGenerator.getHomePage(driver);

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
        homePage.clickOnHeaderLinkByText("Register");
        registerPage = PageGenerator.getRegisterPage(driver);

        registerPage.patternObject_registerUser(userInfo);
        soft.verifyEquals(registerPage.getRegisterSuccessMessage(), "Your registration completed");

        registerPage.clickOnHeaderLinkByText("Log out");
        homePage = PageGenerator.getHomePage(driver);
    }

    @Description("User_02_Login")
    @Severity(SeverityLevel.NORMAL)
    @Test
    public void User_02_Login() {
        homePage.clickOnHeaderLinkByText("Log in");
        loginPage = PageGenerator.getLoginPage(driver);

        homePage = loginPage.patternObject_loginToSystem(userInfo);
        soft.verifyTrue(homePage.isHeaderLinkByTextDisplayed("My account"));
    }

    @Description("User_03_MyAccount")
    @Severity(SeverityLevel.MINOR)
    @Test
    public void User_03_MyAccount() {
        homePage.clickOnHeaderLinkByText("My account");
        customerInfoPage = PageGenerator.getCustomerInfoPage(driver);

        soft.verifyTrue(customerInfoPage.isCheckboxOrRadioByIDSelected("gender-male"));
        soft.verifyEquals(customerInfoPage.getValueInTextboxByID("FirstName"), userInfo.getFirstName());
        soft.verifyEquals(customerInfoPage.getValueInTextboxByID("LastName"), userInfo.getLastName());
        soft.verifyEquals(customerInfoPage.getValueInTextboxByID("Email"), userInfo.getEmailAddress());
        soft.verifyEquals(customerInfoPage.getValueInTextboxByID("Company"), userInfo.getCompanyName());
        soft.verifyTrue(customerInfoPage.isCheckboxOrRadioByIDSelected("Newsletter"));
    }
}
