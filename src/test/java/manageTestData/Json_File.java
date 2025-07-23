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
import testData.UserInfoJson1;
import testData.UserInfoJson2;
import testData.UserInfoJson;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Feature("User")
public class Json_File extends BaseTest {
    private CustomerInfoPO customerInfoPage;
    private HomePO homePage;
    private RegisterPO registerPage;
    private LoginPO loginPage;

    private UserInfoJson userInfo;
    private static final SoftVerification VERIFY = SoftVerification.getSoftVerification();

    @Parameters({"platform", "browserName"})
    @BeforeClass
    public void beforeClass(String platform, String browserName) {
        initDriver(platform, browserName);
        configBrowserAndOpenUrl(GlobalConstants.NOPCOMMERCE_LOCAL);
        homePage = getPage(HomePO.class);

        userInfo = UserInfoJson.getUserInfo();
        if (userInfo != null) userInfo.setRandomEmail(getRandomEmailByCurrentState("test"));
    }

    @Test /*test Json Reader*/
    public void User_00_Test_Json_Reader() {
        System.out.println(Arrays.toString(userInfo.getLanguages()));
        System.out.println(userInfo.getBuildTools());

        UserInfoJson1 json1 = UserInfoJson1.getUserInfo();
        if (json1 != null) {
            json1.getUsers().forEach(userInfo -> System.out.println(
                    "Full Name: " + userInfo.getFirstName() + " " + userInfo.getLastName()
                            + "\nCompany Nane: " + userInfo.getCompany() + "\n"));

            int randomIndex = new Random().nextInt(json1.getUsers().size());
            UserInfoJson1.User randomUser = json1.getUsers().get(randomIndex);
            System.out.println("User " + randomIndex
                    + ":\nFull Name: " + randomUser.getFirstName() + " " + randomUser.getLastName()
                    + "\tCompany Name: " + randomUser.getCompany() + "\n");
        }

        List<UserInfoJson2.User> json2 = UserInfoJson2.getUserInfo();
        if (json2 != null) {
            json2.forEach(userInfo -> System.out.println(
                    "Full Name: " + userInfo.getFirstName() + " " + userInfo.getLastName()
                            + "\nCompany Nane: " + userInfo.getCompany() + "\n"));

            int randomIndex = new Random().nextInt(json2.size());
            UserInfoJson2.User randomUser = json2.get(randomIndex);
            System.out.println("User " + randomIndex
                    + ":\nFull Name: " + randomUser.getFirstName() + " " + randomUser.getLastName()
                    + "\tCompany Name: " + randomUser.getCompany() + "\n");
        }
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
        VERIFY.verifyEquals(customerInfoPage.getValueInFirstnameTextbox(), userInfo.getName().getFirstName());
        VERIFY.verifyEquals(customerInfoPage.getValueInLastnameTextbox(), userInfo.getName().getLastName());
        VERIFY.verifyEquals(customerInfoPage.getValueInCompanyTextbox(), userInfo.getCompany());
    }

}
