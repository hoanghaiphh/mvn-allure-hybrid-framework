package pageObjects.nopcommerce;

import org.openqa.selenium.WebDriver;

public class HomePO extends BasePageObject {
    private WebDriver driver;

    public HomePO(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

}
