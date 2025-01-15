package commons;

import net.datafaker.Faker;

import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Pattern;

public class DataGenerator {
    private Faker faker = new Faker();

    private DataGenerator() {
    }

    public static DataGenerator getData() {
        return new DataGenerator();
    }

    private DataGenerator(Faker localizedFaker) {
        faker = localizedFaker;
    }

    public static DataGenerator getData(String locale) {
        Faker localizedFaker = new Faker(new Locale(locale));
        return new DataGenerator(localizedFaker);
    }

    public String getFirstname() {
        return faker.name().firstName();
    }

    public String getLastname() {
        return faker.name().lastName();
    }

    public String getCompanyName() {
        return faker.company().name();
    }

    public String getPassword() {
        return faker.internet()
                .password(8, 16, true, true, true);
    }

    public static long getRandomNumber(int min, int max) {
        Random rnd = new Random();
        return min + rnd.nextInt(max - min);
    }

    public static long getRandomNumberByDateTime() {
        return Calendar.getInstance().getTimeInMillis(); // Unix Epoch
    }

    public static String getRandomEmailByTimestamp(String prefix) {
        SimpleDateFormat sdf = new SimpleDateFormat("_yyyyMMdd_HHmmssSSS");
        String timestamp = sdf.format(new Date());
        return removeDiacritics(prefix) + timestamp + "@gmail.com";
    }

    private static String removeDiacritics(String str) {
        str = str.replace("Đ", "D").replace("đ", "d");
        String normalized = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(normalized).replaceAll("").toLowerCase();
    }

}
