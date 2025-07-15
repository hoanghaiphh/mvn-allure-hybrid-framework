package sqlQuery;

import commons.BaseTestRefactored;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageObjects.nopcommerceRefactored.HomePO;
import pageObjects.nopcommerceRefactored.LoginPO;
import pageObjects.nopcommerceRefactored.RegisterPO;
import pageObjects.nopcommerceRefactored.myAccount.CustomerInfoPO;
import reportConfigs.SoftVerification;
import testData.UserInfoPOJO;
import utilities.DataGenerator;
import utilities.PropertiesConfig;
import utilities.SQLUtils;

@Feature("User")
public class NopCommerce extends BaseTestRefactored {
    private CustomerInfoPO customerInfoPage;
    private HomePO homePage;
    private RegisterPO registerPage;
    private LoginPO loginPage;

    private PropertiesConfig env;
    private SQLUtils sql;
    private SoftVerification soft;

    private UserInfoPOJO userInfo;

    @Parameters({"platform", "browserName"})
    @BeforeClass
    public void beforeClass(String platform, String browserName) {
        env = PropertiesConfig.getEnvironmentProperties();
        initDriver(platform, browserName);
        configBrowserAndOpenUrl(env.getPropertyValue("app.Url"));
        sql = initSQLConnection(env);
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

        soft.verifyEquals(registerPage.getRegisterSuccessMessage(), "Your registration completed");
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

        soft.verifyTrue(homePage.isMyAccountLinkDisplayed());
    }

    @Description("User_03_MyAccount")
    @Severity(SeverityLevel.MINOR)
    @Test
    public void User_03_MyAccount() {
        homePage.clickOnHeaderLink("My account");
        customerInfoPage = getPage(CustomerInfoPO.class);

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

//        customerInfoPage.deleteUserFromDatabase(sql, userInfo.getEmailAddress());
//        soft.verifyTrue(customerInfoPage.isUserDeletedFromDatabase(sql, userInfo.getEmailAddress()));

        customerInfoPage.deleteNullRecordsFromDatabase(sql);
    }

}
