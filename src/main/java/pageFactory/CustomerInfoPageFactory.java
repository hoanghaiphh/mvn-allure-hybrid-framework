package pageFactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CustomerInfoPageFactory extends BasePageFactory {

    public CustomerInfoPageFactory(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//input[@id='gender-male']")
    private WebElement genderMaleRadio;

    @FindBy(xpath = "//input[@id='FirstName']")
    private WebElement firstNameTextbox;

    @FindBy(xpath = "//input[@id='LastName']")
    private WebElement lastNameTextbox;

    @FindBy(xpath = "//input[@id='Company']")
    private WebElement companyTextbox;

    public boolean isGenderMaleSelected() {
        return isElementSelected(genderMaleRadio);
    }

    public String getValueInFirstnameTextbox() {
        return waitForElementVisible(firstNameTextbox).getDomAttribute("value");
    }

    public String getValueInLastnameTextbox() {
        return waitForElementVisible(lastNameTextbox).getDomAttribute("value");
    }

    public String getValueInCompanyTextbox() {
        return waitForElementVisible(companyTextbox).getDomAttribute("value");
    }

}
