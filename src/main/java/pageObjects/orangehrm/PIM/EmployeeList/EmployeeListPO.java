package pageObjects.orangehrm.PIM.EmployeeList;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pageObjects.orangehrm.PimPO;

import static pageUIs.orangehrm.PIM.EmployeeList.EmployeeListPUI.*;

public class EmployeeListPO extends PimPO {

    public EmployeeListPO(WebDriver driver) {
        super(driver);
    }

    public void clickOnEmployeeDeleteButton(String employeeId) {
        clickOnElement(getClickableElement(DYNAMIC_EMPLOYEE_DELETE_BUTTON, employeeId));
    }

    public void clickOnDialogPopupDeleteButton() {
        clickOnElement(getClickableElement(DIALOG_POPUP_DELETE_BUTTON));
    }

    public void sendKeysToEmployeeIdTextbox(String employeeId) {
        WebElement element = getVisibleElement(EMPLOYEE_ID_TEXTBOX);
        clearElementText(element);
        sendKeysToElement(element, employeeId);
    }

    public void clickOnEmployeeSearchButton() {
        clickOnElement(getClickableElement(EMPLOYEE_SEARCH_BUTTON));
        waitForLoading();
    }

    public String getEmployeeSearchResult() {
        return getElementText(getVisibleElement(EMPLOYEE_SEARCH_RESULT));
    }

}
