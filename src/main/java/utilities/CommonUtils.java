package utilities;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.regex.Pattern;

public class CommonUtils {

    public static void sleepInSeconds(long timeInSecond) {
        try {
            Thread.sleep(timeInSecond * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getCurrentBrowserName(WebDriver driver) {
        String browserName = ((RemoteWebDriver) driver).getCapabilities().getBrowserName().toUpperCase();
        if (browserName.contains("EDGE")) browserName = "EDGE";
        return browserName;
    }

    public static long getRandomNumber(int min, int max) {
        Random rnd = new Random();
        return min + rnd.nextInt(max - min);
    }

    public static long getRandomNumberByDateTime() {
        return Calendar.getInstance().getTimeInMillis(); // Unix Epoch
    }

    public static String getRandomEmail(String prefix, WebDriver driver) {
        String timestamp = new SimpleDateFormat("_yyyyMMdd_HHmmss_").format(new Date());
        String browserName = getCurrentBrowserName(driver).toLowerCase();
        return removeDiacritics(prefix) + timestamp + browserName + "@gmail.com";
    }

    private static String removeDiacritics(String str) {
        str = str.replace("Đ", "D").replace("đ", "d");
        String normalized = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(normalized).replaceAll("").toLowerCase();
    }

    public static Path getFilePathFromClasspath(String folderName, String fileName) {
        try {
            URL url = CommonUtils.class.getClassLoader().getResource(folderName + "/" + fileName);
            if (url == null) {
                throw new RuntimeException("File not found in classpath: " + folderName + "/" + fileName);
            }
            return Paths.get(url.toURI());
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while accessing file: " + folderName + "/" + fileName, e);
        }
    }

    public static String getFileAbsolutePath(String folderName, String fileName) {
        Path path = getFilePathFromClasspath(folderName, fileName);
        return path.toAbsolutePath().toString();
    }

    public static File getFileFromClasspath(String folderName, String fileName) {
        Path path = getFilePathFromClasspath(folderName, fileName);
        return path.toFile();
    }

}
