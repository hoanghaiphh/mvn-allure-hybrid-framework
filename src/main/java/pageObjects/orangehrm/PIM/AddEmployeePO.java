package pageObjects.orangehrm.PIM;

import io.qameta.allure.Step;
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

    @Step("Input value into Firstname textbox: {0}")
    public void sendKeysToFirstNameTextbox(String firstName) {
        WebElement element = getVisibleElement(driver, FIRST_NAME_TEXTBOX);
        clearElementText(element);
        sendKeysToElement(element, firstName);
    }

    @Step("Input value into MiddleName textbox: {0}")
    public void sendKeysToMiddleNameTextbox(String middleName) {
        WebElement element = getVisibleElement(driver, MIDDLE_NAME_TEXTBOX);
        clearElementText(element);
        sendKeysToElement(element, middleName);
    }

    @Step("Input value into Lastname textbox: {0}")
    public void sendKeysToLastNameTextbox(String lastName) {
        WebElement element = getVisibleElement(driver, LAST_NAME_TEXTBOX);
        clearElementText(element);
        sendKeysToElement(element, lastName);
    }

    @Step("Memorize value of Employee ID textbox")
    public String getValueOfEmployeeIdTextbox() {
        return getElementPropertyValue(getVisibleElement(driver, EMPLOYEE_ID_TEXTBOX), "value");
    }

    @Step("Turn on Create Login Details switch button")
    public void switchOnCreateLoginDetails() {
        WebElement element = getElement(driver, CREATE_LOGIN_DETAIL_SWITCH);
        if (!isElementSelected(element)) {
            clickOnElementByJS(driver, element);
        }
    }

    @Step("Input value into Username textbox: {0}")
    public void sendKeysToEmployeeUsernameTextbox(String userName) {
        WebElement element = getVisibleElement(driver, EMPLOYEE_USERNAME_TEXTBOX);
        clearElementText(element);
        sendKeysToElement(element, userName);
    }

    @Step("Input value into Password textbox: {0}")
    public void sendKeysToEmployeePasswordTextbox(String password) {
        WebElement element = getVisibleElement(driver, EMPLOYEE_PASSWORD_TEXTBOX);
        clearElementText(element);
        sendKeysToElement(element, password);
    }

    @Step("Input value into Confirm Password textbox: {0}")
    public void sendKeysToEmployeeConfirmPasswordTextbox(String password) {
        WebElement element = getVisibleElement(driver, EMPLOYEE_CONFIRM_PASSWORD_TEXTBOX);
        clearElementText(element);
        sendKeysToElement(element, password);
    }

    @Step("Select Status Enabled radio button")
    public void selectStatusEnabledRadio() {
        WebElement element = getElement(driver, STATUS_ENABLED_RADIO);
        if (!isElementSelected(element)) {
            clickOnElementByJS(driver, element);
        }
    }

    @Step("Click on Save button (Add Employee)")
    public void clickOnAddEmployeeSaveButton() {
        clickOnElement(getClickableElement(driver, ADD_EMPLOYEE_SAVE_BUTTON));
    }

}
