package pageObjects.techpanda;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import testData.UserInfoPOJO;

import static pageUIs.techpanda.RegisterPUI.*;

public class RegisterPO extends BasePO {

    public RegisterPO(WebDriver driver) {
        super(driver);
    }

    @Step("Input value into Firstname textbox: {0}")
    public void inputValueIntoFirstnameTextbox(String firstname) {
        WebElement element = getVisibleElement(FIRSTNAME_TEXTBOX);
        clearElementText(element);
        sendKeysToElement(element, firstname);
    }

    @Step("Input value into Lastname textbox: {0}")
    public void inputValueIntoLastnameTextbox(String lastname) {
        WebElement element = getVisibleElement(LASTNAME_TEXTBOX);
        clearElementText(element);
        sendKeysToElement(element, lastname);
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

    @Step("Input value into Confirm Password textbox: {0}")
    public void inputValueIntoConfirmPasswordTextbox(String password) {
        WebElement element = getVisibleElement(CONFIRM_PASSWORD_TEXTBOX);
        clearElementText(element);
        sendKeysToElement(element, password);
    }

    @Step("Click on Register button")
    public void clickOnRegisterBtn() {
        clickOnElement(getClickableElement(REGISTER_BUTTON));
    }

    public void registerAccountWithInfo(UserInfoPOJO userInfo) {
        inputValueIntoFirstnameTextbox(userInfo.getFirstName());
        inputValueIntoLastnameTextbox(userInfo.getLastName());
        inputValueIntoEmailTextbox(userInfo.getEmailAddress());
        inputValueIntoPasswordTextbox(userInfo.getPassword());
        inputValueIntoConfirmPasswordTextbox(userInfo.getPassword());
        clickOnRegisterBtn();
        sendRequestAnyway();
    }

}
