package nopcommerce.user;

import commons.BasePage;
import commons.BaseTest;
import commons.DataGenerator;
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
import testData.MockarooJson;
import testData.UserInfoJson;
import testData.UserInfoPOJO;

import java.util.Arrays;
import java.util.Random;

@Feature("User")
public class Manage_Test_Data_Json extends BaseTest {
    private CustomerInfoPageObject customerInfoPage;
    private HomePageObject homePage;
    private RegisterPageObject registerPage;
    private LoginPageObject loginPage;

    private UserInfoJson userInfo;

    @Parameters("browser")
    @BeforeClass
    public void beforeClass(String browserName) {
        driver = openBrowserWithUrl(browserName, GlobalConstants.NOPCOMMERCE_LOCAL);
        homePage = PageGenerator.getHomePage(driver);

        userInfo = UserInfoJson.getUserInfo();
        if (userInfo != null) {
            userInfo.setRandomEmail(DataGenerator.getRandomEmailByTimestamp("test", driver));

            // testJsonReader
            System.out.println(Arrays.toString(userInfo.getLanguages()));
            System.out.println(userInfo.getBuildTools());
        }

        // testJsonReader
        MockarooJson mockaroo = MockarooJson.getUserInfo();
        if (mockaroo != null) {
            mockaroo.getUsers().forEach(userInfo -> System.out.println(
                    "Full Name: " + userInfo.getFirstName() + " " + userInfo.getLastName()
                            + "\nCompany Nane: " + userInfo.getCompany() + "\n"));

            int randomIndex = new Random().nextInt(mockaroo.getUsers().size());
            MockarooJson.User randomUser = mockaroo.getUsers().get(randomIndex);
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

        verifyEquals(registerPage.getRegisterSuccessMessage(), "Your registration completed");
    }

    @Description("User_02_Login")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void User_02_Login() {
        homePage = (HomePageObject) registerPage.clickOnHeaderLink("Log out");

        loginPage = (LoginPageObject) homePage.clickOnHeaderLink("Log in");

        homePage = loginPage.loginToSystem(userInfo);

        verifyTrue(homePage.isMyAccountLinkDisplayed());
    }

    @Description("User_03_MyAccount")
    @Severity(SeverityLevel.MINOR)
    @Test
    public void User_03_MyAccount() {
        customerInfoPage = (CustomerInfoPageObject) homePage.clickOnHeaderLink("My account");

        verifyTrue(customerInfoPage.isGenderMaleSelected());
        verifyEquals(customerInfoPage.getValueInFirstnameTextbox(), userInfo.getName().getFirstName());
        verifyEquals(customerInfoPage.getValueInLastnameTextbox(), userInfo.getName().getLastName());
        verifyEquals(customerInfoPage.getValueInCompanyTextbox(), userInfo.getCompany());
    }

}
