package pageObjects.orangehrm;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;

import static pageUIs.orangehrm.PimPUI.*;

public class PimPO extends BasePO {
    private WebDriver driver;

    public PimPO(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    @Step("Click on Top Bar link: {0}")
    public void clickOnTopBarLink(String linkText) {
        clickOnElement(getClickableElement(driver, DYNAMIC_TOPBAR_NAV_LINK, linkText));
        waitForLoading();
    }

    @Step("Click on Tabs link: {0}")
    public void clickOnTabsLink(String linkText) {
        clickOnElement(getClickableElement(driver, DYNAMIC_TABS_LINK, linkText));
        waitForLoading();
    }

    @Step("Click on Profile Picture image")
    public void clickOnProfilePictureImage() {
        clickOnElement(getClickableElement(driver, PROFILE_PICTURE_IMAGE));
        waitForLoading();
    }

    @Step("Get Employee Full Name")
    public String getEmployeeName() {
        return getElementText(getVisibleElement(driver, EMPLOYEE_NAME));
    }

}
