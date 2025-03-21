package browserCapabitilies;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.testng.annotations.Test;

public class Profile {
    private WebDriver driver;

    @Test
    public void profileChrome() throws InterruptedException {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--user-data-dir=C:\\Users\\HAIPH\\AppData\\Local\\Google\\Chrome\\User Data\\");
        options.addArguments("--profile-directory=Default");
        driver = new ChromeDriver(options);

        driver.get("https://demo.nopcommerce.com/"); // pass Cloud Flare verification
        Thread.sleep(5000);
        driver.quit();
    }

    @Test
    public void profileEdge() throws InterruptedException {
        EdgeOptions options = new EdgeOptions();
        options.addArguments("--user-data-dir=C:\\Users\\HAIPH\\AppData\\Local\\Microsoft\\Edge\\User Data\\");
        options.addArguments("--profile-directory=Profile 3");
        driver = new EdgeDriver(options);

        driver.get("https://demo.nopcommerce.com/"); // pass Cloud Flare verification
        Thread.sleep(5000);
        driver.quit();
    }
}
