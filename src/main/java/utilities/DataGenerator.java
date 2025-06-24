package utilities;

import net.datafaker.Faker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

public class DataGenerator {
    private Faker faker = new Faker();

    private DataGenerator() {
    }

    private DataGenerator(Faker localizedFaker) {
        faker = localizedFaker;
    }

    public static DataGenerator create() {
        return new DataGenerator();
    }

    public static DataGenerator create(String locale) {
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
                .password(12, 16, true, true, true);
    }

    public String getGender() {
        return faker.gender().binaryTypes();
    }

    public String getDriverLicense() {
        return (1000 + ThreadLocalRandom.current().nextInt(9000))
                + "-" + (1000 + ThreadLocalRandom.current().nextInt(9000))
                + "-" + (1000 + ThreadLocalRandom.current().nextInt(9000));
    }

    private static final List<String> MARITAL_STATUSES = Arrays.asList("Single", "Married", "Other");

    public String getMaritalStatus() {
        int randomIndex = ThreadLocalRandom.current().nextInt(MARITAL_STATUSES.size());
        return MARITAL_STATUSES.get(randomIndex);
    }

    private static final List<String> NATIONALITIES = Arrays.asList("British", "Australian", "American");

    public String getNationality() {
        int randomIndex = ThreadLocalRandom.current().nextInt(NATIONALITIES.size());
        return NATIONALITIES.get(randomIndex);
    }

    public String getDateWithinYearRange(int startYear, int endYear) {
        LocalDate startDate = LocalDate.of(startYear, 1, 1);
        LocalDate endDate = LocalDate.of(endYear, 12, 31);

        long startEpochDay = startDate.toEpochDay();
        long endEpochDay = endDate.toEpochDay();
        long randomDay = ThreadLocalRandom.current().nextLong(startEpochDay, endEpochDay + 1);

        LocalDate randomDate = LocalDate.ofEpochDay(randomDay);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return randomDate.format(formatter);
    }

}
