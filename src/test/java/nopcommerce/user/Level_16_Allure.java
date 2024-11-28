package nopcommerce.user;

import commons.BaseTest;
import commons.GlobalConstants;
import io.qameta.allure.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageObjects.nopcommerce.PageGenerator;
import pageObjects.nopcommerce.user.HomePageObject;
import pageObjects.nopcommerce.user.LoginPageObject;
import pageObjects.nopcommerce.user.RegisterPageObject;
import pageObjects.nopcommerce.user.myAccount.CustomerInfoPageObject;

@Feature("User")
public class Level_16_Allure extends BaseTest {

    private CustomerInfoPageObject customerInfoPage;
    private HomePageObject homePage;
    private RegisterPageObject registerPage;
    private LoginPageObject loginPage;

    private String firstName = "Automation";
    private String lastName = "Testing";
    private String dayOfBirth = "15";
    private String monthOfBirth = "October";
    private String yearOfBirth = "1989";
    private String companyName = "Online 29";
    private String emailAddress = firstName + lastName + getRandomNumber() + "@gmail.com";
    private String password = "Abcd@1234";

    @Parameters("browser")
    @BeforeClass
    public void beforeClass(String browserName) {
        driver = openBrowserWithUrl(browserName, GlobalConstants.NOPCOMMERCE_LOCAL);
        homePage = PageGenerator.getHomePage(driver);
    }

    @Description("User_01_Register")
    @Severity(SeverityLevel.NORMAL)
    @Test
    public void User_01_Register() {
        registerPage = (RegisterPageObject) homePage.clickOnHeaderLink("Register");
        
        registerPage.clickOnGenderMaleRadio();
        registerPage.sendKeyToFirstnameTextbox(firstName);
        registerPage.sendKeyToLastnameTextbox(lastName);
//        registerPage.selectItemInDayDropdown(dayOfBirth);
//        registerPage.selectItemInMonthDropdown(monthOfBirth);
//        registerPage.selectItemInYearDropdown(yearOfBirth);
        registerPage.sendKeyToEmailTextbox(emailAddress);
        registerPage.sendKeyToCompanyTextbox(companyName);
        registerPage.sendKeyToPasswordTextbox(password);
        registerPage.sendKeyToConfirmPasswordTextbox(password);
        registerPage.clickOnRegisterButton();
        
        verifyEquals(registerPage.getRegisterSuccessMessage(), "Your registration completed..."); // failed
        
        homePage = (HomePageObject) registerPage.clickOnHeaderLink("Log out");
    }

    @Description("User_02_Login")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void User_02_Login() {
        loginPage = (LoginPageObject) homePage.clickOnHeaderLink("Log in");

        homePage = loginPage.loginToSystem(emailAddress, password);

        verifyTrue(homePage.isMyAccountLinkDisplayed());
    }

    @Description("User_03_MyAccount")
    @Severity(SeverityLevel.MINOR)
    @Test
    public void User_03_MyAccount() {
        customerInfoPage = (CustomerInfoPageObject) homePage.clickOnHeaderLink("My account");

        verifyFalse(customerInfoPage.isGenderMaleSelected()); // failed
        verifyEquals(customerInfoPage.getValueInFirstnameTextbox(), firstName);
        verifyEquals(customerInfoPage.getValueInLastnameTextbox(), "Testing..."); // failed
//        verifyEquals(customerInfoPage.getValueInDayDropdown(), dayOfBirth);
//        verifyEquals(customerInfoPage.getValueInMonthDropdown(), monthOfBirth);
//        verifyEquals(customerInfoPage.getValueInYearDropdown(), yearOfBirth);
        verifyEquals(customerInfoPage.getValueInCompanyTextbox(), companyName);
    }

}
