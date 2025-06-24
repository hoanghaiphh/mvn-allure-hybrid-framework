package pageObjects.orangehrm;

import org.openqa.selenium.WebDriver;

import static pageUIs.orangehrm.PimPUI.*;

public class PimPO extends BasePO {
    private WebDriver driver;

    public PimPO(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    public void clickOnTopBarLink(String linkText) {
        clickOnElement(getClickableElement(driver, DYNAMIC_TOPBAR_NAV_LINK, linkText));
        waitForLoading();
    }

    public void clickOnTabsLink(String linkText) {
        clickOnElement(getClickableElement(driver, DYNAMIC_TABS_LINK, linkText));
        waitForLoading();
    }

    public void clickOnProfilePictureImage() {
        clickOnElement(getClickableElement(driver, PROFILE_PICTURE_IMAGE));
        waitForLoading();
    }

    public String getEmployeeName() {
        return getElementText(getVisibleElement(driver, EMPLOYEE_NAME));
    }

}
