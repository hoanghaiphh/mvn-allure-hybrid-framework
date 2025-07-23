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
import pageObjects.nopcommerce.HomePO;
import pageObjects.nopcommerce.LoginPO;
import pageObjects.nopcommerce.RegisterPO;
import pageObjects.nopcommerce.myAccount.CustomerInfoPO;
import reportConfigs.SoftVerification;
import testData.UserInfoPOJO;
import utilities.DataGenerator;

@Feature("User")
public class Pattern_Object extends BaseTest {
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
        homePage.clickOnHeaderLinkByText("Register");
        registerPage = getPage(RegisterPO.class);

        registerPage.patternObject_registerUser(userInfo);

        VERIFY.verifyEquals(registerPage.getRegisterSuccessMessage(), "Your registration completed");
    }

    @Description("User_02_Login")
    @Severity(SeverityLevel.NORMAL)
    @Test
    public void User_02_Login() {
        registerPage.clickOnHeaderLinkByText("Log out");
        homePage = getPage(HomePO.class);

        homePage.clickOnHeaderLinkByText("Log in");
        loginPage = getPage(LoginPO.class);

        loginPage.patternObject_loginToSystem(userInfo);
        homePage = getPage(HomePO.class);

        VERIFY.verifyTrue(homePage.isHeaderLinkByTextDisplayed("My account"));
    }

    @Description("User_03_MyAccount")
    @Severity(SeverityLevel.MINOR)
    @Test
    public void User_03_MyAccount() {
        homePage.clickOnHeaderLinkByText("My account");
        customerInfoPage = getPage(CustomerInfoPO.class);

        VERIFY.verifyTrue(customerInfoPage.isCheckboxOrRadioByIDSelected("gender-male"));
        VERIFY.verifyEquals(customerInfoPage.getValueInTextboxByID("FirstName"), userInfo.getFirstName());
        VERIFY.verifyEquals(customerInfoPage.getValueInTextboxByID("LastName"), userInfo.getLastName());
        VERIFY.verifyEquals(customerInfoPage.getValueInTextboxByID("Email"), userInfo.getEmailAddress());
        VERIFY.verifyEquals(customerInfoPage.getValueInTextboxByID("Company"), userInfo.getCompanyName());
        VERIFY.verifyTrue(customerInfoPage.isCheckboxOrRadioByIDSelected("Newsletter"));
    }
}
