package browserCapabitilies;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.testng.annotations.Test;

public class Incognito {
    private WebDriver driver;

    // TODO
    public void privateFirefox() throws InterruptedException {
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--private");

        FirefoxProfile profile = new FirefoxProfile();
        profile.setPreference("browser.privatebrowsing.autostart", true);
        options.setProfile(profile);

        driver = new FirefoxDriver(options);

        driver.get("https://www.facebook.com/");
        Thread.sleep(3000);
        driver.quit();
    }

    @Test
    public void incognitoChrome() throws InterruptedException {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--incognito");
        driver = new ChromeDriver(chromeOptions);

        driver.get("https://www.facebook.com/");
        Thread.sleep(3000);
        driver.quit();
    }

    @Test
    public void inPrivateEdge() throws InterruptedException {
        EdgeOptions edgeOptions = new EdgeOptions();
        edgeOptions.addArguments("--inprivate");
        driver = new EdgeDriver(edgeOptions);

        driver.get("https://www.facebook.com/");
        Thread.sleep(3000);
        driver.quit();
    }

}
