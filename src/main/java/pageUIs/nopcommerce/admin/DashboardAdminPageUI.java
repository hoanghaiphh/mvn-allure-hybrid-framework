package pageUIs.nopcommerce.admin;

public class DashboardAdminPageUI {
    public static final String LOGOUT_LINK = "xpath=//a[text()='Logout']";
    public static final String CUSTOMERS_LINK = "xpath=//li[contains(@class,'treeview')]/a/p[contains(text(),'Customers')]";
    public static final String PROMOTIONS_LINK = "xpath=//li[contains(@class,'treeview')]/a/p[contains(text(),'Promotions')]";
    public static final String SALES_LINK = "xpath=//li[contains(@class,'treeview')]/a/p[contains(text(),'Sales')]";

    public static final String SIDEBAR_DYNAMIC_LINK = "xpath=//li[contains(@class,'treeview')]/a/p[contains(text(),'%s')]";

}
