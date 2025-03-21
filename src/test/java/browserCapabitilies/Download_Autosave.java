package browserCapabitilies;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.Test;

import java.io.File;
import java.util.HashMap;

public class Download_Autosave {
    private WebDriver driver;
    private String downloadPath = System.getProperty("user.dir") + File.separator + "downloadFiles";

    @Test
    public void firefoxBrowser() {
        FirefoxOptions options = new FirefoxOptions();
        options.addPreference("browser.download.folderList", 2);
        options.addPreference("browser.download.dir", downloadPath);
        options.addPreference("browser.download.useDownloadDir", true);
        options.addPreference("browser.helperApps.neverAsk.saveToDisk", "multipart/x-zip, application/zip," +
                "application/x-zip-compressed, application/x-compressed, application/msword, application/csv," +
                "text/csv, image/png, image/jpeg, application/pdf, text/html, text/plain, application/excel," +
                "application/vnd.ms-excel, application/x-excel, application/x-msexcel, application/octet-stream");
        options.addPreference("pdfjs.disabled", true);
        driver = new FirefoxDriver(options);
    }

    @Test
    public void chromeBrowser() {
        ChromeOptions options = new ChromeOptions();
        HashMap<String, Object> chromePrefs = new HashMap<>();
        chromePrefs.put("profile.default_content_settings.popups", 0);
        chromePrefs.put("download.default_directory", downloadPath);
        options.setExperimentalOption("prefs", chromePrefs);
        driver = new ChromeDriver(options);
    }

}
