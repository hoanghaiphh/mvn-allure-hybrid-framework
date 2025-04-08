package pageFactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import testData.UserInfoPOJO;

public class RegisterPageFactory extends BasePageFactory {
    private WebDriver driver;

    public RegisterPageFactory(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//input[@id='gender-male']")
    @CacheLookup
    private WebElement genderMaleRadio;

    @FindBy(xpath = "//input[@id='FirstName']")
    @CacheLookup
    private WebElement firstNameTextbox;

    @FindBy(xpath = "//input[@id='LastName']")
    @CacheLookup
    private WebElement lastNameTextbox;

    @FindBy(xpath = "//input[@id='Email']")
    @CacheLookup
    private WebElement emailTextbox;

    @FindBy(xpath = "//input[@id='Company']")
    @CacheLookup
    private WebElement companyTextbox;

    @FindBy(xpath = "//input[@id='Password']")
    @CacheLookup
    private WebElement passwordTextbox;

    @FindBy(xpath = "//input[@id='ConfirmPassword']")
    @CacheLookup
    private WebElement confirmPasswordTextbox;

    @FindBy(xpath = "//button[@id='register-button']")
    @CacheLookup
    private WebElement registerButton;

    @FindBy(xpath = "//div[@class='result']")
    @CacheLookup
    private WebElement registerSuccessMsg;

    @FindBy(xpath = "//a[@class='ico-logout']")
    @CacheLookup
    private WebElement logoutLink;

    public void selectGenderMaleRadio() {
        waitForElementClickable(driver, genderMaleRadio);
        selectCheckboxOrRadio(genderMaleRadio);
    }

    public void sendKeyToFirstnameTextbox(String firstName) {
        waitForElementVisible(driver, firstNameTextbox).sendKeys(firstName);
    }

    public void sendKeyToLastnameTextbox(String lastName) {
        waitForElementVisible(driver, lastNameTextbox).sendKeys(lastName);
    }

    public void sendKeyToEmailTextbox(String emailAddress) {
        waitForElementVisible(driver, emailTextbox).sendKeys(emailAddress);
    }

    public void sendKeyToCompanyTextbox(String companyName) {
        waitForElementVisible(driver, companyTextbox).sendKeys(companyName);
    }

    public void sendKeyToPasswordTextbox(String password) {
        waitForElementVisible(driver, passwordTextbox).sendKeys(password);
    }

    public void sendKeyToConfirmPasswordTextbox(String password) {
        waitForElementVisible(driver, confirmPasswordTextbox).sendKeys(password);
    }

    public void clickOnRegisterButton() {
        waitForElementClickable(driver, registerButton).click();
    }

    public String getRegisterSuccessMessage() {
        return waitForElementVisible(driver, registerSuccessMsg).getText();
    }

    public HomePageFactory clickOnLogoutLink() {
        waitForElementClickable(driver, logoutLink).click();
        return new HomePageFactory(driver);
    }

    public void registerUser(UserInfoPOJO userInfo) {
        selectGenderMaleRadio();
        sendKeyToFirstnameTextbox(userInfo.getFirstName());
        sendKeyToLastnameTextbox(userInfo.getLastName());
        sendKeyToEmailTextbox(userInfo.getEmailAddress());
        sendKeyToCompanyTextbox(userInfo.getCompanyName());
        sendKeyToPasswordTextbox(userInfo.getPassword());
        sendKeyToConfirmPasswordTextbox(userInfo.getPassword());
        clickOnRegisterButton();
    }

}
