package pageObjects.techpanda;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import testData.UserInfoPOJO;

import static pageUIs.techpanda.LoginPUI.*;

public class LoginPO extends BasePO {

    public LoginPO(WebDriver driver) {
        super(driver);
    }

    @Step("Input value into Email textbox: {0}")
    public void inputValueIntoEmailTextbox(String email) {
        WebElement element = getVisibleElement(EMAIL_TEXTBOX);
        clearElementText(element);
        sendKeysToElement(element, email);
    }

    @Step("Input value into Password textbox: {0}")
    public void inputValueIntoPasswordTextbox(String password) {
        WebElement element = getVisibleElement(PASSWORD_TEXTBOX);
        clearElementText(element);
        sendKeysToElement(element, password);
    }

    @Step("Click on Login button")
    public void clickOnLoginBtn() {
        clickOnElement(getClickableElement(LOGIN_BUTTON));
    }

    public void loginToSystemWithInfo(UserInfoPOJO userInfo) {
        inputValueIntoEmailTextbox(userInfo.getEmailAddress());
        inputValueIntoPasswordTextbox(userInfo.getPassword());
        clickOnLoginBtn();
        sendRequestAnyway();
    }

}
