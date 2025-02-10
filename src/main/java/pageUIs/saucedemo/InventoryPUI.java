package pageUIs.saucedemo;

public class InventoryPUI {
    public static final String SORT_DROPDOWN = "css=select.product_sort_container";
    public static final String PRODUCT_NAME = "css=div.inventory_item_name";
    public static final String PRODUCT_PRICE = "css=div.inventory_item_price";

    public static final String SORT_DROPDOWN_PARENT = "xpath=//span[text()='Sort by:']";
    public static final String SORT_DROPDOWN_CHILD
            = "xpath=//div[contains(@id,'a-popover') and @aria-hidden='false']//a[@class='a-dropdown-link']";

    public static final String BOOK_PUBLICATION_DATE
            = "css=div[class='a-row']>span.a-size-base.a-color-secondary.a-text-normal";

}
