package pageObjects.techpanda;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import pageObjects.techpanda.myAccount.AccountInfoPO;

import static pageUIs.techpanda.BasePUI.ACCOUNT_MENU;
import static pageUIs.techpanda.MyAccountPUI.*;

public class MyAccountPO extends BasePO {
    private WebDriver driver;

    public MyAccountPO(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    @Step("Click on Account Information link at sidebar")
    public AccountInfoPO switchToAccountInfoPage() {
        clickOnElement(getClickableElement(driver, ACCOUNT_INFO_LINK));
        return PageGenerator.getAccountInfoPage(driver);
    }

    @Step("Open Account menu & Click on Logout link")
    public HomePO logoutFromSystem() {
        clickOnElement(getClickableElement(driver, ACCOUNT_MENU));
        clickOnElement(getClickableElement(driver, LOGOUT_LINK));
        return PageGenerator.getHomePage(driver);
    }

}
