package pageObjects.techpanda;

import commons.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;

import static pageUIs.techpanda.BasePUI.*;

public class BasePO extends BasePage {
    private WebDriver driver;

    public BasePO(WebDriver driver) {
        this.driver = driver;
    }

    public void sendRequestAnyway() {
        String driverInstance = driver.toString().toUpperCase();
        if (driverInstance.contains("CHROME") || driverInstance.contains("EDGE")) {
            clickOnElement(getClickableElement(driver, SEND_ANYWAY_BUTTON));
        } else if (driverInstance.contains("FIREFOX")) {
            acceptAlert(getPresentAlert(driver));
        }
    }

    @Step("Verify that Welcome Message on header should be: {0}")
    public boolean isHeaderWelcomeMsg(String expectedMsg) {
        waitForElementTextToBe(driver, HEADER_WELCOME_MSG, expectedMsg);
        return getElementText(getVisibleElement(driver, HEADER_WELCOME_MSG)).equals(expectedMsg);
    }

}
