package pageUIs.orangehrm.PIM.EmployeeList;

public class PersonalDetailsPUI {

    public static final String FIRST_NAME_TEXTBOX = "name=firstName";

    public static final String MIDDLE_NAME_TEXTBOX = "name=middleName";

    public static final String LAST_NAME_TEXTBOX = "name=lastName";

    public static final String EMPLOYEE_ID_TEXTBOX =
            "xpath=//label[text()='Employee Id']/parent::div/following-sibling::div/input";

    public static final String DRIVER_LICENSE_NUMBER_TEXTBOX =
            "xpath=//label[text()=\"Driver's License Number\"]/parent::div/following-sibling::div/input";

    public static final String LICENSE_EXPIRY_DATE_TEXTBOX =
            "xpath=//label[text()='License Expiry Date']/parent::div/following-sibling::div//input";

    public static final String NATIONALITY_DROPDOWN_PARENT =
            "xpath=//label[text()='Nationality']/parent::div/following-sibling::div//div[@class='oxd-select-text-input']";

    public static final String NATIONALITY_DROPDOWN_OPTIONS =
            "xpath=//label[text()='Nationality']/parent::div/following-sibling::div//span";

    public static final String MARITAL_STATUS_DROPDOWN_PARENT =
            "xpath=//label[text()='Marital Status']/parent::div/following-sibling::div//div[@class='oxd-select-text-input']";

    public static final String MARITAL_STATUS_DROPDOWN_OPTIONS =
            "xpath=//label[text()='Marital Status']/parent::div/following-sibling::div//span";

    public static final String DATE_OF_BIRTH_TEXTBOX =
            "xpath=//label[text()='Date of Birth']/parent::div/following-sibling::div//input";

    public static final String DYNAMIC_GENDER_RADIO =
            "xpath=//label[text()='Gender']/parent::div/following-sibling::div//label[text()='%s']/input";

    public static final String PERSONAL_DETAILS_SAVE_BUTTON =
            "xpath=//h6[text()='Personal Details']/following-sibling::form//button[contains(string(),'Save')]";

}
