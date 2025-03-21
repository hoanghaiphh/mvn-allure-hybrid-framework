package browserCapabitilies;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.Test;

public class Headless {
    private WebDriver driver;

    @Test
    public void headlessFirefox() {
        FirefoxOptions fOptions = new FirefoxOptions();
        fOptions.addArguments("--headless");
        fOptions.addArguments("--width=1024");
        fOptions.addArguments("--height=768");

        driver = new FirefoxDriver(fOptions);

        driver.get("https://www.facebook.com/");
        driver.quit();
    }

    @Test
    public void headlessChrome() {
        ChromeOptions cOptions = new ChromeOptions();
        cOptions.addArguments("--headless=new");
        cOptions.addArguments("--window-size=1024,768");

        driver = new ChromeDriver(cOptions);

        driver.get("https://www.facebook.com/");
        driver.quit();
    }

    @Test
    public void headlessEdge() {
        EdgeOptions eOptions = new EdgeOptions();
        eOptions.addArguments("--headless=new");
        eOptions.addArguments("--window-size=1024,768");

        driver = new EdgeDriver(eOptions);

        driver.get("https://www.facebook.com/");
        driver.quit();
    }

}
