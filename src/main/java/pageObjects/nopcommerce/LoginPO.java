package pageObjects.nopcommerce;

import testData.UserInfoJson;
import testData.UserInfoPOJO;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;

import java.util.Map;

import static pageUIs.nopcommerce.LoginPUI.*;

public class LoginPO extends BasePageObject {

    public LoginPO(WebDriver driver) {
        super(driver);
    }

    @Step("Input value into Email textbox: {0}")
    public void sendKeyToEmailTextbox(String emailAddress) {
        sendKeysToElement(getVisibleElement(EMAIL_TEXTBOX), emailAddress);
    }

    @Step("Input value into Password textbox: {0}")
    public void sendKeyToPasswordTextbox(String password) {
        sendKeysToElement(getVisibleElement(PASSWORD_TEXTBOX), password);
    }

    @Step("Click on Login button")
    public void clickOnLoginButton() {
        clickOnElement(getClickableElement(LOGIN_BUTTON));
    }

    public void loginToSystem(String emailAddress, String password) {
        sendKeyToEmailTextbox(emailAddress);
        sendKeyToPasswordTextbox(password);
        clickOnLoginButton();
    }

    public void loginToSystem(UserInfoPOJO userInfo) {
        sendKeyToEmailTextbox(userInfo.getEmailAddress());
        sendKeyToPasswordTextbox(userInfo.getPassword());
        clickOnLoginButton();
    }

    public void loginToSystem(UserInfoJson userInfo) {
        sendKeyToEmailTextbox(userInfo.getRandomEmail());
        sendKeyToPasswordTextbox(userInfo.getLogin().getPassword());
        clickOnLoginButton();
    }

    public void loginToSystem(Map<String, String> userInfo) {
        sendKeyToEmailTextbox(userInfo.get("email"));
        sendKeyToPasswordTextbox(userInfo.get("password"));
        clickOnLoginButton();
    }

    // Pattern Object
    public void patternObject_loginToSystem(UserInfoPOJO userInfo) {
        sendKeysToTextboxByID("Email", userInfo.getEmailAddress());
        sendKeysToTextboxByID("Password", userInfo.getPassword());
        clickOnButtonByText("Log in");
    }
}
