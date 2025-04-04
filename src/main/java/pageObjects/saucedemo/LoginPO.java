package pageObjects.saucedemo;

import commons.BasePage;
import org.openqa.selenium.WebDriver;

import static pageUIs.saucedemo.LoginPUI.*;

public class LoginPO extends BasePage {
    private WebDriver driver;

    public LoginPO(WebDriver driver) {
        this.driver = driver;
    }

    public InventoryPO loginToSystem(String userName, String password) {
        sendKeysToElement(getVisibleElement(driver, USERNAME_TEXTBOX), userName);
        sendKeysToElement(getVisibleElement(driver, PASSWORD_TEXTBOX), password);
        clickOnElement(getClickableElement(driver, LOGIN_BUTTON));
        return PageGenerator.getInventoryPage(driver);
    }
}
