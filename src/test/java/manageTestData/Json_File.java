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
import pageObjects.nopcommerce.user.HomePageObject;
import pageObjects.nopcommerce.user.LoginPageObject;
import pageObjects.nopcommerce.user.RegisterPageObject;
import pageObjects.nopcommerce.user.myAccount.CustomerInfoPageObject;
import reportConfigs.SoftVerification;
import testData.UserInfoJson1;
import testData.UserInfoJson2;
import testData.UserInfoJson;
import utilities.RandomData;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Feature("User")
public class Json_File extends BaseTest {
    private CustomerInfoPageObject customerInfoPage;
    private HomePageObject homePage;
    private RegisterPageObject registerPage;
    private LoginPageObject loginPage;

    private WebDriver driver;
    private SoftVerification soft;
    private UserInfoJson userInfo;

    @Parameters("browser")
    @BeforeClass
    public void beforeClass(String browserName) {
        driver = initDriver(browserName);
        openUrl(driver, GlobalConstants.NOPCOMMERCE_LOCAL);
        homePage = PageGenerator.getHomePage(driver);

        soft = SoftVerification.getSoftVerification();

        userInfo = UserInfoJson.getUserInfo();
        if (userInfo != null) userInfo.setRandomEmail(
                RandomData.getRandomData().getRandomEmailByTimestamp("test", driver));
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
        soft.verifyEquals(customerInfoPage.getValueInFirstnameTextbox(), userInfo.getName().getFirstName());
        soft.verifyEquals(customerInfoPage.getValueInLastnameTextbox(), userInfo.getName().getLastName());
        soft.verifyEquals(customerInfoPage.getValueInCompanyTextbox(), userInfo.getCompany());
    }

}
