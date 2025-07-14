package pageObjects.nopcommerceRefactored;

import commons.BasePage;
import org.openqa.selenium.WebDriver;

import java.lang.reflect.Constructor;

public class PageGenerator {

    public static <T extends BasePage> T getPage(Class<T> pageClass, WebDriver driver) {
        try {
            Constructor<T> constructor = pageClass.getConstructor(WebDriver.class);
            return constructor.newInstance(driver);
        } catch (Exception e) {
            throw new RuntimeException("Could not init Page Object class: " + pageClass.getSimpleName(), e);
        }
    }

}
