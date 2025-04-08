package pageFactory;

import commons.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePageFactory extends BasePage {

    public WebElement waitForElementVisible(WebDriver driver, WebElement element) {
        return new WebDriverWait(driver, Duration.ofSeconds(15)).until(ExpectedConditions.visibilityOf(element));
    }

    public WebElement waitForElementClickable(WebDriver driver, WebElement element) {
        return new WebDriverWait(driver, Duration.ofSeconds(15)).until(ExpectedConditions.elementToBeClickable(element));
    }

}
