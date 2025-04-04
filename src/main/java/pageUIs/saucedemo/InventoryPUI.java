package pageUIs.saucedemo;

public class InventoryPUI {

    public static final String SORT_DROPDOWN = "css=select.product_sort_container";

    public static final String PRODUCT_NAME = "css=div.inventory_item_name";

    public static final String PRODUCT_PRICE = "css=div.inventory_item_price";

    public static final String SORT_DROPDOWN_PARENT = "xpath=//span[text()='Sort by:']";

    public static final String SORT_DROPDOWN_CHILD
            = "xpath=//div[contains(@id,'a-popover') and @aria-hidden='false']//a[@class='a-dropdown-link']";

    public static final String BOOK_PUBLICATION_DATE
            = "xpath=//div[contains(@class,'s-main-slot')]/div[@data-component-type='s-search-result']" +
            "//span[contains(@class,'a-text-normal')]";

    public static final String LOADING_SPINNER = "css=span.a-spinner";

}
