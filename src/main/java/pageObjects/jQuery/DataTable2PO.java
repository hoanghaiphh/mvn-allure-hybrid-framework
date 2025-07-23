package pageObjects.jQuery;

import commons.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import static pageUIs.jQuery.DataTable2PUI.*;

public class DataTable2PO extends BasePage {

    public DataTable2PO(WebDriver driver) {
        super(driver);
    }

    public void loadDataToTable() {
        clickOnElement(getClickableElement(LOAD_DATA_BUTTON));
    }

    private String getIndexOfColumn(String columnName) {
        List<WebElement> elementList = getVisibleListElements(PRECEDING_SIBLINGS_OF_DYNAMIC_COLUMN, columnName);
        return String.valueOf(elementList.size() + 1);
    }

    public void enterValueToTextbox(String rowIndex, String columnName, String keyToSend) {
        WebElement element = getVisibleElement(DYNAMIC_TEXTBOX, rowIndex, getIndexOfColumn(columnName));
        clearElementText(element);
        sendKeysToElement(element, keyToSend);
    }

    public void selectValueFromDropdown(String rowIndex, String valueToSelect) {
        selectOptionInDefaultDropdown(getClickableElement(DYNAMIC_DROPDOWN, rowIndex), valueToSelect);
    }

    public void selectOrDeselectCheckbox(String rowIndex, boolean status) {
        WebElement element = getClickableElement(DYNAMIC_CHECKBOX, rowIndex);
        if (status) selectCheckboxOrRadio(element);
        else deselectCheckbox(element);
    }

    public void enterDateToDatePicker(String rowIndex, String date) {
        WebElement element = getVisibleElement(DYNAMIC_DATE_PICKER, rowIndex);
        clearElementText(element);
        if (driver.toString().toLowerCase().contains("firefox")) {
            removeAttributeInDOM(element, "type");
            String convertedDate = date.substring(6) + "-" + date.substring(0, 2) + "-" + date.substring(3, 5);
            sendKeysToElement(element, convertedDate);
            setAttributeInDOM(element, "type", "date");
        } else {
            sendKeysToElement(element, date);
        }
    }

    public void enterAllValueToRow(String rowIndex, String company, String contactPerson, String country,
                                   boolean NPOStatus, String orderPlaced, String memberSince) {
        enterValueToTextbox(rowIndex, "Company", company);
        enterValueToTextbox(rowIndex, "Contact Person", contactPerson);
        selectValueFromDropdown(rowIndex, country);
        selectOrDeselectCheckbox(rowIndex, NPOStatus);
        enterValueToTextbox(rowIndex, "Order Placed", orderPlaced);
        enterDateToDatePicker(rowIndex, memberSince);
    }

    public void rowForAction(String rowIndex, String action) {
        clickOnElement(getClickableElement(DYNAMIC_ACTION_BUTTON, rowIndex, action));
    }

}
