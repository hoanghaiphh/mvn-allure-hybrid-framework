package pageObjects.orangehrm;

import commons.BasePage;
import commons.GlobalConstants;
import io.qameta.allure.Step;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import testData.EmployeeInfo;
import utilities.SQLUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static pageUIs.orangehrm.BasePUI.*;

public class BasePO extends BasePage {

    public BasePO(WebDriver driver) {
        super(driver);
    }

    public void waitForLoading() {
        if (isElementDisplayed(LOADING_SPINNER)) {
            waitForListElementsInvisible(LOADING_SPINNER);
        }
    }

    public void clearTextboxByKeysPressThenSendKeys(WebElement textbox, String keysToSend) {
        if (GlobalConstants.OS_NAME.toUpperCase().contains("MAC")) {
            sendKeysToElement(textbox, Keys.chord(Keys.COMMAND, "a", Keys.BACK_SPACE));
        } else {
            sendKeysToElement(textbox, Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE));
        }
        sendKeysToElement(textbox, keysToSend);
    }

    @Step("Click on Side Panel link: {0}")
    public void clickOnSidePanelLink(String linkText) {
        clickOnElement(getClickableElement(DYNAMIC_SIDEPANEL_LINK, linkText));
        waitForLoading();
    }

    @Step("Get Toast Message")
    public String getToastMessage() {
        return getElementText(getVisibleElement(TOAST_MESSAGE));
    }

    @Step("Get Employee Information from DATABASE")
    public Map<String, Object> getEmployeeInformationFromDatabase(SQLUtils sql, String employeeID) {
        String sqlQuery = """
                SELECT employee_id, emp_firstname, emp_lastname, emp_middle_name,
                emp_dri_lice_num, emp_dri_lice_exp_date, nation_code, emp_marital_status, emp_birthday, emp_gender
                FROM orangehrm.hs_hr_employee WHERE employee_id = ?;
                """;

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

    @Step("Get Employee Information from TEST DATA")
    public Map<String, Object> getEmployeeInformationFromTestData(EmployeeInfo empInfo) {
        Map<String, Object> result = new LinkedHashMap<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        result.put("employee_id", empInfo.getEmployeeId());
        result.put("emp_firstname", empInfo.getFirstName());
        result.put("emp_lastname", empInfo.getLastName());
        result.put("emp_middle_name", empInfo.getMiddleName());
        result.put("emp_dri_lice_num", empInfo.getDriverLicense());
        result.put("emp_dri_lice_exp_date", LocalDate.parse(empInfo.getLicenseExpiryDate(), formatter));
        result.put("nation_code", convertNationalityToNumber(empInfo.getNationality()));
        result.put("emp_marital_status", empInfo.getMaritalStatus());
        result.put("emp_birthday", LocalDate.parse(empInfo.getDateOfBirth(), formatter));
        result.put("emp_gender", convertGenderToNumber(empInfo.getGender()));

        return result;
    }

    protected int convertNationalityToNumber(String nationalityString) {
        int nationalityNumber = 0;
        switch (nationalityString) {
            case "American" -> nationalityNumber = 4;
            case "Australian" -> nationalityNumber = 10;
            case "British" -> nationalityNumber = 27;
        }
        return nationalityNumber;
    }

    private int convertGenderToNumber(String genderString) {
        int genderNumber = 0;
        switch (genderString) {
            case "Male" -> genderNumber = 1;
            case "Female" -> genderNumber = 2;
        }
        return genderNumber;
    }

}