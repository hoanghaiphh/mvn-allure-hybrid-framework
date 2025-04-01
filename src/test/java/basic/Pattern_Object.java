package basic;

import commons.BaseTest;
import commons.GlobalConstants;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
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

@Feature("User")
public class Pattern_Object extends BaseTest {
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
        FakerConfig faker = FakerConfig.getData("vi");
        String firstName = faker.getFirstname();
        String lastName = faker.getLastname();
        userInfo.setFirstName(firstName);
        userInfo.setLastName(lastName);
        userInfo.setEmailAddress(RandomData.getRandomEmail(firstName + lastName, driver));
        userInfo.setPassword(faker.getPassword());
        userInfo.setCompanyName(faker.getCompanyName());
    }

    @Description("User_01_Register")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void User_01_Register() {
        homePage.clickOnHeaderLinkByText("Register");
        registerPage = PageGenerator.getRegisterPage(driver);

        registerPage.patternObject_registerUser(userInfo);
        Assert.assertEquals(registerPage.getRegisterSuccessMessage(), "Your registration completed");

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
        Assert.assertTrue(homePage.isHeaderLinkByTextDisplayed("My account"));
    }

    @Description("User_03_MyAccount")
    @Severity(SeverityLevel.MINOR)
    @Test
    public void User_03_MyAccount() {
        homePage.clickOnHeaderLinkByText("My account");
        customerInfoPage = PageGenerator.getCustomerInfoPage(driver);

        SoftVerification soft = SoftVerification.getSoftVerification();
        soft.verifyTrue(customerInfoPage.isCheckboxOrRadioByIDSelected("gender-male"));
        soft.verifyEquals(customerInfoPage.getValueInTextboxByID("FirstName"), userInfo.getFirstName());
        soft.verifyEquals(customerInfoPage.getValueInTextboxByID("LastName"), userInfo.getLastName());
        soft.verifyEquals(customerInfoPage.getValueInTextboxByID("Email"), userInfo.getEmailAddress());
        soft.verifyEquals(customerInfoPage.getValueInTextboxByID("Company"), userInfo.getCompanyName());
        soft.verifyTrue(customerInfoPage.isCheckboxOrRadioByIDSelected("Newsletter"));
    }
}
