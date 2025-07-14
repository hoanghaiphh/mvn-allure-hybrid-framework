package pageObjects.nopcommerceRefactored;

import testData.UserInfoJson;
import testData.UserInfoPOJO;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;

import java.util.Map;

import static pageUIs.nopcommerce.LoginPUI.*;

public class LoginPO extends BasePageObject {
    private WebDriver driver;

    public LoginPO(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    @Step("Input value into Email textbox: {0}")
    public void sendKeyToEmailTextbox(String emailAddress) {
        sendKeysToElement(getVisibleElement(driver, EMAIL_TEXTBOX), emailAddress);
    }

    @Step("Input value into Password textbox: {0}")
    public void sendKeyToPasswordTextbox(String password) {
        sendKeysToElement(getVisibleElement(driver, PASSWORD_TEXTBOX), password);
    }

    @Step("Click on Login button")
    public void clickOnLoginButton() {
        clickOnElement(getClickableElement(driver, LOGIN_BUTTON));
    }

    public HomePO loginToSystem(String emailAddress, String password) {
        sendKeyToEmailTextbox(emailAddress);
        sendKeyToPasswordTextbox(password);
        clickOnLoginButton();
        return PageGenerator.getPage(HomePO.class, driver);
    }

    public HomePO loginToSystem(UserInfoPOJO userInfo) {
        sendKeyToEmailTextbox(userInfo.getEmailAddress());
        sendKeyToPasswordTextbox(userInfo.getPassword());
        clickOnLoginButton();
        return PageGenerator.getPage(HomePO.class, driver);
    }

    public HomePO loginToSystem(UserInfoJson userInfo) {
        sendKeyToEmailTextbox(userInfo.getRandomEmail());
        sendKeyToPasswordTextbox(userInfo.getLogin().getPassword());
        clickOnLoginButton();
        return PageGenerator.getPage(HomePO.class, driver);
    }

    public HomePO loginToSystem(Map<String, String> userInfo) {
        sendKeyToEmailTextbox(userInfo.get("email"));
        sendKeyToPasswordTextbox(userInfo.get("password"));
        clickOnLoginButton();
        return PageGenerator.getPage(HomePO.class, driver);
    }

    // Pattern Object
    public HomePO patternObject_loginToSystem(UserInfoPOJO userInfo) {
        sendKeysToTextboxByID("Email", userInfo.getEmailAddress());
        sendKeysToTextboxByID("Password", userInfo.getPassword());
        clickOnButtonByText("Log in");
        return PageGenerator.getPage(HomePO.class, driver);
    }
}
