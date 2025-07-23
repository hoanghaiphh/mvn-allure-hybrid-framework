package pageObjects.saucedemo;

import commons.BasePage;
import org.openqa.selenium.WebDriver;

import static pageUIs.saucedemo.LoginPUI.*;

public class LoginPO extends BasePage {

    public LoginPO(WebDriver driver) {
        super(driver);
    }

    public void loginToSystem(String userName, String password) {
        sendKeysToElement(getVisibleElement(USERNAME_TEXTBOX), userName);
        sendKeysToElement(getVisibleElement(PASSWORD_TEXTBOX), password);
        clickOnElement(getClickableElement(LOGIN_BUTTON));
    }
}
