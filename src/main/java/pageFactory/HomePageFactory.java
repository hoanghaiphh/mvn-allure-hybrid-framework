package pageFactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePageFactory extends BasePageFactory {
    private WebDriver driver;

    public HomePageFactory(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//a[@class='ico-register']")
    private WebElement registerLink;

    @FindBy(xpath = "//a[@class='ico-account' and text()='My account']")
    private WebElement myAccountLink;

    @FindBy(xpath = "//a[@class='ico-login']")
    private WebElement loginLink;

    public RegisterPageFactory clickOnRegisterLink() {
        waitForElementClickable(driver, registerLink).click();
        return new RegisterPageFactory(driver);
    }

    public boolean isMyAccountLinkDisplayed() {
        return myAccountLink.isDisplayed();
    }

    public CustomerInfoPageFactory clickOnMyAccountLink() {
        waitForElementClickable(driver, myAccountLink).click();
        return new CustomerInfoPageFactory(driver);
    }

    public LoginPageFactory clickOnLoginLink() {
        waitForElementClickable(driver, loginLink).click();
        return new LoginPageFactory(driver);
    }

}
