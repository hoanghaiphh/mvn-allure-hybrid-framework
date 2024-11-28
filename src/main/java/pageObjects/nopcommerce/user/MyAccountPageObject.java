package pageObjects.nopcommerce.user;

import org.openqa.selenium.WebDriver;
import pageObjects.nopcommerce.PageGenerator;
import pageObjects.nopcommerce.user.myAccount.AddressesPageObject;
import pageObjects.nopcommerce.user.myAccount.ChangePasswordPageObject;
import pageObjects.nopcommerce.user.myAccount.CustomerInfoPageObject;
import pageObjects.nopcommerce.user.myAccount.OrdersPageObject;
import pageUIs.nopcommerce.user.MyAccountPageUI;

public class MyAccountPageObject extends BasePageObject {
    private WebDriver driver;

    public MyAccountPageObject(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    public AddressesPageObject clickOnAddressesLink() {
        waitForElementClickable(driver, MyAccountPageUI.ADDRESSES_LINK);
        clickOnElement(driver, MyAccountPageUI.ADDRESSES_LINK);
        return PageGenerator.getAddressesPage(driver);
    }

    public ChangePasswordPageObject clickOnChangePasswordLink() {
        waitForElementClickable(driver, MyAccountPageUI.CHANGE_PASSWORD_LINK);
        clickOnElement(driver, MyAccountPageUI.CHANGE_PASSWORD_LINK);
        return PageGenerator.getChangePasswordPage(driver);
    }

    public CustomerInfoPageObject clickOnCustomerInfoLink() {
        waitForElementClickable(driver, MyAccountPageUI.CUSTOMER_INFO_LINK);
        clickOnElement(driver, MyAccountPageUI.CUSTOMER_INFO_LINK);
        return PageGenerator.getCustomerInfoPage(driver);
    }

    public OrdersPageObject clickOnOrdersLink() {
        waitForElementClickable(driver, MyAccountPageUI.ORDERS_LINK);
        clickOnElement(driver, MyAccountPageUI.ORDERS_LINK);
        return PageGenerator.getOrdersPage(driver);
    }

    public MyAccountPageObject clickOnSidebarLink(String pageName) {
        waitForElementClickable(driver, MyAccountPageUI.SIDEBAR_DYNAMIC_LINK, pageName);
        clickOnElement(driver, MyAccountPageUI.SIDEBAR_DYNAMIC_LINK, pageName);
        return switch (pageName) {
            case ("Addresses") -> PageGenerator.getAddressesPage(driver);
            case ("Change password") -> PageGenerator.getChangePasswordPage(driver);
            case ("Customer info") -> PageGenerator.getCustomerInfoPage(driver);
            case ("Orders") -> PageGenerator.getOrdersPage(driver);
            default -> throw new RuntimeException("Page name is not valid !!!");
        };
    }

    public void clickOnSidebarLink2(String pageName) {
        waitForElementClickable(driver, MyAccountPageUI.SIDEBAR_DYNAMIC_LINK, pageName);
        clickOnElement(driver, MyAccountPageUI.SIDEBAR_DYNAMIC_LINK, pageName);
    }

}
