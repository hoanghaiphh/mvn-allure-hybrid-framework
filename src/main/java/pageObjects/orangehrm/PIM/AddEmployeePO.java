package pageObjects.orangehrm.PIM;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pageObjects.orangehrm.PimPO;

import static pageUIs.orangehrm.PIM.AddEmployeePUI.*;

public class AddEmployeePO extends PimPO {
    private WebDriver driver;

    public AddEmployeePO(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    public void uploadProfilePicture(String profilePicturePath) {
        sendKeysToElement(getElement(driver, PROFILE_PICTURE_UPLOAD), profilePicturePath);
    }

    public void sendKeysToFirstNameTextbox(String firstName) {
        WebElement element = getVisibleElement(driver, FIRST_NAME_TEXTBOX);
        clearElementText(element);
        sendKeysToElement(element, firstName);
    }

    public void sendKeysToMiddleNameTextbox(String middleName) {
        WebElement element = getVisibleElement(driver, MIDDLE_NAME_TEXTBOX);
        clearElementText(element);
        sendKeysToElement(element, middleName);
    }

    public void sendKeysToLastNameTextbox(String lastName) {
        WebElement element = getVisibleElement(driver, LAST_NAME_TEXTBOX);
        clearElementText(element);
        sendKeysToElement(element, lastName);
    }

    public String getValueOfEmployeeIdTextbox() {
        return getElementPropertyValue(getVisibleElement(driver, EMPLOYEE_ID_TEXTBOX), "value");
    }

    public void switchOnCreateLoginDetails() {
        WebElement element = getElement(driver, CREATE_LOGIN_DETAIL_SWITCH);
        if (!isElementSelected(element)) {
            clickOnElementByJS(driver, element);
        }
    }

    public void sendKeysToEmployeeUsernameTextbox(String userName) {
        WebElement element = getVisibleElement(driver, EMPLOYEE_USERNAME_TEXTBOX);
        clearElementText(element);
        sendKeysToElement(element, userName);
    }

    public void sendKeysToEmployeePasswordTextbox(String password) {
        WebElement element = getVisibleElement(driver, EMPLOYEE_PASSWORD_TEXTBOX);
        clearElementText(element);
        sendKeysToElement(element, password);
    }

    public void sendKeysToEmployeeConfirmPasswordTextbox(String password) {
        WebElement element = getVisibleElement(driver, EMPLOYEE_CONFIRM_PASSWORD_TEXTBOX);
        clearElementText(element);
        sendKeysToElement(element, password);
    }

    public void selectStatusEnabledRadio() {
        WebElement element = getElement(driver, STATUS_ENABLED_RADIO);
        if (!isElementSelected(element)) {
            clickOnElementByJS(driver, element);
        }
    }

    public void clickOnAddEmployeeSaveButton() {
        clickOnElement(getClickableElement(driver, ADD_EMPLOYEE_SAVE_BUTTON));
    }

}
