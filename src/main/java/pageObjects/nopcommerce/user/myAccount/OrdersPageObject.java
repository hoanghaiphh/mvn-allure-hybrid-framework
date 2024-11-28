package pageObjects.nopcommerce.user.myAccount;

import org.openqa.selenium.WebDriver;
import pageObjects.nopcommerce.user.MyAccountPageObject;

public class OrdersPageObject extends MyAccountPageObject {
    private WebDriver driver;

    public OrdersPageObject(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

}
