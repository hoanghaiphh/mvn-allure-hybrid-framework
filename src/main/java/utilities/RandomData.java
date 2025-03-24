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

    public static RandomData getRandomData() {
        return new RandomData();
    }

    public long getRandomNumber(int min, int max) {
        Random rnd = new Random();
        return min + rnd.nextInt(max - min);
    }

    public long getRandomNumberByDateTime() {
        return Calendar.getInstance().getTimeInMillis(); // Unix Epoch
    }

    public String getRandomEmailByTimestamp(String prefix, WebDriver driver) {
        SimpleDateFormat sdf = new SimpleDateFormat("_yyyyMMdd_HHmmss_");
        String timestamp = sdf.format(new Date());
        return removeDiacritics(prefix) + timestamp
                + BasePage.getCurrentBrowserName(driver).toLowerCase() + "@gmail.com";
    }

    private String removeDiacritics(String str) {
        str = str.replace("Đ", "D").replace("đ", "d");
        String normalized = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(normalized).replaceAll("").toLowerCase();
    }

}
