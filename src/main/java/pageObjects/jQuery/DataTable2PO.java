package pageObjects.jQuery;

import commons.BasePage;
import org.openqa.selenium.WebDriver;
import pageUIs.jQuery.DataTable2PageUI;

public class DataTable2PO extends BasePage {
    private WebDriver driver;

    public DataTable2PO(WebDriver driver) {
        this.driver = driver;
    }

    public void loadDataToTable() {
        waitForElementClickable(driver, DataTable2PageUI.LOAD_DATA_BUTTON);
        clickOnElement(driver, DataTable2PageUI.LOAD_DATA_BUTTON);
    }

    public String getIndexOfColumn(String columnName) {
        return String.valueOf(getListElementsSize(driver, DataTable2PageUI.DYNAMIC_PRECEDING_SIBLING_OF_COLUMN, columnName) +1);
    }

    public void enterValueToTextbox(String rowIndex, String columnName, String keyToSend) {
        String columnIndex = getIndexOfColumn(columnName);
        waitForElementVisible(driver, DataTable2PageUI.DYNAMIC_TEXTBOX, rowIndex, columnIndex);
        clearElementText(driver, DataTable2PageUI.DYNAMIC_TEXTBOX, rowIndex, columnIndex);
        sendKeysToElement(driver, DataTable2PageUI.DYNAMIC_TEXTBOX, keyToSend, rowIndex, columnIndex);
    }

    public void selectValueFromDropdown(String rowIndex, String valueToSelect) {
        waitForElementClickable(driver, DataTable2PageUI.DYNAMIC_DROPDOWN, rowIndex);
        selectOptionInDropdown(driver, DataTable2PageUI.DYNAMIC_DROPDOWN, valueToSelect, rowIndex);
    }

    public void selectOrDeselectCheckbox(String rowIndex, boolean status) {
        waitForElementClickable(driver, DataTable2PageUI.DYNAMIC_CHECKBOX, rowIndex);
        if (status) {
            selectCheckboxOrRadio(driver, DataTable2PageUI.DYNAMIC_CHECKBOX, rowIndex);
        } else {
            deselectCheckbox(driver, DataTable2PageUI.DYNAMIC_CHECKBOX, rowIndex);
        }
    }

    public void enterDateToDatePicker(String rowIndex, String date) {
        String dynamicLocator = DataTable2PageUI.DYNAMIC_DATE_PICKER;
        waitForElementVisible(driver, dynamicLocator, rowIndex);
        clearElementText(driver, dynamicLocator, rowIndex);
        if (driver.toString().toLowerCase().contains("firefox")) {
            removeAttributeInDOM(driver, dynamicLocator, "type", rowIndex);
            String convertedDate = date.substring(6) + "-" + date.substring(0, 2) + "-" + date.substring(3,5);
            sendKeysToElement(driver, dynamicLocator, convertedDate, rowIndex);
            setAttributeInDOM(driver, dynamicLocator, "type", "date", rowIndex);
        } else {
            sendKeysToElement(driver, dynamicLocator, date, rowIndex);
        }
    }

    public void enterAllValueToRow(String rowIndex, String company, String contactPerson, String country, boolean NPOStatus, String orderPlaced, String memberSince) {
        enterValueToTextbox(rowIndex, "Company", company);
        enterValueToTextbox(rowIndex, "Contact Person", contactPerson);
        selectValueFromDropdown(rowIndex, country);
        selectOrDeselectCheckbox(rowIndex, NPOStatus);
        enterValueToTextbox(rowIndex, "Order Placed", orderPlaced);
        enterDateToDatePicker(rowIndex, memberSince);
    }

    public void rowForAction(String rowIndex, String action) {
        waitForElementClickable(driver, DataTable2PageUI.DYNAMIC_ACTION_BUTTON, rowIndex, action);
        clickOnElement(driver, DataTable2PageUI.DYNAMIC_ACTION_BUTTON, rowIndex, action);
    }

}
