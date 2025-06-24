package sqlQuery;

import commons.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageObjects.nopcommerce.HomePO;
import pageObjects.nopcommerce.LoginPO;
import pageObjects.nopcommerce.PageGenerator;
import pageObjects.nopcommerce.RegisterPO;
import pageObjects.nopcommerce.myAccount.CustomerInfoPO;
import reportConfigs.SoftVerification;
import testData.UserInfoPOJO;
import utilities.DataGenerator;
import utilities.PropertiesConfig;
import utilities.SQLUtils;

@Feature("User")
public class NopCommerce extends BaseTest {
    private CustomerInfoPO customerInfoPage;
    private HomePO homePage;
    private RegisterPO registerPage;
    private LoginPO loginPage;

    private PropertiesConfig env;
    private WebDriver driver;
    private SoftVerification soft;
    private UserInfoPOJO userInfo;
    private SQLUtils sql;

    @Parameters({"platform", "browserName"})
    @BeforeClass
    public void beforeClass(String platform, String browserName) {
        env = PropertiesConfig.getEnvironmentProperties();
        driver = initDriver(platform, browserName);
        configBrowserAndOpenUrl(driver, env.getPropertyValue("app.Url"));
        homePage = PageGenerator.getHomePage(driver);

        soft = SoftVerification.getSoftVerification();

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

        sql = SQLUtils.getSQLConnection(env);
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
        soft.verifyEquals(customerInfoPage.getValueInFirstnameTextbox(), userInfo.getFirstName());
        soft.verifyEquals(customerInfoPage.getValueInLastnameTextbox(), userInfo.getLastName());
        soft.verifyEquals(customerInfoPage.getValueInCompanyTextbox(), userInfo.getCompanyName());
    }

    @Description("User_04_Database_Verification")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void User_04_Database_Verification() {
        soft.verifyEquals(
                customerInfoPage.getUserInformationFromDatabase(sql, userInfo.getEmailAddress()),
                customerInfoPage.getUserInformationFromTestData(userInfo));

        customerInfoPage.deleteUserFromDatabase(sql, userInfo.getEmailAddress());
        soft.verifyTrue(customerInfoPage.isUserDeletedFromDatabase(sql, userInfo.getEmailAddress()));

        customerInfoPage.deleteNullRecordsFromDatabase(sql);
    }

    @AfterClass
    public void closeDatabase() {
        sql.close();
    }

}
