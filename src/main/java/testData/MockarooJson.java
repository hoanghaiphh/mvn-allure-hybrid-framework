package testData;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

import java.net.URL;
import java.util.List;

public class MockarooJson {

    public static MockarooJson getUserInfo() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            URL jsonFile = MockarooJson.class.getClassLoader().getResource("testData/mockaroo.json");
            return objectMapper.readValue(jsonFile, MockarooJson.class);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Getter
    public static class User {
        private String firstName;
        private String lastName;
        private String company;
    }

    @Getter
    private List<User> users;
}
