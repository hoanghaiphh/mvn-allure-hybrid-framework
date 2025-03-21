package browserCapabitilies;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Popup_Disable {
    private WebDriver driver;

    @Test
    public void notificationPopup() {
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.addPreference("dom.webnotifications.enabled", false);
        driver = new FirefoxDriver(firefoxOptions);

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--disable-notifications");
        driver = new ChromeDriver(chromeOptions);

        EdgeOptions edgeOptions = new EdgeOptions();
        edgeOptions.addArguments("--disable-notifications");
        driver = new EdgeDriver(edgeOptions);
    }

    @Test
    public void locationPopup() throws InterruptedException {
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.addPreference("geo.enabled", false);
        driver = new FirefoxDriver(firefoxOptions);
        driver.get("https://rode.com/en/support/where-to-buy");
        Thread.sleep(10000);
        driver.quit();

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--disable-geolocations");
        driver = new ChromeDriver(chromeOptions);
        driver.get("https://rode.com/en/support/where-to-buy");
        Thread.sleep(10000);
        driver.quit();

        EdgeOptions edgeOptions = new EdgeOptions();
        edgeOptions.addArguments("--disable-geolocations");
        driver = new EdgeDriver(edgeOptions);
        driver.get("https://rode.com/en/support/where-to-buy");
        Thread.sleep(10000);
        driver.quit();
    }

    @Test
    public void autoInfoBar() throws InterruptedException { // chrome/edge
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setExperimentalOption("useAutomationExtension", false);
        chromeOptions.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
        driver = new ChromeDriver(chromeOptions);
        driver.get("https://rode.com/en/support/where-to-buy");
        Thread.sleep(1000);
        driver.quit();

        EdgeOptions edgeOptions = new EdgeOptions();
        edgeOptions.setExperimentalOption("useAutomationExtension", false);
        edgeOptions.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
        driver = new EdgeDriver(edgeOptions);
        driver.get("https://rode.com/en/support/where-to-buy");
        Thread.sleep(1000);
        driver.quit();
    }

    public void savePasswordPopup() {
        ChromeOptions chromeOptions = new ChromeOptions();

        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);

        chromeOptions.setExperimentalOption("prefs", prefs);
        driver = new ChromeDriver(chromeOptions);
    }

    public void saveAddressPopup() {
        ChromeOptions chromeOptions = new ChromeOptions();

        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.default_content_setting_values.notifications", 2);
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        prefs.put("autofill.profile_enabled", false);

        chromeOptions.setExperimentalOption("prefs", prefs);
        chromeOptions.addArguments("--disable-notifications");
        driver = new ChromeDriver(chromeOptions);
    }

    public void saveCardPopup() {
        ChromeOptions chromeOptions = new ChromeOptions();

        Map<String, Object> prefs = new HashMap<>();
        prefs.put("autofill.credit_card_enabled", false);

        chromeOptions.setExperimentalOption("prefs", prefs);
        driver = new ChromeDriver(chromeOptions);
    }

}
