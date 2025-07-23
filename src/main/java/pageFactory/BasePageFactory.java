package pageFactory;

import commons.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.time.Duration;

public class BasePageFactory extends BasePage {

    protected BasePageFactory(WebDriver driver) {
        super(driver);
    }

    protected <T extends BasePage> T getPage(Class<T> pageClass) {
        try {
            Constructor<T> constructor = pageClass.getConstructor(WebDriver.class);
            return constructor.newInstance(driver);
        } catch (Exception e) {
            throw new RuntimeException("Could not init Page Object class: " + pageClass.getSimpleName(), e);
        }
    }

    protected WebElement waitForElementVisible(WebElement element) {
        return new WebDriverWait(driver, Duration.ofSeconds(15)).until(ExpectedConditions.visibilityOf(element));
    }

    protected WebElement waitForElementClickable(WebElement element) {
        return new WebDriverWait(driver, Duration.ofSeconds(15)).until(ExpectedConditions.elementToBeClickable(element));
    }

    // Java Reflection
    protected void handleElement(String elementName, String action, String... value) {
        WebElement element;
        try {
            Field field = this.getClass().getDeclaredField(elementName);
            field.setAccessible(true);
            element = (WebElement) field.get(this);
        } catch (Exception e) {
            throw new RuntimeException("Error handling element: " + elementName, e);
        }

        switch (action) {
            case "click" -> element.click();
            case "clear" -> element.clear();
            case "sendKeys" -> {
                if (value.length == 1) {
                    element.sendKeys(value[0]);
                } else {
                    throw new IllegalArgumentException("SendKeys Action need 1 Argument, but found " + value.length);
                }
            }
            default -> throw new IllegalArgumentException("Action is not valid !!!");
        }
    }

}
