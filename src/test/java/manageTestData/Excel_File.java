package manageTestData;

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
import utilities.ExcelConfig;

import java.util.Map;

@Feature("User")
public class Excel_File extends BaseTest {
    private CustomerInfoPO customerInfoPage;
    private HomePO homePage;
    private RegisterPO registerPage;
    private LoginPO loginPage;

    private Map<String, String> userInfo;
    private static final SoftVerification VERIFY = SoftVerification.getSoftVerification();

    @Parameters({"platform", "browserName"})
    @BeforeClass
    public void beforeClass(String platform, String browserName) {
        userInfo = ExcelConfig.getRowData("testDataUserInfo.xlsx", "Sheet1", 1);

        initDriver(platform, browserName);
        configBrowserAndOpenUrl(GlobalConstants.NOPCOMMERCE_LOCAL);
        homePage = getPage(HomePO.class);
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
        VERIFY.verifyEquals(customerInfoPage.getValueInFirstnameTextbox(), userInfo.get("firstName"));
        VERIFY.verifyEquals(customerInfoPage.getValueInLastnameTextbox(), userInfo.get("lastName"));
        VERIFY.verifyEquals(customerInfoPage.getValueInCompanyTextbox(), userInfo.get("company"));
    }

}
