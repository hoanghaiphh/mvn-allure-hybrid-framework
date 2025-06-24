package pageObjects.orangehrm.PIM.EmployeeList;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pageObjects.orangehrm.PimPO;
import utilities.SQLUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static pageUIs.orangehrm.PIM.EmployeeList.PersonalDetailsPUI.*;

public class PersonalDetailsPO extends PimPO {
    private WebDriver driver;

    public PersonalDetailsPO(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    @Step("Get value of Firstname textbox")
    public String getValueOfFirstNameTextbox() {
        return getElementPropertyValue(getVisibleElement(driver, FIRST_NAME_TEXTBOX), "value");
    }

    @Step("Get value of MiddleName textbox")
    public String getValueOfMiddleNameTextbox() {
        return getElementPropertyValue(getVisibleElement(driver, MIDDLE_NAME_TEXTBOX), "value");
    }

    @Step("Get value of Lastname textbox")
    public String getValueOfLastNameTextbox() {
        return getElementPropertyValue(getVisibleElement(driver, LAST_NAME_TEXTBOX), "value");
    }

    @Step("Get value of Employee ID textbox")
    public String getValueOfEmployeeIdTextbox() {
        return getElementPropertyValue(getVisibleElement(driver, EMPLOYEE_ID_TEXTBOX), "value");
    }

    @Step("Input value into Driver License Number textbox: {0}")
    public void sendKeysToDriverLicenseNumberTextbox(String driverLicense) {
        WebElement element = getVisibleElement(driver, DRIVER_LICENSE_NUMBER_TEXTBOX);
        clearElementText(element);
        sendKeysToElement(element, driverLicense);
    }

    @Step("Input value into License Expiry Date textbox: {0}")
    public void sendKeysToLicenseExpiryDateTextbox(String licenseExpiryDate) {
        WebElement element = getVisibleElement(driver, LICENSE_EXPIRY_DATE_TEXTBOX);
        clearElementText(element);
        sendKeysToElement(element, licenseExpiryDate);
    }

    @Step("Select option in Nationality dropdown list: {0}")
    public void selectOptionInNationalityDropdown(String nationality) {
        selectOptionInCustomDropdown(driver,
                NATIONALITY_DROPDOWN_PARENT,
                NATIONALITY_DROPDOWN_OPTIONS,
                nationality);
    }

    @Step("Select option in Marital Status dropdown list: {0}")
    public void selectOptionInMaritalStatusDropdown(String maritalStatus) {
        selectOptionInCustomDropdown(driver,
                MARITAL_STATUS_DROPDOWN_PARENT,
                MARITAL_STATUS_DROPDOWN_OPTIONS,
                maritalStatus);
    }

    @Step("Input value into Date Of Birth textbox: {0}")
    public void sendKeysToDateOfBirthTextbox(String dateOfBirth) {
        WebElement element = getVisibleElement(driver, DATE_OF_BIRTH_TEXTBOX);
        clearElementText(element);
        sendKeysToElement(element, dateOfBirth);
    }

    @Step("Click on {0} Gender radio button")
    public void selectGenderRadio(String gender) {
        WebElement element = getElement(driver, DYNAMIC_GENDER_RADIO, gender);
        if (!isElementSelected(element)) {
            clickOnElementByJS(driver, element);
        }
    }

    @Step("Click on Save button (Personal Details)")
    public void clickOnPersonalDetailsSaveButton() {
        clickOnElement(getClickableElement(driver, PERSONAL_DETAILS_SAVE_BUTTON));
    }

    @Step("Get value of Driver License Number textbox")
    public String getValueOfDriverLicenseNumberTextbox() {
        return getElementPropertyValue(getVisibleElement(driver, DRIVER_LICENSE_NUMBER_TEXTBOX), "value");
    }

    @Step("Get value of License Expiry Date textbox")
    public String getValueOfLicenseExpiryDateTextbox() {
        return getElementPropertyValue(getVisibleElement(driver, LICENSE_EXPIRY_DATE_TEXTBOX), "value");
    }

    @Step("Get selected option in Nationality dropdown list")
    public String getSelectedOptionInNationalityDropdown() {
        return getElementText(getVisibleElement(driver, NATIONALITY_DROPDOWN_PARENT));
    }
    @Step("Get selected option in Marital Status dropdown list")
    public String getSelectedOptionInMaritalStatusDropdown() {
        return getElementText(getVisibleElement(driver, MARITAL_STATUS_DROPDOWN_PARENT));
    }

    @Step("Get value of Date Of Birth textbox")
    public String getValueOfDateOfBirthTextbox() {
        return getElementPropertyValue(getVisibleElement(driver, DATE_OF_BIRTH_TEXTBOX), "value");
    }

    @Step("Is {0} Gender radio button selected")
    public boolean isGenderRadioSelected(String gender) {
        return isElementSelected(getElement(driver, DYNAMIC_GENDER_RADIO, gender));
    }

    @Step("Input value into Firstname textbox: {0}")
    public void sendKeysToFirstNameTextbox(String firstName) {
        WebElement element = getVisibleElement(driver, FIRST_NAME_TEXTBOX);
        clearThenSendKeysToTextbox(element, firstName);
    }

    @Step("Input value into Lastname textbox: {0}")
    public void sendKeysToLastNameTextbox(String lastName) {
        WebElement element = getVisibleElement(driver, LAST_NAME_TEXTBOX);
        clearThenSendKeysToTextbox(element, lastName);
    }

    @Step("Get Employee Information from UI")
    public Map<String, Object> getEmployeeInformationFromUI() {
        Map<String, Object> result = new LinkedHashMap<>();

        result.put("emp_firstname", getValueOfFirstNameTextbox());
        result.put("emp_lastname", getValueOfLastNameTextbox());
        result.put("emp_dri_lice_num", getValueOfDriverLicenseNumberTextbox());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        result.put("emp_dri_lice_exp_date", LocalDate.parse(getValueOfLicenseExpiryDateTextbox(), formatter));

        int nationCode = 0;
        switch (getSelectedOptionInNationalityDropdown()) {
            case "American" -> nationCode = 4;
            case "Australian" -> nationCode = 10;
            case "British" -> nationCode = 27;
        }
        result.put("nation_code", nationCode);

        result.put("emp_marital_status", getSelectedOptionInMaritalStatusDropdown());
        result.put("emp_birthday", LocalDate.parse(getValueOfDateOfBirthTextbox(), formatter));

        int genderNumber = 0;
        if (isGenderRadioSelected("Male") && !isGenderRadioSelected("Female")) { // TODO: allure report meaningless log
            genderNumber = 1;
        } else if (!isGenderRadioSelected("Male") && isGenderRadioSelected("Female")) {
            genderNumber = 2;
        }
        result.put("emp_gender", genderNumber);

        return result;
    }

}
