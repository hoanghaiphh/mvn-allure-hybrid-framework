package testData;

import lombok.Getter;
import tools.jackson.databind.DeserializationFeature;
import tools.jackson.databind.json.JsonMapper;

import java.io.InputStream;
import java.util.List;

public class UserInfoJson1 {

    public static UserInfoJson1 getUserInfo() {
        try (InputStream inputStream = UserInfoJson1.class.getClassLoader()
                .getResourceAsStream("testData/testDataUserInfo1.json")) {
            if (inputStream == null) throw new RuntimeException("File not found!");

            JsonMapper jsonMapper = JsonMapper.builder()
                    .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                    .build();

            return jsonMapper.readValue(inputStream, UserInfoJson1.class);

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
