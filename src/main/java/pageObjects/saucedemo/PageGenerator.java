package pageObjects.saucedemo;

import org.openqa.selenium.WebDriver;

public class PageGenerator {

    public static LoginPO getLoginPage(WebDriver driver) {
        return new LoginPO(driver);
    }

    public static InventoryPO getInventoryPage(WebDriver driver) {
        return new InventoryPO(driver);
    }

}
