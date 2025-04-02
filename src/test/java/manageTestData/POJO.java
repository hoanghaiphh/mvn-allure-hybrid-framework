package manageTestData;

import commons.BaseTest;
import org.openqa.selenium.WebDriver;
import reportConfigs.SoftVerification;
import utilities.FakerConfig;
import commons.GlobalConstants;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageObjects.nopcommerce.PageGenerator;
import pageObjects.nopcommerce.user.HomePageObject;
import pageObjects.nopcommerce.user.LoginPageObject;
import pageObjects.nopcommerce.user.RegisterPageObject;
import pageObjects.nopcommerce.user.myAccount.CustomerInfoPageObject;
import testData.UserInfoPOJO;
import utilities.CommonUtils;

@Feature("User")
public class POJO extends BaseTest {
    private CustomerInfoPageObject customerInfoPage;
    private HomePageObject homePage;
    private RegisterPageObject registerPage;
    private LoginPageObject loginPage;

    private WebDriver driver;
    private SoftVerification soft;
    private UserInfoPOJO userInfo;

    @Parameters("browser")
    @BeforeClass
    public void beforeClass(String browserName) {
        driver = initDriver(browserName);
        openUrl(driver, GlobalConstants.NOPCOMMERCE_LOCAL);
        homePage = PageGenerator.getHomePage(driver);

        soft = SoftVerification.getSoftVerification();

        userInfo = UserInfoPOJO.getUserInfo();

        FakerConfig fakerVi = FakerConfig.getData("vi");
        String firstName = fakerVi.getFirstname();
        String lastName = fakerVi.getLastname();
        userInfo.setFirstName(firstName);
        userInfo.setLastName(lastName);
        userInfo.setEmailAddress(CommonUtils.getRandomEmail(firstName + lastName, driver));
        FakerConfig fakerDefault = FakerConfig.getData();
        userInfo.setCompanyName(fakerDefault.getCompanyName());
        userInfo.setPassword(fakerDefault.getPassword());
    }

    @Description("User_01_Register")
    @Severity(SeverityLevel.NORMAL)
    @Test
    public void User_01_Register() {
        registerPage = (RegisterPageObject) homePage.clickOnHeaderLink("Register");

        registerPage.addUserInfo(userInfo);

        registerPage.clickOnRegisterButton();

        soft.verifyEquals(registerPage.getRegisterSuccessMessage(), "Your registration completed");
    }

    @Description("User_02_Login")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void User_02_Login() {
        homePage = (HomePageObject) registerPage.clickOnHeaderLink("Log out");

        loginPage = (LoginPageObject) homePage.clickOnHeaderLink("Log in");

        homePage = loginPage.loginToSystem(userInfo);

        soft.verifyTrue(homePage.isMyAccountLinkDisplayed());
    }

    @Description("User_03_MyAccount")
    @Severity(SeverityLevel.MINOR)
    @Test
    public void User_03_MyAccount() {
        customerInfoPage = (CustomerInfoPageObject) homePage.clickOnHeaderLink("My account");

        soft.verifyTrue(customerInfoPage.isGenderMaleSelected());
        soft.verifyEquals(customerInfoPage.getValueInFirstnameTextbox(), userInfo.getFirstName());
        soft.verifyEquals(customerInfoPage.getValueInLastnameTextbox(), userInfo.getLastName());
        soft.verifyEquals(customerInfoPage.getValueInCompanyTextbox(), userInfo.getCompanyName());
    }

}
