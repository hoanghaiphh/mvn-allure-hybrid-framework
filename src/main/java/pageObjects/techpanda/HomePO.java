package pageObjects.techpanda;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;

import static pageUIs.techpanda.BasePUI.ACCOUNT_MENU;
import static pageUIs.techpanda.HomePUI.*;

public class HomePO extends BasePO {

    public HomePO(WebDriver driver) {
        super(driver);
    }

    @Step("Open Account menu & Click on Register link")
    public void openRegisterPage() {
        clickOnElement(getClickableElement(ACCOUNT_MENU));
        clickOnElement(getClickableElement(REGISTER_LINK));
    }

    @Step("Open Account menu & Click on Login link")
    public void openLoginPage() {
        clickOnElement(getClickableElement(ACCOUNT_MENU));
        clickOnElement(getClickableElement(LOGIN_LINK));
    }

}
