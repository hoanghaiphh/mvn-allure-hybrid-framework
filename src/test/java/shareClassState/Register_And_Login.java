package shareClassState;

import commons.BaseTest;
import commons.GlobalConstants;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import lombok.Getter;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageObjects.nopcommerce.PageGenerator;
import pageObjects.nopcommerce.HomePO;
import pageObjects.nopcommerce.LoginPO;
import pageObjects.nopcommerce.RegisterPO;
import reportConfigs.SoftVerification;
import testData.UserInfoPOJO;
import utilities.FakerConfig;

import java.util.Set;

public class Register_And_Login extends BaseTest {
    private HomePO homePage;
    private RegisterPO registerPage;
    private LoginPO loginPage;

    private WebDriver driver;
    private SoftVerification soft;
    @Getter
    private static ThreadLocal<UserInfoPOJO> userInfoThreadLocal = new ThreadLocal<>();
    @Getter
    private static ThreadLocal<Set<Cookie>> cookiesThreadLocal = new ThreadLocal<>();

    @Parameters({"platform", "browserName"})
    @BeforeClass
    public void beforeClass(String platform, String browserName) {
        driver = initDriver(platform, browserName);
        configBrowserAndOpenUrl(driver, GlobalConstants.NOPCOMMERCE_LOCAL);
        homePage = PageGenerator.getHomePage(driver);

        UserInfoPOJO userInfo = UserInfoPOJO.getUserInfo();
        FakerConfig fakerVi = FakerConfig.getData("vi");
        String firstName = fakerVi.getFirstname();
        String lastName = fakerVi.getLastname();
        userInfo.setFirstName(firstName);
        userInfo.setLastName(lastName);
        userInfo.setEmailAddress(getRandomEmailByCurrentState(firstName + lastName));
        FakerConfig fakerDefault = FakerConfig.getData();
        userInfo.setCompanyName(fakerDefault.getCompanyName());
        userInfo.setPassword(fakerDefault.getPassword());

        userInfoThreadLocal.set(userInfo);

        soft = SoftVerification.getSoftVerification();
    }

    @Description("User_01_Register")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void User_01_Register() {
        registerPage = (RegisterPO) homePage.clickOnHeaderLink("Register");
        registerPage.addUserInfo(userInfoThreadLocal.get());
        registerPage.clickOnRegisterButton();
        soft.verifyEquals(registerPage.getRegisterSuccessMessage(), "Your registration completed");

        homePage = (HomePO) registerPage.clickOnHeaderLink("Log out");
    }

    @Description("User_02_Login")
    @Severity(SeverityLevel.NORMAL)
    @Test
    public void User_02_Login() {
        loginPage = (LoginPO) homePage.clickOnHeaderLink("Log in");

        homePage = loginPage.loginToSystem(userInfoThreadLocal.get());
        soft.verifyTrue(homePage.isMyAccountLinkDisplayed());

        cookiesThreadLocal.set(homePage.getCookies(driver));
    }
}
