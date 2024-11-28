package pageObjects.nopcommerce.user.myAccount;

import org.openqa.selenium.WebDriver;
import pageObjects.nopcommerce.user.MyAccountPageObject;

public class ChangePasswordPageObject extends MyAccountPageObject {
    private WebDriver driver;

    public ChangePasswordPageObject(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

}
