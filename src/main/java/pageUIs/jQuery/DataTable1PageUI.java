package pageUIs.jQuery;

public class DataTable1PageUI {

    public static final String DYNAMIC_PAGINATION_BUTTON = "xpath=//li[@class='qgrd-pagination-page']/a[text()='%s']";

    public static final String DYNAMIC_SEARCH_TEXTBOX = "xpath=//div[text()='%s']/parent::div/following-sibling::input";

    public static final String ALL_SEARCH_TEXTBOXES = "css=input.qgrd-header-filter";

    public static final String DYNAMIC_DATA_ROW = "xpath=//td[@data-key='females' and text()='%s']" +
            "//following-sibling::td[@data-key='country' and text()='%s']" +
            "//following-sibling::td[@data-key='males' and text()='%s']" +
            "//following-sibling::td[@data-key='total' and text()='%s']";

    public static final String DYNAMIC_ACTION_BUTTON = "xpath=//td[@data-key='%s' and text()='%s']" +
            "/preceding-sibling::td[@class='qgrd-actions']/button[contains(@class,'%s')]";

    public static final String SUBMIT_BUTTON = "xpath=//form//input[@type='submit']";

    public static final String DYNAMIC_COLUMN = "xpath=//tbody//td[@data-key='%s']";

    public static final String DYNAMIC_ROW = "xpath=//tbody/tr[%s]/td[@data-key]";

}
