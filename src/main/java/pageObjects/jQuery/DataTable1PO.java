package pageObjects.jQuery;

import commons.BasePage;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import static pageUIs.jQuery.DataTable1PUI.*;

public class DataTable1PO extends BasePage {

    public DataTable1PO(WebDriver driver) {
        super(driver);
    }

    public void openPageNumber(String pageNumber) {
        clickOnElement(getClickableElement(DYNAMIC_PAGINATION_BUTTON, pageNumber));
    }

    public boolean isPageNumberSelected(String pageNumber) {
        WebElement element = getVisibleElement(DYNAMIC_PAGINATION_BUTTON, pageNumber);
        return getElementAttributeValue(element, "class").endsWith("active");
    }

    public void clearAllSearchTextboxes() {
        clearListElementsText(getVisibleListElements(ALL_SEARCH_TEXTBOXES));
    }

    public void searchValueByHeader(String headerName, String value) {
        WebElement element = getVisibleElement(DYNAMIC_SEARCH_TEXTBOX, headerName);
        sendKeysToElement(element, value);
        pressKeyOnElement(element, Keys.ENTER);
    }

    public boolean isDataRowDisplayed(String female, String country, String male, String total) {
        return isElementDisplayed(DYNAMIC_DATA_ROW, female, country, male, total);
    }

    public void removeOrEditDataRow(String action, String headerName, String value) {
        clickOnElement(getClickableElement(DYNAMIC_ACTION_BUTTON, headerName.toLowerCase(), value, action));
    }

    public void submitChangesAfterEditDataRow() {
        clickOnElement(getClickableElement(SUBMIT_BUTTON));
    }

    public List<String> getAllValueOfColumnName(String columnName) {
        return getListElementsText(getVisibleListElements(DYNAMIC_COLUMN, columnName.toLowerCase()));
    }

    public List<String> getAllValueOfRowNumber(String rowIndex) {
        return getListElementsText(getVisibleListElements(DYNAMIC_ROW, rowIndex));
    }

}
