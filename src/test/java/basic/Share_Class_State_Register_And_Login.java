package basic;

import commons.BaseTest;
import commons.GlobalConstants;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import lombok.Getter;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageObjects.nopcommerce.PageGenerator;
import pageObjects.nopcommerce.user.HomePageObject;
import pageObjects.nopcommerce.user.LoginPageObject;
import pageObjects.nopcommerce.user.RegisterPageObject;
import pageObjects.nopcommerce.user.myAccount.CustomerInfoPageObject;
import testData.UserInfoPOJO;
import utilities.FakerConfig;
import utilities.RandomData;

import java.util.Set;

public class Share_Class_State_Register_And_Login extends BaseTest {
    private CustomerInfoPageObject customerInfoPage;
    private HomePageObject homePage;
    private RegisterPageObject registerPage;
    private LoginPageObject loginPage;

    private WebDriver driver;
    @Getter
    private static ThreadLocal<UserInfoPOJO> userInfoThreadLocal = new ThreadLocal<>();
    @Getter
    private static ThreadLocal<Set<Cookie>> cookiesThreadLocal = new ThreadLocal<>();

    @Parameters("browser")
    @BeforeClass
    public void beforeClass(String browserName) {
        driver = initDriver(browserName);
        openUrl(driver, GlobalConstants.NOPCOMMERCE_LOCAL);
        homePage = PageGenerator.getHomePage(driver);

        UserInfoPOJO userInfo = UserInfoPOJO.getUserInfo();
        FakerConfig fakerVi = FakerConfig.getData("vi");
        String firstName = fakerVi.getFirstname();
        String lastName = fakerVi.getLastname();
        userInfo.setFirstName(firstName);
        userInfo.setLastName(lastName);
        userInfo.setEmailAddress(RandomData.getRandomEmail(firstName + lastName, driver));
        FakerConfig fakerDefault = FakerConfig.getData();
        userInfo.setCompanyName(fakerDefault.getCompanyName());
        userInfo.setPassword(fakerDefault.getPassword());

        userInfoThreadLocal.set(userInfo);
    }

    @Description("User_01_Register")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void User_01_Register() {
        registerPage = (RegisterPageObject) homePage.clickOnHeaderLink("Register");
        registerPage.addUserInfo(userInfoThreadLocal.get());
        registerPage.clickOnRegisterButton();
        Assert.assertEquals(registerPage.getRegisterSuccessMessage(), "Your registration completed");

        homePage = (HomePageObject) registerPage.clickOnHeaderLink("Log out");
    }

    @Description("User_02_Login")
    @Severity(SeverityLevel.NORMAL)
    @Test
    public void User_02_Login() {
        loginPage = (LoginPageObject) homePage.clickOnHeaderLink("Log in");

        homePage = loginPage.loginToSystem(userInfoThreadLocal.get());
        Assert.assertTrue(homePage.isMyAccountLinkDisplayed());

        Set<Cookie> cookies = homePage.getCookies(driver);

        cookiesThreadLocal.set(cookies);
    }
}
