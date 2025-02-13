package utilities;

import net.datafaker.Faker;

import java.util.Locale;

public class FakerConfig {
    private Faker faker = new Faker();

    private FakerConfig() {
    }

    private FakerConfig(Faker localizedFaker) {
        faker = localizedFaker;
    }

    public static FakerConfig getData() {
        return new FakerConfig();
    }

    public static FakerConfig getData(String locale) {
        Faker localizedFaker = new Faker(new Locale(locale));
        return new FakerConfig(localizedFaker);
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

}
