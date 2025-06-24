package pageObjects.orangehrm.PIM.EmployeeList;

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

    public String getValueOfFirstNameTextbox() {
        return getElementPropertyValue(getVisibleElement(driver, FIRST_NAME_TEXTBOX), "value");
    }

    public String getValueOfMiddleNameTextbox() {
        return getElementPropertyValue(getVisibleElement(driver, MIDDLE_NAME_TEXTBOX), "value");
    }

    public String getValueOfLastNameTextbox() {
        return getElementPropertyValue(getVisibleElement(driver, LAST_NAME_TEXTBOX), "value");
    }

    public String getValueOfEmployeeIdTextbox() {
        return getElementPropertyValue(getVisibleElement(driver, EMPLOYEE_ID_TEXTBOX), "value");
    }

    public void sendKeysToDriverLicenseNumberTextbox(String driverLicense) {
        WebElement element = getVisibleElement(driver, DRIVER_LICENSE_NUMBER_TEXTBOX);
        clearElementText(element);
        sendKeysToElement(element, driverLicense);
    }

    public void sendKeysToLicenseExpiryDateTextbox(String licenseExpiryDate) {
        WebElement element = getVisibleElement(driver, LICENSE_EXPIRY_DATE_TEXTBOX);
        clearElementText(element);
        sendKeysToElement(element, licenseExpiryDate);
    }

    public void selectOptionInNationalityDropdown(String nationality) {
        selectOptionInCustomDropdown(driver,
                NATIONALITY_DROPDOWN_PARENT,
                NATIONALITY_DROPDOWN_OPTIONS,
                nationality);
    }

    public void selectOptionInMaritalStatusDropdown(String maritalStatus) {
        selectOptionInCustomDropdown(driver,
                MARITAL_STATUS_DROPDOWN_PARENT,
                MARITAL_STATUS_DROPDOWN_OPTIONS,
                maritalStatus);
    }

    public void sendKeysToDateOfBirthTextbox(String dateOfBirth) {
        WebElement element = getVisibleElement(driver, DATE_OF_BIRTH_TEXTBOX);
        clearElementText(element);
        sendKeysToElement(element, dateOfBirth);
    }

    public void selectGenderRadio(String gender) {
        WebElement element = getElement(driver, DYNAMIC_GENDER_RADIO, gender);
        if (!isElementSelected(element)) {
            clickOnElementByJS(driver, element);
        }
    }

    public void clickOnPersonalDetailsSaveButton() {
        clickOnElement(getClickableElement(driver, PERSONAL_DETAILS_SAVE_BUTTON));
    }

    public String getValueOfDriverLicenseNumberTextbox() {
        return getElementPropertyValue(getVisibleElement(driver, DRIVER_LICENSE_NUMBER_TEXTBOX), "value");
    }

    public String getValueOfLicenseExpiryDateTextbox() {
        return getElementPropertyValue(getVisibleElement(driver, LICENSE_EXPIRY_DATE_TEXTBOX), "value");
    }

    public String getSelectedOptionInNationalityDropdown() {
        return getElementText(getVisibleElement(driver, NATIONALITY_DROPDOWN_PARENT));
    }

    public String getSelectedOptionInMaritalStatusDropdown() {
        return getElementText(getVisibleElement(driver, MARITAL_STATUS_DROPDOWN_PARENT));
    }

    public String getValueOfDateOfBirthTextbox() {
        return getElementPropertyValue(getVisibleElement(driver, DATE_OF_BIRTH_TEXTBOX), "value");
    }

    public boolean isGenderRadioSelected(String gender) {
        return isElementSelected(getElement(driver, DYNAMIC_GENDER_RADIO, gender));
    }

    public void sendKeysToFirstNameTextbox(String firstName) {
        WebElement element = getVisibleElement(driver, FIRST_NAME_TEXTBOX);
        clearThenSendKeysToTextbox(element, firstName);
    }

    public void sendKeysToLastNameTextbox(String lastName) {
        WebElement element = getVisibleElement(driver, LAST_NAME_TEXTBOX);
        clearThenSendKeysToTextbox(element, lastName);
    }

    public Map<String, Object> getEmployeeInformationFromDatabase(SQLUtils sql, String employeeID) {
        String sqlQuery = "SELECT emp_firstname, emp_lastname, "
                + "emp_dri_lice_num, emp_dri_lice_exp_date, nation_code, emp_marital_status, emp_birthday, emp_gender "
                + "FROM orangehrm.hs_hr_employee WHERE employee_id = ?;";

        List<Map<String, Object>> result = sql.executeSELECTQuery(sqlQuery, employeeID);

        if (result.size() == 1) {
            return result.get(0);
        } else if (result.isEmpty()) {
            throw new IllegalStateException("Employee with ID: " + employeeID + " not found in the database !!!");
        } else {
            String msg = "Found " + result.size() + " Employee ID: " + employeeID + " !!! Expected exactly 1 record.";
            throw new IllegalStateException(msg);
        }
    }

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
        if (isGenderRadioSelected("Male") && !isGenderRadioSelected("Female")) {
            genderNumber = 1;
        } else if (!isGenderRadioSelected("Male") && isGenderRadioSelected("Female")) {
            genderNumber = 2;
        }
        result.put("emp_gender", genderNumber);

        return result;
    }

}
