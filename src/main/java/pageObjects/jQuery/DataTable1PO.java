package pageObjects.jQuery;

import commons.BasePage;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pageUIs.jQuery.DataTable1PageUI;

import java.util.List;
import java.util.stream.Collectors;

public class DataTable1PO extends BasePage {
    private WebDriver driver;

    public DataTable1PO(WebDriver driver) {
        this.driver = driver;
    }

    public void openPageNumber(String pageNumber) {
        waitForElementClickable(driver, DataTable1PageUI.DYNAMIC_PAGINATION_BUTTON, pageNumber);
        clickOnElement(driver, DataTable1PageUI.DYNAMIC_PAGINATION_BUTTON, pageNumber);
    }

    public boolean isPageNumberSelected(String pageNumber) {
        waitForElementVisible(driver, DataTable1PageUI.DYNAMIC_PAGINATION_BUTTON, pageNumber);
        return getAttributeValue(driver, DataTable1PageUI.DYNAMIC_PAGINATION_BUTTON, "class", pageNumber)
                .endsWith("active");
    }

    public void clearAllSearchTextboxes() {
        waitForAllElementsVisible(driver, DataTable1PageUI.ALL_SEARCH_TEXTBOXES);
        clearAllElementsText(driver, DataTable1PageUI.ALL_SEARCH_TEXTBOXES);
    }

    public void searchValueByHeader(String headerName, String value) {
        waitForElementVisible(driver, DataTable1PageUI.DYNAMIC_SEARCH_TEXTBOX, headerName);
        sendKeysToElement(driver, DataTable1PageUI.DYNAMIC_SEARCH_TEXTBOX, value, headerName);
        sendKeyPressToElementByActions(driver, DataTable1PageUI.DYNAMIC_SEARCH_TEXTBOX, Keys.ENTER, headerName);
    }

    public boolean isDataRowDisplayed(String female, String country, String male, String total) {
        return isElementDisplayed(driver, DataTable1PageUI.DYNAMIC_DATA_ROW, female, country, male, total);
    }

    public void removeOrEditDataRow(String action, String headerName, String value) {
        waitForElementClickable(driver, DataTable1PageUI.DYNAMIC_ACTION_BUTTON, headerName.toLowerCase(), value, action);
        clickOnElement(driver, DataTable1PageUI.DYNAMIC_ACTION_BUTTON, headerName.toLowerCase(), value, action);
    }

    public void submitChangesAfterEditDataRow() {
        waitForElementClickable(driver, DataTable1PageUI.SUBMIT_BUTTON);
        clickOnElement(driver, DataTable1PageUI.SUBMIT_BUTTON);
    }

    public List<String> getAllValueOfColumnName(String columnName) {
        waitForAllElementsVisible(driver, DataTable1PageUI.DYNAMIC_COLUMN, columnName.toLowerCase());
        return getListElements(driver, DataTable1PageUI.DYNAMIC_COLUMN, columnName.toLowerCase())
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public List<String> getAllValueOfRowNumber(String rowIndex) {
        waitForAllElementsVisible(driver, DataTable1PageUI.DYNAMIC_ROW, rowIndex);
        return getListElements(driver, DataTable1PageUI.DYNAMIC_ROW, rowIndex)
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

}
