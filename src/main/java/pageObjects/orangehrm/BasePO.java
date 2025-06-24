package pageObjects.orangehrm;

import commons.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utilities.SQLUtils;

import java.util.List;
import java.util.Map;

import static pageUIs.orangehrm.BasePUI.*;

public class BasePO extends BasePage {
    private WebDriver driver;

    public BasePO(WebDriver driver) {
        this.driver = driver;
    }

    public void waitForLoading() {
        if (isElementDisplayed(driver, LOADING_SPINNER)) {
            waitForListElementsInvisible(driver, LOADING_SPINNER);
        }
    }

    public void clearThenSendKeysToTextbox(WebElement textbox, String keysToSend) {
        sendKeysToElement(textbox, Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE));
        sendKeysToElement(textbox, keysToSend);
    }

    @Step("Click on Side Panel link: {0}")
    public PimPO clickOnSidePanelLink(String linkText) {
        clickOnElement(getClickableElement(driver, DYNAMIC_SIDEPANEL_LINK, linkText));
        waitForLoading();
        return PageGenerator.getPIMPage(driver);
    }

    @Step("Get Toast Message")
    public String getToastMessage() {
        return getElementText(getVisibleElement(driver, TOAST_MESSAGE));
    }

    @Step("Get Information of Employee {1} from database")
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

}
