package pageFactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import testData.UserInfoPOJO;

public class LoginPageFactory extends BasePageFactory {
    private WebDriver driver;

    public LoginPageFactory(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//input[@id='Email']")
    private WebElement emailTextbox;

    @FindBy(xpath = "//input[@id='Password']")
    private WebElement passwordTextbox;

    @FindBy(xpath = "//button[@class='button-1 login-button']")
    private WebElement loginButton;

    public void sendKeyToEmailTextbox(String emailAddress) {
        waitForElementVisible(driver, emailTextbox).sendKeys(emailAddress);
    }

    public void sendKeyToPasswordTextbox(String password) {
        waitForElementVisible(driver, passwordTextbox).sendKeys(password);
    }

    public void clickOnLoginButton() {
        waitForElementClickable(driver, loginButton).click();
    }

    public HomePageFactory loginToSystem(UserInfoPOJO userInfo) {
        sendKeyToEmailTextbox(userInfo.getEmailAddress());
        sendKeyToPasswordTextbox(userInfo.getPassword());
        clickOnLoginButton();
        return new HomePageFactory(driver);
    }

    // Java Reflection
    public HomePageFactory loginJavaReflection(UserInfoPOJO userInfo) {
        handleElement("emailTextbox", "clear");
        handleElement("emailTextbox", "sendKeys", userInfo.getEmailAddress());
        handleElement("passwordTextbox", "clear");
        handleElement("passwordTextbox", "sendKeys", userInfo.getPassword());
        handleElement("loginButton", "click");
        return new HomePageFactory(driver);
    }

}
