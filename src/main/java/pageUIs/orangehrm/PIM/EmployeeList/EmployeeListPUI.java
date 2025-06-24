package pageUIs.orangehrm.PIM.EmployeeList;

public class EmployeeListPUI {

    public static final String DYNAMIC_EMPLOYEE_DELETE_BUTTON =
            "xpath=//div[text()='%s']/parent::div/parent::div//button/i[contains(@class,'trash')]";

    public static final String DIALOG_POPUP_DELETE_BUTTON =
            "xpath=//div[contains(@class,'dialog-popup')]//button[contains(string(),'Yes, Delete')]";

    public static final String EMPLOYEE_ID_TEXTBOX =
            "xpath=//label[text()='Employee Id']/parent::div/following-sibling::div/input";

    public static final String EMPLOYEE_SEARCH_BUTTON = "xpath=//button[contains(string(),'Search')]";

    public static final String EMPLOYEE_SEARCH_RESULT =
            "css=div.orangehrm-paper-container div.orangehrm-horizontal-padding>span";

}
