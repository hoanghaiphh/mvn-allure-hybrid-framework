package pageObjects.techpanda;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pageObjects.techpanda.myAccount.AccountDashboardPO;
import testData.UserInfoPOJO;

import static pageUIs.techpanda.RegisterPUI.*;

public class RegisterPO extends BasePO {
    private WebDriver driver;

    public RegisterPO(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    @Step("Input value into Firstname textbox: {0}")
    public void inputValueIntoFirstnameTextbox(String firstname) {
        WebElement element = getVisibleElement(driver, FIRSTNAME_TEXTBOX);
        clearElementText(element);
        sendKeysToElement(element, firstname);
    }

    @Step("Input value into Lastname textbox: {0}")
    public void inputValueIntoLastnameTextbox(String lastname) {
        WebElement element = getVisibleElement(driver, LASTNAME_TEXTBOX);
        clearElementText(element);
        sendKeysToElement(element, lastname);
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

    @Step("Input value into Confirm Password textbox: {0}")
    public void inputValueIntoConfirmPasswordTextbox(String password) {
        WebElement element = getVisibleElement(driver, CONFIRM_PASSWORD_TEXTBOX);
        clearElementText(element);
        sendKeysToElement(element, password);
    }

    @Step("Click on Register button")
    public void clickOnRegisterBtn() {
        clickOnElement(getClickableElement(driver, REGISTER_BUTTON));
    }

    public AccountDashboardPO registerAccountWithInfo(UserInfoPOJO userInfo) {
        inputValueIntoFirstnameTextbox(userInfo.getFirstName());
        inputValueIntoLastnameTextbox(userInfo.getLastName());
        inputValueIntoEmailTextbox(userInfo.getEmailAddress());
        inputValueIntoPasswordTextbox(userInfo.getPassword());
        inputValueIntoConfirmPasswordTextbox(userInfo.getPassword());
        clickOnRegisterBtn();
        sendRequestAnyway();
        return PageGenerator.getAccountDashboardPage(driver);
    }

}
