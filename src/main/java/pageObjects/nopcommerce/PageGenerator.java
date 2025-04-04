package pageObjects.nopcommerce;

import org.openqa.selenium.WebDriver;
import pageObjects.nopcommerce.myAccount.CustomerInfoPO;

public class PageGenerator {

    public static LoginPO getLoginPage(WebDriver driver) {
        return new LoginPO(driver);
    }

    public static RegisterPO getRegisterPage(WebDriver driver) {
        return new RegisterPO(driver);
    }

    public static HomePO getHomePage(WebDriver driver) {
        return new HomePO(driver);
    }

    public static CustomerInfoPO getCustomerInfoPage(WebDriver driver) {
        return new CustomerInfoPO(driver);
    }

}
