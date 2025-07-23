package pageObjects.techpanda;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;

import static pageUIs.techpanda.BasePUI.ACCOUNT_MENU;
import static pageUIs.techpanda.MyAccountPUI.*;

public class MyAccountPO extends BasePO {

    public MyAccountPO(WebDriver driver) {
        super(driver);
    }

    @Step("Click on Account Information link at sidebar")
    public void switchToAccountInfoPage() {
        clickOnElement(getClickableElement(ACCOUNT_INFO_LINK));
    }

    @Step("Open Account menu & Click on Logout link")
    public void logoutFromSystem() {
        clickOnElement(getClickableElement(ACCOUNT_MENU));
        clickOnElement(getClickableElement(LOGOUT_LINK));
    }

}
