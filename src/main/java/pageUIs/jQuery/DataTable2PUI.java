package pageUIs.jQuery;

public class DataTable2PUI {

    public static final String LOAD_DATA_BUTTON = "css=button#load";

    public static final String PRECEDING_SIBLINGS_OF_DYNAMIC_COLUMN
            = "xpath=//thead//th[text()='%s']/preceding-sibling::th";

    public static final String DYNAMIC_TEXTBOX = "xpath=//tr[%s]/td[%s]/input";

    public static final String DYNAMIC_DROPDOWN = "xpath=//tr[%s]//select";

    public static final String DYNAMIC_CHECKBOX = "xpath=//tr[%s]//input[@type='checkbox']";

    public static final String DYNAMIC_DATE_PICKER = "xpath=//tr[%s]//input[contains(@id,'memberSince')]";

    public static final String DYNAMIC_ACTION_BUTTON = "xpath=//tr[%s]//button[starts-with(@title,'%s')]";

}
