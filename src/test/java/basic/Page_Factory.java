package basic;

import commons.BaseTest;
import commons.GlobalConstants;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageFactory.CustomerInfoPageFactory;
import pageFactory.HomePageFactory;
import pageFactory.LoginPageFactory;
import pageFactory.RegisterPageFactory;
import reportConfigs.SoftVerification;
import testData.UserInfoPOJO;
import utilities.DataGenerator;

@Feature("User")
public class Page_Factory extends BaseTest {
    private CustomerInfoPageFactory customerInfoPage;
    private HomePageFactory homePage;
    private RegisterPageFactory registerPage;
    private LoginPageFactory loginPage;

    private UserInfoPOJO userInfo;
    private static final SoftVerification VERIFY = SoftVerification.getSoftVerification();

    @Parameters({"platform", "browserName"})
    @BeforeClass
    public void beforeClass(String platform, String browserName) {
        initDriver(platform, browserName);
        configBrowserAndOpenUrl(GlobalConstants.NOPCOMMERCE_LOCAL);
        homePage = getPage(HomePageFactory.class);

        userInfo = UserInfoPOJO.getUserInfo();
        DataGenerator fakerVi = DataGenerator.create("vi");
        String firstName = fakerVi.getFirstname();
        String lastName = fakerVi.getLastname();
        userInfo.setFirstName(firstName);
        userInfo.setLastName(lastName);
        userInfo.setEmailAddress(getRandomEmailByCurrentState(firstName + lastName));
        DataGenerator fakerDefault = DataGenerator.create();
        userInfo.setCompanyName(fakerDefault.getCompanyName());
        userInfo.setPassword(fakerDefault.getPassword());
    }

    @Description("User_01_Register")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void User_01_Register() {
        registerPage = homePage.clickOnRegisterLink();
        registerPage.registerUser(userInfo);

        VERIFY.verifyEquals(registerPage.getRegisterSuccessMessage(), "Your registration completed");
    }

    @Description("User_02_Login")
    @Severity(SeverityLevel.NORMAL)
    @Test
    public void User_02_Login() {
        homePage = registerPage.clickOnLogoutLink();
        loginPage = homePage.clickOnLoginLink();
//        homePage = loginPage.loginToSystem(userInfo);
        homePage = loginPage.loginJavaReflection(userInfo); // Java Reflection

        VERIFY.verifyTrue(homePage.isMyAccountLinkDisplayed());
    }

    @Description("User_03_MyAccount")
    @Severity(SeverityLevel.MINOR)
    @Test
    public void User_03_MyAccount() {
        customerInfoPage = homePage.clickOnMyAccountLink();

        VERIFY.verifyTrue(customerInfoPage.isGenderMaleSelected());
        VERIFY.verifyEquals(customerInfoPage.getValueInFirstnameTextbox(), userInfo.getFirstName());
        VERIFY.verifyEquals(customerInfoPage.getValueInLastnameTextbox(), userInfo.getLastName());
        VERIFY.verifyEquals(customerInfoPage.getValueInCompanyTextbox(), userInfo.getCompanyName());
    }
}
