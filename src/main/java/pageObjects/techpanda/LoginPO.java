package pageObjects.techpanda;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pageObjects.techpanda.myAccount.AccountDashboardPO;
import testData.UserInfoPOJO;

import static pageUIs.techpanda.LoginPUI.*;

public class LoginPO extends BasePO {
    private WebDriver driver;

    public LoginPO(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    @Step("Input value into Email textbox: {0}")
    public void inputValueIntoEmailTextbox(String email) {
        WebElement element = getVisibleElement(driver, EMAIL_TEXTBOX);
        clearElementText(element);
        sendKeysToElement(element, email);
    }

    @Step("Input value into Password textbox: {0}")
    public void inputValueIntoPasswordTextbox(String password) {
        WebElement element = getVisibleElement(driver, PASSWORD_TEXTBOX);
        clearElementText(element);
        sendKeysToElement(element, password);
    }

    @Step("Click on Login button")
    public void clickOnLoginBtn() {
        clickOnElement(getClickableElement(driver, LOGIN_BUTTON));
    }

    public AccountDashboardPO loginToSystemWithInfo(UserInfoPOJO userInfo) {
        inputValueIntoEmailTextbox(userInfo.getEmailAddress());
        inputValueIntoPasswordTextbox(userInfo.getPassword());
        clickOnLoginBtn();
        sendRequestAnyway();
        return new AccountDashboardPO(driver);
    }

}
