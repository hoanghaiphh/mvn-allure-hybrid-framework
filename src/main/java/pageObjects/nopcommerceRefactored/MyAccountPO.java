package pageObjects.nopcommerceRefactored;

import org.openqa.selenium.WebDriver;

public class MyAccountPO extends BasePageObject {
    private WebDriver driver;

    public MyAccountPO(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

}
