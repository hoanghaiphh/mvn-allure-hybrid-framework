package pageObjects.orangehrm;

import commons.BasePage;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static pageUIs.orangehrm.BasePUI.*;

public class BasePO extends BasePage {
    private WebDriver driver;

    public BasePO(WebDriver driver) {
        this.driver = driver;
    }

    public void waitForLoading() {
        if (isElementDisplayed(driver, LOADING_SPINNER)) {
            waitForListElementsInvisible(driver, LOADING_SPINNER);
        }
    }

    public void clearThenSendKeysToTextbox(WebElement textbox, String keysToSend) {
        sendKeysToElement(textbox, Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE));
        sendKeysToElement(textbox, keysToSend);
    }

    public PimPO clickOnSidePanelLink(String linkText) {
        clickOnElement(getClickableElement(driver, DYNAMIC_SIDEPANEL_LINK, linkText));
        waitForLoading();
        return PageGenerator.getPIMPage(driver);
    }

    public String getToastMessage() {
        return getElementText(getVisibleElement(driver, TOAST_MESSAGE));
    }

}
