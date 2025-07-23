package pageObjects.techpanda;

import commons.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

import static pageUIs.techpanda.BasePUI.*;

public class BasePO extends BasePage {

    public BasePO(WebDriver driver) {
        super(driver);
    }

    public void sendRequestAnyway() {
        String driverInstance = driver.toString().toUpperCase();
        if (driverInstance.contains("CHROME") || driverInstance.contains("EDGE")) {
            clickOnElement(getClickableElement(SEND_ANYWAY_BUTTON));
        } else if (driverInstance.contains("FIREFOX")) {
            acceptAlert(getPresentAlert());
        } else if (driverInstance.contains("SAFARI")) {
            pressKey(Keys.TAB);
            pressKey(Keys.ENTER);
        }
    }

    @Step("Verify that Welcome Message on header should be: {0}")
    public boolean headerWelcomeMsgContains(String expectedMsg) {
        String driverInstance = driver.toString().toUpperCase();
        if (!driverInstance.contains("SAFARI")) expectedMsg = expectedMsg.toUpperCase();
        waitForElementTextToBe(HEADER_WELCOME_MSG, expectedMsg);
        return getElementText(getVisibleElement(HEADER_WELCOME_MSG)).contains(expectedMsg);
    }

}
