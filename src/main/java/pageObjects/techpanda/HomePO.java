package pageObjects.techpanda;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;

import static pageUIs.techpanda.BasePUI.ACCOUNT_MENU;
import static pageUIs.techpanda.HomePUI.*;

public class HomePO extends BasePO {
    private WebDriver driver;

    public HomePO(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    @Step("Open Account menu & Click on Register link")
    public RegisterPO openRegisterPage() {
        clickOnElement(getClickableElement(driver, ACCOUNT_MENU));
        clickOnElement(getClickableElement(driver, REGISTER_LINK));
        return PageGenerator.getRegisterPage(driver);
    }

    @Step("Open Account menu & Click on Login link")
    public LoginPO openLoginPage() {
        clickOnElement(getClickableElement(driver, ACCOUNT_MENU));
        clickOnElement(getClickableElement(driver, LOGIN_LINK));
        return PageGenerator.getLoginPage(driver);
    }

}
