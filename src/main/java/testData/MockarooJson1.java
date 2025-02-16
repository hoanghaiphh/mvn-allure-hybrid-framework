package testData;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

import java.util.List;

public class MockarooJson1 {

    public static List<User> getUserInfo() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            return objectMapper.readValue(
                    MockarooJson1.class.getClassLoader().getResource("testData/mockaroo1.json"),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, User.class));

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
}
