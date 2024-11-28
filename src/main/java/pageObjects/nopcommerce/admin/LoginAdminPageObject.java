package pageObjects.nopcommerce.admin;

import commons.BasePage;
import org.openqa.selenium.WebDriver;
import pageObjects.nopcommerce.PageGenerator;
import pageUIs.nopcommerce.admin.LoginAdminPageUI;

public class LoginAdminPageObject extends BasePage {
    private WebDriver driver;

    public LoginAdminPageObject(WebDriver driver) {
        this.driver = driver;
    }

    public void enterEmail(String emailAddress) {
        waitForElementVisible(driver, LoginAdminPageUI.EMAIL_TEXTBOX);
        clearElementText(driver, LoginAdminPageUI.EMAIL_TEXTBOX);
        sendKeysToElement(driver, LoginAdminPageUI.EMAIL_TEXTBOX, emailAddress);
    }

    public void enterPassword(String password) {
        waitForElementVisible(driver, LoginAdminPageUI.PASSWORD_TEXTBOX);
        clearElementText(driver, LoginAdminPageUI.PASSWORD_TEXTBOX);
        sendKeysToElement(driver, LoginAdminPageUI.PASSWORD_TEXTBOX, password);
    }

    public void clickOnLoginButton() {
        waitForElementClickable(driver, LoginAdminPageUI.LOGIN_BUTTON);
        clickOnElement(driver, LoginAdminPageUI.LOGIN_BUTTON);
    }

    public DashboardAdminPageObject loginToSystem(String emailAddress, String password) {
        enterEmail(emailAddress);
        enterPassword(password);
        clickOnLoginButton();
        return PageGenerator.getDashboardAdminPage(driver);
    }
}
