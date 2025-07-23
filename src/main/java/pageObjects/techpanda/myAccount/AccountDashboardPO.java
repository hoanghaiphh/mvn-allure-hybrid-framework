package pageObjects.techpanda.myAccount;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import pageObjects.techpanda.MyAccountPO;

import static pageUIs.techpanda.myAccount.AccountDashboardPUI.*;

public class AccountDashboardPO extends MyAccountPO {

    public AccountDashboardPO(WebDriver driver) {
        super(driver);
    }

    @Step("Verify Registration Success Message")
    public String getRegistrationSuccessMsg() {
        return getElementText(getVisibleElement(REGISTER_SUCCESS_MSG));
    }

    @Step("Verify Welcome Message")
    public String getWelcomeMsg() {
        return getElementText(getVisibleElement(WELCOME_MSG));
    }

    @Step("Verify Contact Information")
    public String getContactInfo() {
        return getElementText(getVisibleElement(CONTACT_INFO));
    }

}
