package pageObjects.orangehrm;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static pageUIs.orangehrm.LoginPUI.*;

public class LoginPO extends BasePO {
    private WebDriver driver;

    public LoginPO(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    public void sendKeysToUsernameTextbox(String adminUsername) {
        WebElement element = getVisibleElement(driver, USERNAME_TEXTBOX);
        clearElementText(element);
        sendKeysToElement(element, adminUsername);
    }

    public void sendKeysToPasswordTextbox(String adminPassword) {
        WebElement element = getVisibleElement(driver, PASSWORD_TEXTBOX);
        clearElementText(element);
        sendKeysToElement(element, adminPassword);
    }

    public DashboardPO clickOnLoginButton() {
        clickOnElement(getClickableElement(driver, LOGIN_BUTTON));
        waitForLoading();
        return PageGenerator.getDashboardPage(driver);
    }

}
