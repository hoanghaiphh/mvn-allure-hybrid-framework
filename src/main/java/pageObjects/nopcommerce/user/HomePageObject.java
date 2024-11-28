package pageObjects.nopcommerce.user;

import org.openqa.selenium.WebDriver;

public class HomePageObject extends BasePageObject {
    private WebDriver driver;

    public HomePageObject(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

}
