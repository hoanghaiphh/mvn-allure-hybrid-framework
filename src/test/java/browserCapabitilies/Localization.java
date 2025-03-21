package browserCapabitilies;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.Test;

public class Localization {
    private WebDriver driver;

    @Test
    public void changeLocalizationFirefox() throws InterruptedException {
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.addPreference("intl.accept_languages", "vi-vn, vi");
        driver = new FirefoxDriver(firefoxOptions);

        driver.get("https://www.facebook.com/");
        Thread.sleep(1000);
        driver.get("https://wordpress.com/");
        Thread.sleep(1000);
        driver.quit();
    }

    @Test
    public void changeLocalizationChrome() throws InterruptedException {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--lang=vi");
        driver = new ChromeDriver(chromeOptions);

        driver.get("https://www.facebook.com/");
        Thread.sleep(1000);
        driver.get("https://wordpress.com/");
        Thread.sleep(1000);
        driver.quit();
    }

    @Test
    public void changeLocalizationEdge() throws InterruptedException {
        EdgeOptions edgeOptions = new EdgeOptions();
        edgeOptions.addArguments("--lang=vi");
        driver = new EdgeDriver(edgeOptions);

        driver.get("https://www.facebook.com/");
        Thread.sleep(1000);
        driver.get("https://wordpress.com/");
        Thread.sleep(1000);
        driver.quit();
    }

}
