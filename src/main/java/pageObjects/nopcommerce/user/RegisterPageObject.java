package pageObjects.nopcommerce.user;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import pageUIs.nopcommerce.user.RegisterPageUI;

public class RegisterPageObject extends BasePageObject {
    private WebDriver driver;

    public RegisterPageObject(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    @Step("Select Gender Male radio button")
    public void clickOnGenderMaleRadio() {
        waitForElementClickable(driver, RegisterPageUI.GENDER_MALE_RADIO);
        selectCheckboxOrRadio(driver, RegisterPageUI.GENDER_MALE_RADIO);
    }

    @Step("Input value into Firstname textbox: {0}")
    public void sendKeyToFirstnameTextbox(String firstName) {
        waitForElementVisible(driver, RegisterPageUI.FIRST_NAME_TEXTBOX);
        sendKeysToElement(driver, RegisterPageUI.FIRST_NAME_TEXTBOX, firstName);
    }

    @Step("Input value into Lastname textbox: {0}")
    public void sendKeyToLastnameTextbox(String lastName) {
        waitForElementVisible(driver, RegisterPageUI.LAST_NAME_TEXTBOX);
        sendKeysToElement(driver, RegisterPageUI.LAST_NAME_TEXTBOX, lastName);
    }

    @Step("Select value for Day dropdown: {0}")
    public void selectItemInDayDropdown(String dayOfBirth) {
        waitForElementClickable(driver, RegisterPageUI.DAY_DROPDOWN);
        selectOptionInDropdown(driver, RegisterPageUI.DAY_DROPDOWN, dayOfBirth);
    }

    @Step("Select value for Month dropdown: {0}")
    public void selectItemInMonthDropdown(String monthOfBirth) {
        waitForElementClickable(driver, RegisterPageUI.MONTH_DROPDOWN);
        selectOptionInDropdown(driver, RegisterPageUI.MONTH_DROPDOWN, monthOfBirth);
    }

    @Step("Select value for Year dropdown: {0}")
    public void selectItemInYearDropdown(String yearOfBirth) {
        waitForElementClickable(driver, RegisterPageUI.YEAR_DROPDOWN);
        selectOptionInDropdown(driver, RegisterPageUI.YEAR_DROPDOWN, yearOfBirth);
    }

    @Step("Input value into Email textbox: {0}")
    public void sendKeyToEmailTextbox(String emailAddress) {
        waitForElementVisible(driver, RegisterPageUI.EMAIL_TEXTBOX);
        sendKeysToElement(driver, RegisterPageUI.EMAIL_TEXTBOX, emailAddress);
    }

    @Step("Input value into Company textbox: {0}")
    public void sendKeyToCompanyTextbox(String companyName) {
        waitForElementVisible(driver, RegisterPageUI.COMPANY_TEXTBOX);
        sendKeysToElement(driver, RegisterPageUI.COMPANY_TEXTBOX, companyName);
    }

    @Step("Input value into Password textbox: {0}")
    public void sendKeyToPasswordTextbox(String password) {
        waitForElementVisible(driver, RegisterPageUI.PASSWORD_TEXTBOX);
        sendKeysToElement(driver, RegisterPageUI.PASSWORD_TEXTBOX, password);
    }

    @Step("Input value into Confirm Password textbox: {0}")
    public void sendKeyToConfirmPasswordTextbox(String password) {
        waitForElementVisible(driver, RegisterPageUI.CONFIRM_PASSWORD_TEXTBOX);
        sendKeysToElement(driver, RegisterPageUI.CONFIRM_PASSWORD_TEXTBOX, password);
    }

    @Step("Click on Register button")
    public void clickOnRegisterButton() {
        waitForElementClickable(driver, RegisterPageUI.REGISTER_BUTTON);
        clickOnElement(driver, RegisterPageUI.REGISTER_BUTTON);
    }

    @Step("Get Register success message")
    public String getRegisterSuccessMessage() {
        waitForElementVisible(driver, RegisterPageUI.REGISTER_SUCCESS_MESSAGE);
        return getElementText(driver, RegisterPageUI.REGISTER_SUCCESS_MESSAGE);
    }
}
