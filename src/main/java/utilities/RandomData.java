package utilities;

import commons.BasePage;
import org.openqa.selenium.WebDriver;

import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.regex.Pattern;

public class RandomData {

    public static long getRandomNumber(int min, int max) {
        Random rnd = new Random();
        return min + rnd.nextInt(max - min);
    }

    public static long getRandomNumberByDateTime() {
        return Calendar.getInstance().getTimeInMillis(); // Unix Epoch
    }

    public static String getRandomEmail(String prefix, WebDriver driver) {
        String timestamp = new SimpleDateFormat("_yyyyMMdd_HHmmss_").format(new Date());
        String browserName = BasePage.getCurrentBrowserName(driver).toLowerCase();
        return removeDiacritics(prefix) + timestamp + browserName + "@gmail.com";
    }

    private static String removeDiacritics(String str) {
        str = str.replace("Đ", "D").replace("đ", "d");
        String normalized = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(normalized).replaceAll("").toLowerCase();
    }

}
