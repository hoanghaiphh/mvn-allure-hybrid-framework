package shareClassState;

import commons.BaseTest;
import commons.GlobalConstants;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.openqa.selenium.Cookie;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageObjects.nopcommerce.HomePO;
import pageObjects.nopcommerce.LoginPO;
import pageObjects.nopcommerce.RegisterPO;
import reportConfigs.SoftVerification;
import testData.UserInfoPOJO;
import utilities.DataGenerator;

import java.util.Set;

public class Register_And_Login extends BaseTest {
    private HomePO homePage;
    private RegisterPO registerPage;
    private LoginPO loginPage;

    private static final SoftVerification VERIFY = SoftVerification.getSoftVerification();

    static ThreadLocal<UserInfoPOJO> userInfoThreadLocal = new ThreadLocal<>();
    static ThreadLocal<Set<Cookie>> cookiesThreadLocal = new ThreadLocal<>();

    @Parameters({"platform", "browserName"})
    @BeforeClass
    public void beforeClass(String platform, String browserName) {
        initDriver(platform, browserName);
        configBrowserAndOpenUrl(GlobalConstants.NOPCOMMERCE_LOCAL);
        homePage = getPage(HomePO.class);

        UserInfoPOJO userInfo = UserInfoPOJO.getUserInfo();
        DataGenerator fakerVi = DataGenerator.create("vi");
        String firstName = fakerVi.getFirstname();
        String lastName = fakerVi.getLastname();
        userInfo.setFirstName(firstName);
        userInfo.setLastName(lastName);
        userInfo.setEmailAddress(getRandomEmailByCurrentState(firstName + lastName));
        DataGenerator fakerDefault = DataGenerator.create();
        userInfo.setCompanyName(fakerDefault.getCompanyName());
        userInfo.setPassword(fakerDefault.getPassword());
        userInfoThreadLocal.set(userInfo);
    }

    @Description("User_01_Register")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void User_01_Register() {
        homePage.clickOnHeaderLink("Register");
        registerPage = getPage(RegisterPO.class);

        registerPage.addUserInfo(userInfoThreadLocal.get());
        registerPage.clickOnRegisterButton();

        VERIFY.verifyEquals(registerPage.getRegisterSuccessMessage(), "Your registration completed");
    }

    @Description("User_02_Login")
    @Severity(SeverityLevel.NORMAL)
    @Test
    public void User_02_Login() {
        registerPage.clickOnHeaderLink("Log out");
        homePage = getPage(HomePO.class);

        homePage.clickOnHeaderLink("Log in");
        loginPage = getPage(LoginPO.class);

        loginPage.loginToSystem(userInfoThreadLocal.get());
        homePage = getPage(HomePO.class);

        VERIFY.verifyTrue(homePage.isMyAccountLinkDisplayed());

        cookiesThreadLocal.set(homePage.getCookies());
    }
}
