package pageObjects.nopcommerce;

import commons.BasePage;
import org.openqa.selenium.WebElement;
import pageObjects.nopcommerce.myAccount.CustomerInfoPO;
import io.qameta.allure.Step;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

import java.util.Set;

import static pageUIs.nopcommerce.BasePageUI.*;

public class BasePageObject extends BasePage {
    private WebDriver driver;

    public BasePageObject(WebDriver driver) {
        this.driver = driver;
    }

    public RegisterPO clickOnRegisterLink() {
        clickOnElement(getClickableElement(driver, REGISTER_LINK));
        return PageGenerator.getRegisterPage(driver);
    }

    @Step("Verify that My Account link was displayed in header")
    public boolean isMyAccountLinkDisplayed() {
        return isElementDisplayed(driver, MY_ACCOUNT_LINK);
    }

    public CustomerInfoPO clickOnMyAccountLink() {
        clickOnElement(getClickableElement(driver, MY_ACCOUNT_LINK));
        return PageGenerator.getCustomerInfoPage(driver);
    }

    public LoginPO clickOnLoginLink() {
        clickOnElement(getClickableElement(driver, LOGIN_LINK));
        return PageGenerator.getLoginPage(driver);
    }

    public HomePO clickOnLogoutLink() {
        clickOnElement(getClickableElement(driver, LOGOUT_LINK));
        return PageGenerator.getHomePage(driver);
    }

    @Step("Click on {0} link in header")
    public BasePageObject clickOnHeaderLink(String pageName) {
        clickOnElement(getClickableElement(driver, DYNAMIC_HEADER_LINK_TEXT, pageName));
        return switch (pageName) {
            case ("Register") -> PageGenerator.getRegisterPage(driver);
            case ("My account") -> PageGenerator.getCustomerInfoPage(driver);
            case ("Log in") -> PageGenerator.getLoginPage(driver);
            case ("Log out") -> PageGenerator.getHomePage(driver);
            default -> throw new RuntimeException("Page name is not valid !!!");
        };
    }

    // Pattern Object

    @Step("Input value to {0} textbox: {1}")
    public void sendKeysToTextboxByID(String textboxID, String keyToSend) {
        WebElement element = getVisibleElement(driver, DYNAMIC_TEXTBOX_ID, textboxID);
        clearElementText(element);
        sendKeysToElement(element, keyToSend);
    }

    @Step("Get value in {0} textbox")
    public String getValueInTextboxByID(String textboxID) {
        return getElementAttributeValue(getVisibleElement(driver, DYNAMIC_TEXTBOX_ID, textboxID), "value");
    }

    @Step("Click on {0} button")
    public void clickOnButtonByText(String buttonText) {
        clickOnElement(getClickableElement(driver, DYNAMIC_BUTTON_TEXT, buttonText));
    }

    @Step("Select {0}")
    public void selectCheckboxOrRadioByID(String id) {
        selectCheckboxOrRadio(getClickableElement(driver, DYNAMIC_CHECKBOX_RADIO_ID, id));
    }

    @Step("{0} selected")
    public boolean isCheckboxOrRadioByIDSelected(String id) {
        return isElementSelected(getVisibleElement(driver, DYNAMIC_CHECKBOX_RADIO_ID, id));
    }

    @Step("Click on {0} link in header")
    public void clickOnHeaderLinkByText(String headerLinkText) {
        clickOnElement(getClickableElement(driver, DYNAMIC_HEADER_LINK_TEXT, headerLinkText));
    }

    @Step("{0} link displayed in header")
    public boolean isHeaderLinkByTextDisplayed(String headerLinkText) {
        return isElementDisplayed(driver, DYNAMIC_HEADER_LINK_TEXT, headerLinkText);
    }

    public void loginByCookies(Set<Cookie> cookies) {
        setCookies(driver, cookies);
        refreshCurrentPage(driver);
    }

}
