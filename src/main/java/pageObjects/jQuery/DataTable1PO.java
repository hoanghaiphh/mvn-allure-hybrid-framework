package pageObjects.jQuery;

import commons.BasePage;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import static pageUIs.jQuery.DataTable1PUI.*;

public class DataTable1PO extends BasePage {
    private WebDriver driver;

    public DataTable1PO(WebDriver driver) {
        this.driver = driver;
    }

    public void openPageNumber(String pageNumber) {
        clickOnElement(getClickableElement(driver, DYNAMIC_PAGINATION_BUTTON, pageNumber));
    }

    public boolean isPageNumberSelected(String pageNumber) {
        WebElement element = getVisibleElement(driver, DYNAMIC_PAGINATION_BUTTON, pageNumber);
        return getElementAttributeValue(element, "class").endsWith("active");
    }

    public void clearAllSearchTextboxes() {
        clearListElementsText(getVisibleListElements(driver, ALL_SEARCH_TEXTBOXES));
    }

    public void searchValueByHeader(String headerName, String value) {
        WebElement element = getVisibleElement(driver, DYNAMIC_SEARCH_TEXTBOX, headerName);
        sendKeysToElement(element, value);
        pressKeyOnElement(driver, element, Keys.ENTER);
    }

    public boolean isDataRowDisplayed(String female, String country, String male, String total) {
        return isElementDisplayed(driver, DYNAMIC_DATA_ROW, female, country, male, total);
    }

    public void removeOrEditDataRow(String action, String headerName, String value) {
        clickOnElement(getClickableElement(driver, DYNAMIC_ACTION_BUTTON, headerName.toLowerCase(), value, action));
    }

    public void submitChangesAfterEditDataRow() {
        clickOnElement(getClickableElement(driver, SUBMIT_BUTTON));
    }

    public List<String> getAllValueOfColumnName(String columnName) {
        return getListElementsText(getVisibleListElements(driver, DYNAMIC_COLUMN, columnName.toLowerCase()));
    }

    public List<String> getAllValueOfRowNumber(String rowIndex) {
        return getListElementsText(getVisibleListElements(driver, DYNAMIC_ROW, rowIndex));
    }

}
