package pageUIs.nopcommerce.user;

public class BasePageUI {
    public static final String REGISTER_LINK = "class=ico-register";
    public static final String MY_ACCOUNT_LINK = "xpath=//a[@class='ico-account' and text()='My account']";
    public static final String LOGIN_LINK = "class=ico-login";
    public static final String LOGOUT_LINK = "class=ico-logout";

    // Page Element Component Pattern Object

    public static final String DYNAMIC_HEADER_LINK_TEXT = "xpath=//div[@class='header-links']//a[text()='%s']";
    public static final String DYNAMIC_TEXTBOX_ID = "css=input#%s";
    public static final String DYNAMIC_BUTTON_TEXT = "xpath=//button[text()='%s']";
    public static final String DYNAMIC_CHECKBOX_RADIO_ID = "css=input#%s";

}
