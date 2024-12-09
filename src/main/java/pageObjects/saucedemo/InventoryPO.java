package pageObjects.saucedemo;

import commons.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pageUIs.saucedemo.InventoryPUI;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;

public class InventoryPO extends BasePage {
    private WebDriver driver;

    public InventoryPO(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Select criteria for sorting: {0}")
    public void selectSortingCriteria(String criteria) {
        waitForElementClickable(driver, InventoryPUI.SORT_DROPDOWN);
        selectOptionInDropdown(driver, InventoryPUI.SORT_DROPDOWN, criteria);
        sleepInSeconds(1);
    }

    @Step("Verify that products was sorted by criteria: {0}")
    public boolean isProductsSortedByCriteria(String criteria) {
        var productNames = getListElements(driver, InventoryPUI.PRODUCT_NAME);
        var productNamesUIOrder = productNames.stream()
                .map(WebElement::getText)
                .toList();
        var productNamesAsc = new ArrayList<>(productNamesUIOrder);
        Collections.sort(productNamesAsc);

        var productPrices = getListElements(driver, InventoryPUI.PRODUCT_PRICE);
        var productPricesUIOrder = productPrices.stream()
                .map(n -> Float.valueOf(n.getText().replace("$", "")))
                .toList();
        var productPricesAsc = new ArrayList<>(productPricesUIOrder);
        Collections.sort(productPricesAsc);

        return switch (criteria) {
            case ("Name (A to Z)") -> productNamesUIOrder.equals(productNamesAsc);
            case ("Name (Z to A)") -> productNamesUIOrder.equals(productNamesAsc.reversed());
            case ("Price (low to high)") -> productPricesUIOrder.equals(productPricesAsc);
            case ("Price (high to low)") -> productPricesUIOrder.equals(productPricesAsc.reversed());
            default -> throw new RuntimeException("Criteria is not valid !!!");
        };
    }

    @Step("Select criteria for sorting: {0}")
    public void selectSortingCriteria_2(String criteria) {
        waitForElementClickable(driver, InventoryPUI.SORT_DROPDOWN_PARENT);
        selectOptionInCustomDropdown(driver, InventoryPUI.SORT_DROPDOWN_PARENT, InventoryPUI.SORT_DROPDOWN_CHILD, criteria);
        sleepInSeconds(1);
    }

    @Step("Verify that book was sorted by Publication Date")
    public boolean isBookSortedByPublicationDate() {
        var publicationDates = getListElements(driver, InventoryPUI.BOOK_PUBLICATION_DATE);
        var formatter = new SimpleDateFormat("MMM dd, yyyy");
        var publicationDatesUIOrder = publicationDates.stream()
                .map(n -> {
                    try {
                        return formatter.parse(n.getText());
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                })
                .toList();
        var publicationDatesAsc = new ArrayList<>(publicationDatesUIOrder);
        Collections.sort(publicationDatesAsc);

        return publicationDatesUIOrder.equals(publicationDatesAsc.reversed());
    }
}
