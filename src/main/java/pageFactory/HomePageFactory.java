package pageFactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePageFactory extends BasePageFactory {

    public HomePageFactory(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//a[@class='ico-register']")
    private WebElement registerLink;

    @FindBy(xpath = "//a[@class='ico-account' and text()='My account']")
    private WebElement myAccountLink;

    @FindBy(xpath = "//a[@class='ico-login']")
    private WebElement loginLink;

    public RegisterPageFactory clickOnRegisterLink() {
        waitForElementClickable(registerLink).click();
        return getPage(RegisterPageFactory.class);
    }

    public boolean isMyAccountLinkDisplayed() {
        return myAccountLink.isDisplayed();
    }

    public CustomerInfoPageFactory clickOnMyAccountLink() {
        waitForElementClickable(myAccountLink).click();
        return getPage(CustomerInfoPageFactory.class);
    }

    public LoginPageFactory clickOnLoginLink() {
        waitForElementClickable(loginLink).click();
        return getPage(LoginPageFactory.class);
    }

}
