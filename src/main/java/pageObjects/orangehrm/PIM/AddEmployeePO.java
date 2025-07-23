package pageObjects.orangehrm.PIM;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pageObjects.orangehrm.PimPO;
import testData.EmployeeInfo;

import static pageUIs.orangehrm.PIM.AddEmployeePUI.*;

public class AddEmployeePO extends PimPO {

    public AddEmployeePO(WebDriver driver) {
        super(driver);
    }

    @Step("Input value into Firstname textbox: {0}")
    public void sendKeysToFirstNameTextbox(String firstName) {
        WebElement element = getVisibleElement(FIRST_NAME_TEXTBOX);
        clearElementText(element);
        sendKeysToElement(element, firstName);
    }

    @Step("Input value into MiddleName textbox: {0}")
    public void sendKeysToMiddleNameTextbox(String middleName) {
        WebElement element = getVisibleElement(MIDDLE_NAME_TEXTBOX);
        clearElementText(element);
        sendKeysToElement(element, middleName);
    }

    @Step("Input value into Lastname textbox: {0}")
    public void sendKeysToLastNameTextbox(String lastName) {
        WebElement element = getVisibleElement(LAST_NAME_TEXTBOX);
        clearElementText(element);
        sendKeysToElement(element, lastName);
    }

    @Step("Memorize value of Employee ID textbox")
    public String getValueOfEmployeeIdTextbox() {
        return getElementPropertyValue(getVisibleElement(EMPLOYEE_ID_TEXTBOX), "value");
    }

    @Step("Turn on Create Login Details switch button")
    public void switchOnCreateLoginDetails() {
        WebElement element = getElement(CREATE_LOGIN_DETAIL_SWITCH);
        if (!isElementSelected(element)) {
            clickOnElementByJS(element);
        }
    }

    @Step("Input value into Username textbox: {0}")
    public void sendKeysToEmployeeUsernameTextbox(String userName) {
        WebElement element = getVisibleElement(EMPLOYEE_USERNAME_TEXTBOX);
        clearElementText(element);
        sendKeysToElement(element, userName);
    }

    @Step("Input value into Password textbox: {0}")
    public void sendKeysToEmployeePasswordTextbox(String password) {
        WebElement element = getVisibleElement(EMPLOYEE_PASSWORD_TEXTBOX);
        clearElementText(element);
        sendKeysToElement(element, password);
    }

    @Step("Input value into Confirm Password textbox: {0}")
    public void sendKeysToEmployeeConfirmPasswordTextbox(String password) {
        WebElement element = getVisibleElement(EMPLOYEE_CONFIRM_PASSWORD_TEXTBOX);
        clearElementText(element);
        sendKeysToElement(element, password);
    }

    @Step("Select Status Enabled radio button")
    public void selectStatusEnabledRadio() {
        WebElement element = getElement(STATUS_ENABLED_RADIO);
        if (!isElementSelected(element)) {
            clickOnElementByJS(element);
        }
    }

    @Step("Input Employee Information into fields")
    public void inputEmployeeInformationIntoFields(EmployeeInfo empInfo) {
        sendKeysToFirstNameTextbox(empInfo.getFirstName());
        sendKeysToMiddleNameTextbox(empInfo.getMiddleName());
        sendKeysToLastNameTextbox(empInfo.getLastName());
        switchOnCreateLoginDetails();
        selectStatusEnabledRadio();
        String userName
                = (empInfo.getFirstName() + "." + empInfo.getLastName()).toLowerCase() + "." + empInfo.getEmployeeId();
        sendKeysToEmployeeUsernameTextbox(userName);
        sendKeysToEmployeePasswordTextbox(empInfo.getPassword());
        sendKeysToEmployeeConfirmPasswordTextbox(empInfo.getPassword());
    }

    @Step("Click on Save button (Add Employee)")
    public void clickOnAddEmployeeSaveButton() {
        clickOnElement(getClickableElement(ADD_EMPLOYEE_SAVE_BUTTON));
    }

}
