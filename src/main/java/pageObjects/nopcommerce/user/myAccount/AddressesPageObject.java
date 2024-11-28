package pageObjects.nopcommerce.user.myAccount;

import org.openqa.selenium.WebDriver;
import pageObjects.nopcommerce.user.MyAccountPageObject;

public class AddressesPageObject extends MyAccountPageObject {
    private WebDriver driver;

    public AddressesPageObject(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

}
