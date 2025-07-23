package pageObjects.orangehrm;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static pageUIs.orangehrm.LoginPUI.*;

public class LoginPO extends BasePO {

    public LoginPO(WebDriver driver) {
        super(driver);
    }

    @Step("Input value into Username textbox: {0}")
    public void sendKeysToUsernameTextbox(String adminUsername) {
        WebElement element = getVisibleElement(USERNAME_TEXTBOX);
        clearElementText(element);
        sendKeysToElement(element, adminUsername);
    }

    @Step("Input value into Password textbox: {0}")
    public void sendKeysToPasswordTextbox(String adminPassword) {
        WebElement element = getVisibleElement(PASSWORD_TEXTBOX);
        clearElementText(element);
        sendKeysToElement(element, adminPassword);
    }

    @Step("Click on Login button")
    public void clickOnLoginButton() {
        clickOnElement(getClickableElement(LOGIN_BUTTON));
        waitForLoading();
    }

}
