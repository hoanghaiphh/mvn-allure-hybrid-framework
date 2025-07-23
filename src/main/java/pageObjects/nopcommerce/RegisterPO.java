package pageObjects.nopcommerce;

import testData.UserInfoJson;
import testData.UserInfoPOJO;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;

import java.util.Map;

import static pageUIs.nopcommerce.RegisterPUI.*;

public class RegisterPO extends BasePageObject {

    public RegisterPO(WebDriver driver) {
        super(driver);
    }

    @Step("Select Gender Male radio button")
    public void clickOnGenderMaleRadio() {
        selectCheckboxOrRadio(getClickableElement(GENDER_MALE_RADIO));
    }

    @Step("Input value into Firstname textbox: {0}")
    public void sendKeyToFirstnameTextbox(String firstName) {
        sendKeysToElement(getVisibleElement(FIRST_NAME_TEXTBOX), firstName);
    }

    @Step("Input value into Lastname textbox: {0}")
    public void sendKeyToLastnameTextbox(String lastName) {
        sendKeysToElement(getVisibleElement(LAST_NAME_TEXTBOX), lastName);
    }

    @Step("Input value into Email textbox: {0}")
    public void sendKeyToEmailTextbox(String emailAddress) {
        sendKeysToElement(getVisibleElement(EMAIL_TEXTBOX), emailAddress);
    }

    @Step("Input value into Company textbox: {0}")
    public void sendKeyToCompanyTextbox(String companyName) {
        sendKeysToElement(getVisibleElement(COMPANY_TEXTBOX), companyName);
    }

    @Step("Input value into Password textbox: {0}")
    public void sendKeyToPasswordTextbox(String password) {
        sendKeysToElement(getVisibleElement(PASSWORD_TEXTBOX), password);
    }

    @Step("Input value into Confirm Password textbox: {0}")
    public void sendKeyToConfirmPasswordTextbox(String password) {
        sendKeysToElement(getVisibleElement(CONFIRM_PASSWORD_TEXTBOX), password);
    }

    public void addUserInfo(UserInfoPOJO userInfo) {
        clickOnGenderMaleRadio();
        sendKeyToFirstnameTextbox(userInfo.getFirstName());
        sendKeyToLastnameTextbox(userInfo.getLastName());
        sendKeyToEmailTextbox(userInfo.getEmailAddress());
        sendKeyToCompanyTextbox(userInfo.getCompanyName());
        sendKeyToPasswordTextbox(userInfo.getPassword());
        sendKeyToConfirmPasswordTextbox(userInfo.getPassword());
    }

    public void addUserInfo(UserInfoJson userInfo) {
        clickOnGenderMaleRadio();
        sendKeyToFirstnameTextbox(userInfo.getName().getFirstName());
        sendKeyToLastnameTextbox(userInfo.getName().getLastName());
        sendKeyToEmailTextbox(userInfo.getRandomEmail());
        sendKeyToCompanyTextbox(userInfo.getCompany());
        sendKeyToPasswordTextbox(userInfo.getLogin().getPassword());
        sendKeyToConfirmPasswordTextbox(userInfo.getLogin().getPassword());
    }

    public void addUserInfo(Map<String, String> userInfo) {
        clickOnGenderMaleRadio();
        sendKeyToFirstnameTextbox(userInfo.get("firstName"));
        sendKeyToLastnameTextbox(userInfo.get("lastName"));
        sendKeyToEmailTextbox(userInfo.get("email"));
        sendKeyToCompanyTextbox(userInfo.get("company"));
        sendKeyToPasswordTextbox(userInfo.get("password"));
        sendKeyToConfirmPasswordTextbox(userInfo.get("password"));
    }

    @Step("Click on Register button")
    public void clickOnRegisterButton() {
        clickOnElement(getClickableElement(REGISTER_BUTTON));
    }

    @Step("Get Register success message")
    public String getRegisterSuccessMessage() {
        return getElementText(getVisibleElement(REGISTER_SUCCESS_MESSAGE));
    }

    // Pattern Object
    public void patternObject_registerUser(UserInfoPOJO userInfo) {
        selectCheckboxOrRadioByID("gender-male");
        sendKeysToTextboxByID("FirstName", userInfo.getFirstName());
        sendKeysToTextboxByID("LastName", userInfo.getLastName());
        sendKeysToTextboxByID("Email", userInfo.getEmailAddress());
        sendKeysToTextboxByID("Company", userInfo.getCompanyName());
        sendKeysToTextboxByID("Password", userInfo.getPassword());
        sendKeysToTextboxByID("ConfirmPassword", userInfo.getPassword());
        selectCheckboxOrRadioByID("Newsletter");
        clickOnButtonByText("Register");
    }
}
