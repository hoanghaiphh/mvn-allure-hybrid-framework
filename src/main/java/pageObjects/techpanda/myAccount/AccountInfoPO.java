package pageObjects.techpanda.myAccount;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import pageObjects.techpanda.MyAccountPO;

import static pageUIs.techpanda.myAccount.AccountInfoPUI.*;

public class AccountInfoPO extends MyAccountPO {
    private WebDriver driver;

    public AccountInfoPO(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    @Step("Verify Account Firstname")
    public String getAccountFirstname() {
        return getElementAttributeValue(getVisibleElement(driver, FIRSTNAME_TEXTBOX), "value");
    }

    @Step("Verify Account Lastname")
    public String getAccountLastname() {
        return getElementAttributeValue(getVisibleElement(driver, LASTNAME_TEXTBOX), "value");
    }

    @Step("Verify Account Email")
    public String getAccountEmail() {
        return getElementAttributeValue(getVisibleElement(driver, EMAIL_TEXTBOX), "value");
    }

}
