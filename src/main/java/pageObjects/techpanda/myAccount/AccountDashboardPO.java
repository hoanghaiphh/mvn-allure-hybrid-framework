package pageObjects.techpanda.myAccount;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import pageObjects.techpanda.MyAccountPO;

import static pageUIs.techpanda.myAccount.AccountDashboardPUI.*;

public class AccountDashboardPO extends MyAccountPO {
    private WebDriver driver;

    public AccountDashboardPO(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    @Step("Verify Registration Success Message")
    public String getRegistrationSuccessMsg() {
        return getElementText(getVisibleElement(driver, REGISTER_SUCCESS_MSG));
    }

    @Step("Verify Welcome Message")
    public String getWelcomeMsg() {
        return getElementText(getVisibleElement(driver, WELCOME_MSG));
    }

    @Step("Verify Contact Information")
    public String getContactInfo() {
        return getElementText(getVisibleElement(driver, CONTACT_INFO));
    }

}
