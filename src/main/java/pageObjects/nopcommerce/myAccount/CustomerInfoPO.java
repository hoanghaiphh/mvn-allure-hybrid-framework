package pageObjects.nopcommerce.myAccount;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import pageObjects.nopcommerce.MyAccountPO;

import static pageUIs.nopcommerce.myAccount.CustomerInfoPUI.*;

public class CustomerInfoPO extends MyAccountPO {

    public CustomerInfoPO(WebDriver driver) {
        super(driver);
    }

    @Step("Verify that Gender Male radio button was selected")
    public boolean isGenderMaleSelected() {
        return isElementSelected(getVisibleElement(GENDER_MALE_RADIO));
    }

    @Step("Get value in Firstname textbox")
    public String getValueInFirstnameTextbox() {
        return getElementAttributeValue(getVisibleElement(FIRST_NAME_TEXTBOX), "value");
    }

    @Step("Get value in Lastname textbox")
    public String getValueInLastnameTextbox() {
        return getElementAttributeValue(getVisibleElement(LAST_NAME_TEXTBOX), "value");
    }

    @Step("Get value in Company textbox")
    public String getValueInCompanyTextbox() {
        return getElementAttributeValue(getVisibleElement(COMPANY_TEXTBOX), "value");
    }

}
