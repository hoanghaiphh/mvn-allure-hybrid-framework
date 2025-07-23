package testData;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

import java.io.InputStream;
import java.util.List;

public class UserInfoJson1 {

    public static UserInfoJson1 getUserInfo() {
        try (InputStream inputStream = UserInfoJson1.class.getClassLoader()
                .getResourceAsStream("testData/testDataUserInfo1.json")) {
            if (inputStream == null) throw new RuntimeException("File not found!");

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            return objectMapper.readValue(inputStream, UserInfoJson1.class);

        } catch (Exception e) {
            throw new RuntimeException(e);
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
