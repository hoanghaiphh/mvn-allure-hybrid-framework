package pageObjects.orangehrm.PIM.EmployeeList;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pageObjects.orangehrm.PimPO;

import static pageUIs.orangehrm.PIM.EmployeeList.ProfilePicturePUI.*;
import static pageUIs.orangehrm.PimPUI.*;

public class ProfilePicturePO extends PimPO {
    private WebDriver driver;

    public ProfilePicturePO(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    public Dimension getNaturalDimensionOfProfilePicture() {
        WebElement element = getVisibleElement(driver, PROFILE_PICTURE_IMAGE);
        int naturalWidth = Integer.parseInt(getElementPropertyValue(element, "naturalWidth"));
        int naturalHeight = Integer.parseInt(getElementPropertyValue(element, "naturalHeight"));
        return new Dimension(naturalWidth, naturalHeight);
    }

    public void uploadProfilePicture(String profilePicturePath) {
        sendKeysToElement(getElement(driver, PROFILE_PICTURE_UPLOAD), profilePicturePath);
    }

    public void clickOnChangeProfilePictureSaveButton() {
        clickOnElement(getClickableElement(driver, CHANGE_PROFILE_PICTURE_SAVE_BUTTON));
    }

}
