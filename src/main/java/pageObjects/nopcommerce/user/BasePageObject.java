package pageObjects.nopcommerce.user;

import commons.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import pageObjects.nopcommerce.PageGenerator;
import pageObjects.nopcommerce.user.myAccount.CustomerInfoPageObject;
import pageUIs.nopcommerce.user.BasePageUI;

public class BasePageObject extends BasePage {
    private WebDriver driver;

    public BasePageObject(WebDriver driver) {
        this.driver = driver;
    }

    public RegisterPageObject clickOnRegisterLink() {
        waitForElementClickable(driver, BasePageUI.REGISTER_LINK);
        clickOnElement(driver, BasePageUI.REGISTER_LINK);
        return PageGenerator.getRegisterPage(driver);
    }

    @Step("My Account link displayed in header")
    public boolean isMyAccountLinkDisplayed() {
        waitForElementVisible(driver, BasePageUI.MY_ACCOUNT_LINK);
        return isElementDisplayed(driver, BasePageUI.MY_ACCOUNT_LINK);
    }

    public CustomerInfoPageObject clickOnMyAccountLink() {
        waitForElementClickable(driver, BasePageUI.MY_ACCOUNT_LINK);
        clickOnElement(driver, BasePageUI.MY_ACCOUNT_LINK);
        return PageGenerator.getCustomerInfoPage(driver);
    }

    public LoginPageObject clickOnLoginLink() {
        waitForElementClickable(driver, BasePageUI.LOGIN_LINK);
        clickOnElement(driver, BasePageUI.LOGIN_LINK);
        return PageGenerator.getLoginPage(driver);
    }

    public HomePageObject clickOnLogoutLink() {
        if (isElementDisplayed(driver, BasePageUI.LOGOUT_LINK)) {
            waitForElementClickable(driver, BasePageUI.LOGOUT_LINK);
            clickOnElement(driver, BasePageUI.LOGOUT_LINK);
        }
        return PageGenerator.getHomePage(driver);
    }

    @Step("Click on {0} link in header")
    public BasePageObject clickOnHeaderLink(String pageName) {
        waitForElementClickable(driver, BasePageUI.DYNAMIC_HEADER_LINK_TEXT, pageName);
        clickOnElement(driver, BasePageUI.DYNAMIC_HEADER_LINK_TEXT, pageName);
        return switch (pageName) {
            case ("Register") -> PageGenerator.getRegisterPage(driver);
            case ("My account") -> PageGenerator.getCustomerInfoPage(driver);
            case ("Log in") -> PageGenerator.getLoginPage(driver);
            case ("Log out") -> PageGenerator.getHomePage(driver);
            default -> throw new RuntimeException("Page name is not valid !!!");
        };
    }

    // Page Element Component Pattern Object

    @Step("Input value to {0} textbox: {1}")
    public void sendKeysToTextboxByID(String textboxID, String keyToSend) {
        waitForElementVisible(driver, BasePageUI.DYNAMIC_TEXTBOX_ID, textboxID);
        clearElementText(driver, BasePageUI.DYNAMIC_TEXTBOX_ID, textboxID);
        sendKeysToElement(driver, BasePageUI.DYNAMIC_TEXTBOX_ID, keyToSend, textboxID);
    }

    @Step("Get value in {0} textbox")
    public String getValueInTextboxByID(String textboxID) {
        waitForElementVisible(driver, BasePageUI.DYNAMIC_TEXTBOX_ID, textboxID);
        return getAttributeValue(driver, BasePageUI.DYNAMIC_TEXTBOX_ID, "value", textboxID);
    }

    @Step("Click on {0} button")
    public void clickOnButtonByText(String buttonText) {
        waitForElementClickable(driver, BasePageUI.DYNAMIC_BUTTON_TEXT, buttonText);
        clickOnElement(driver, BasePageUI.DYNAMIC_BUTTON_TEXT, buttonText);
    }

    @Step("Select {0}")
    public void selectCheckboxOrRadioByID(String id) {
        waitForElementClickable(driver, BasePageUI.DYNAMIC_CHECKBOX_RADIO_ID, id);
        selectCheckboxOrRadio(driver, BasePageUI.DYNAMIC_CHECKBOX_RADIO_ID, id);
    }

    @Step("{0} selected")
    public boolean isCheckboxOrRadioByIDSelected(String id) {
        waitForElementSelected(driver, BasePageUI.DYNAMIC_CHECKBOX_RADIO_ID, id);
        return isElementSelected(driver, BasePageUI.DYNAMIC_CHECKBOX_RADIO_ID, id);
    }

    @Step("Click on {0} link in header")
    public void clickOnHeaderLinkByText(String headerLinkText) {
        waitForElementVisible(driver, BasePageUI.DYNAMIC_HEADER_LINK_TEXT, headerLinkText);
        clickOnElement(driver, BasePageUI.DYNAMIC_HEADER_LINK_TEXT, headerLinkText);
    }

    @Step("{0} link displayed in header")
    public boolean isHeaderLinkByTextDisplayed(String headerLinkText) {
        return isElementDisplayed(driver, BasePageUI.DYNAMIC_HEADER_LINK_TEXT, headerLinkText);
    }

}
