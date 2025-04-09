package pageObjects.techpanda;

import org.openqa.selenium.WebDriver;
import pageObjects.techpanda.myAccount.AccountDashboardPO;
import pageObjects.techpanda.myAccount.AccountInfoPO;

public class PageGenerator {

    public static HomePO getHomePage(WebDriver driver) {
        return new HomePO(driver);
    }

    public static LoginPO getLoginPage(WebDriver driver) {
        return new LoginPO(driver);
    }

    public static RegisterPO getRegisterPage(WebDriver driver) {
        return new RegisterPO(driver);
    }

    public static MyAccountPO getMyAccountPage(WebDriver driver) {
        return new MyAccountPO(driver);
    }

    public static AccountDashboardPO getAccountDashboardPage(WebDriver driver) {
        return new AccountDashboardPO(driver);
    }

    public static AccountInfoPO getAccountInfoPage(WebDriver driver) {
        return new AccountInfoPO(driver);
    }

}
