package manageTestData;

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
import utilities.ExcelConfig;

import java.util.Map;

@Feature("User")
public class Excel_File extends BaseTest {
    private CustomerInfoPO customerInfoPage;
    private HomePO homePage;
    private RegisterPO registerPage;
    private LoginPO loginPage;

    private WebDriver driver;
    private SoftVerification soft;
    private Map<String, String> userInfo;

    @Parameters({"platform", "browserName"})
    @BeforeClass
    public void beforeClass(String platform, String browserName) {
        driver = initDriver(platform, browserName);
        configBrowserAndOpenUrl(driver, GlobalConstants.NOPCOMMERCE_LOCAL);
        homePage = PageGenerator.getHomePage(driver);

        soft = SoftVerification.getSoftVerification();

        userInfo = ExcelConfig.getRowData("testDataUserInfo.xlsx", "Sheet1", 1);
    }

    @Description("User_01_Register")
    @Severity(SeverityLevel.NORMAL)
    @Test
    public void User_01_Register() {
        registerPage = (RegisterPO) homePage.clickOnHeaderLink("Register");

        registerPage.addUserInfo(userInfo);

        registerPage.clickOnRegisterButton();

        soft.verifyEquals(registerPage.getRegisterSuccessMessage(), "Your registration completed");
    }

    @Description("User_02_Login")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void User_02_Login() {
        homePage = (HomePO) registerPage.clickOnHeaderLink("Log out");

        loginPage = (LoginPO) homePage.clickOnHeaderLink("Log in");

        homePage = loginPage.loginToSystem(userInfo);

        soft.verifyTrue(homePage.isMyAccountLinkDisplayed());
    }

    @Description("User_03_MyAccount")
    @Severity(SeverityLevel.MINOR)
    @Test
    public void User_03_MyAccount() {
        customerInfoPage = (CustomerInfoPO) homePage.clickOnHeaderLink("My account");

        soft.verifyTrue(customerInfoPage.isGenderMaleSelected());
        soft.verifyEquals(customerInfoPage.getValueInFirstnameTextbox(), userInfo.get("firstName"));
        soft.verifyEquals(customerInfoPage.getValueInLastnameTextbox(), userInfo.get("lastName"));
        soft.verifyEquals(customerInfoPage.getValueInCompanyTextbox(), userInfo.get("company"));
    }

}
