package pageObjects.saucedemo;

import commons.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static pageUIs.saucedemo.InventoryPUI.*;

public class InventoryPO extends BasePage {

    public InventoryPO(WebDriver driver) {
        super(driver);
    }

    @Step("Select criteria for sorting: {0}")
    public void selectSortingCriteria(String criteria) {
        selectOptionInDefaultDropdown(getClickableElement(SORT_DROPDOWN), criteria);
        sleepThread(1);
    }

    @Step("Verify that products was sorted by criteria: {0}")
    public boolean isProductsSortedByCriteria(String criteria) {
        List<String> productNamesUIOrder = getVisibleListElements(PRODUCT_NAME)
                .stream()
                .map(WebElement::getText)
                .toList();
        List<String> productNamesAsc = new ArrayList<>(productNamesUIOrder);
        Collections.sort(productNamesAsc);

        List<Float> productPricesUIOrder = getVisibleListElements(PRODUCT_PRICE)
                .stream()
                .map(n -> Float.valueOf(n.getText().replace("$", "")))
                .toList();
        List<Float> productPricesAsc = new ArrayList<>(productPricesUIOrder);
        Collections.sort(productPricesAsc);

        return switch (criteria) {
            case ("Name (A to Z)") -> productNamesUIOrder.equals(productNamesAsc);
            case ("Name (Z to A)") -> productNamesUIOrder.equals(productNamesAsc.reversed());
            case ("Price (low to high)") -> productPricesUIOrder.equals(productPricesAsc);
            case ("Price (high to low)") -> productPricesUIOrder.equals(productPricesAsc.reversed());
            default -> throw new RuntimeException("Criteria is not valid !!!");
        };
    }

    @Step("Sort books by Publication Date")
    public void sortBooksByPublicationDate() {
        waitForListElementsInvisible(LOADING_SPINNER);
        selectOptionInCustomDropdown(SORT_DROPDOWN_PARENT, SORT_DROPDOWN_CHILD, "Publication Date");
        waitForListElementsInvisible(LOADING_SPINNER);
    }

    @Step("Verify that books was sorted by Publication Date")
    public boolean isBookSortedByPublicationDate() {
        List<Date> publicationDatesUIOrder = getVisibleListElements(BOOK_PUBLICATION_DATE)
                .stream()
                .map(n -> {
                    try {
                        return new SimpleDateFormat("MMM dd, yyyy").parse(n.getText());
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                })
                .toList();
        List<Date> publicationDatesAsc = new ArrayList<>(publicationDatesUIOrder);
        Collections.sort(publicationDatesAsc);

        return publicationDatesUIOrder.equals(publicationDatesAsc.reversed());
    }
}
