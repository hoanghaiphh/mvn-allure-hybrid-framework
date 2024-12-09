package pageObjects.saucedemo;

import commons.BasePage;
import org.openqa.selenium.WebDriver;
import pageUIs.saucedemo.LoginPUI;

public class LoginPO extends BasePage {
    private WebDriver driver;

    public LoginPO(WebDriver driver) {
        this.driver = driver;
    }

    public InventoryPO loginToSystem(String userName, String password) {
        waitForElementVisible(driver, LoginPUI.USERNAME_TEXTBOX);
        sendKeysToElement(driver, LoginPUI.USERNAME_TEXTBOX, userName);

        waitForElementVisible(driver, LoginPUI.PASSWORD_TEXTBOX);
        sendKeysToElement(driver, LoginPUI.PASSWORD_TEXTBOX, password);

        waitForElementClickable(driver, LoginPUI.LOGIN_BUTTON);
        clickOnElement(driver, LoginPUI.LOGIN_BUTTON);

        return PageGenerator.getInventoryPage(driver);
    }
}
