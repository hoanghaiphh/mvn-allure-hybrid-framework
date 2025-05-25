package browserCapabitilies;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import utilities.FileUtils;

import java.time.Duration;

public class Extension {
    private WebDriver driver;
    private WebDriverWait explicitlyWait;

    // Selenium version 3.x
    public void firefoxExtensionsOld() {
        FirefoxProfile firefoxProfile = new FirefoxProfile();
        firefoxProfile.addExtension(
                FileUtils.getFileFromClasspath("browserExtensions", "wappalyzer-firefox.xpi"));
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.setProfile(firefoxProfile);
        driver = new FirefoxDriver(firefoxOptions);

        driver.get("https://www.facebook.com/");
        driver.quit();
    }

    @Test
    public void firefoxExtensions() throws InterruptedException {
        driver = new FirefoxDriver();
        FirefoxDriver fDriver = (FirefoxDriver) driver;
        fDriver.installExtension(
                FileUtils.getFilePathFromClasspath("browserExtensions", "wappalyzer-firefox.xpi"));
        driver = fDriver;

        driver.get("https://www.facebook.com/");

        String parentWindow = driver.getWindowHandle();
        explicitlyWait = new WebDriverWait(driver, Duration.ofSeconds(30));
        explicitlyWait.until(ExpectedConditions.numberOfWindowsToBe(2));
        driver.switchTo().window(parentWindow);

        Thread.sleep(5000);
        driver.quit();
    }

    @Test
    public void chromeExtensions() throws InterruptedException {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addExtensions(
                FileUtils.getFileFromClasspath("browserExtensions", "wappalyzer-chrome.crx"));
        driver = new ChromeDriver(chromeOptions);

        driver.get("https://www.facebook.com/");

        String parentWindow = driver.getWindowHandle();
        explicitlyWait = new WebDriverWait(driver, Duration.ofSeconds(30));
        explicitlyWait.until(ExpectedConditions.numberOfWindowsToBe(2));
        driver.switchTo().window(parentWindow);

        Thread.sleep(5000);
        driver.quit();
    }

    @Test
    public void edgeExtensions() throws InterruptedException {
        EdgeOptions edgeOptions = new EdgeOptions();
        edgeOptions.addExtensions(
                FileUtils.getFileFromClasspath("browserExtensions", "wappalyzer-chrome.crx"));
        driver = new EdgeDriver(edgeOptions);

        driver.get("https://www.facebook.com/");

        String parentWindow = driver.getWindowHandle();
        explicitlyWait = new WebDriverWait(driver, Duration.ofSeconds(30));
        explicitlyWait.until(ExpectedConditions.numberOfWindowsToBe(2));
        driver.switchTo().window(parentWindow);

        Thread.sleep(5000);
        driver.quit();
    }

}
