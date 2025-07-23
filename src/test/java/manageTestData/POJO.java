package manageTestData;

import commons.BaseTest;
import commons.GlobalConstants;
import reportConfigs.SoftVerification;
import utilities.DataGenerator;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageObjects.nopcommerce.HomePO;
import pageObjects.nopcommerce.LoginPO;
import pageObjects.nopcommerce.RegisterPO;
import pageObjects.nopcommerce.myAccount.CustomerInfoPO;
import testData.UserInfoPOJO;

@Feature("User")
public class POJO extends BaseTest {
    private CustomerInfoPO customerInfoPage;
    private HomePO homePage;
    private RegisterPO registerPage;
    private LoginPO loginPage;

    private UserInfoPOJO userInfo;
    private static final SoftVerification VERIFY = SoftVerification.getSoftVerification();

    @Parameters({"platform", "browserName"})
    @BeforeClass
    public void beforeClass(String platform, String browserName) {
        initDriver(platform, browserName);
        configBrowserAndOpenUrl(GlobalConstants.NOPCOMMERCE_LOCAL);
        homePage = getPage(HomePO.class);

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
    @Severity(SeverityLevel.NORMAL)
    @Test
    public void User_01_Register() {
        homePage.clickOnHeaderLink("Register");
        registerPage = getPage(RegisterPO.class);

        registerPage.addUserInfo(userInfo);
        registerPage.clickOnRegisterButton();

        VERIFY.verifyEquals(registerPage.getRegisterSuccessMessage(), "Your registration completed");
    }

    @Description("User_02_Login")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void User_02_Login() {
        registerPage.clickOnHeaderLink("Log out");
        homePage = getPage(HomePO.class);

        homePage.clickOnHeaderLink("Log in");
        loginPage = getPage(LoginPO.class);

        loginPage.loginToSystem(userInfo);
        homePage = getPage(HomePO.class);

        VERIFY.verifyTrue(homePage.isMyAccountLinkDisplayed());
    }

    @Description("User_03_MyAccount")
    @Severity(SeverityLevel.MINOR)
    @Test
    public void User_03_MyAccount() {
        homePage.clickOnHeaderLink("My account");
        customerInfoPage = getPage(CustomerInfoPO.class);

        VERIFY.verifyTrue(customerInfoPage.isGenderMaleSelected());
        VERIFY.verifyEquals(customerInfoPage.getValueInFirstnameTextbox(), userInfo.getFirstName());
        VERIFY.verifyEquals(customerInfoPage.getValueInLastnameTextbox(), userInfo.getLastName());
        VERIFY.verifyEquals(customerInfoPage.getValueInCompanyTextbox(), userInfo.getCompanyName());
    }

}
