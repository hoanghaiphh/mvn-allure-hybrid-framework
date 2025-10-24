package testData;

import lombok.Getter;
import tools.jackson.databind.DeserializationFeature;
import tools.jackson.databind.json.JsonMapper;

import java.io.InputStream;
import java.util.List;

public class UserInfoJson2 {

    public static List<User> getUserInfo() {
        try (InputStream inputStream = UserInfoJson2.class.getClassLoader()
                .getResourceAsStream("testData/testDataUserInfo2.json")) {
            if (inputStream == null) throw new RuntimeException("File not found!");

            JsonMapper jsonMapper = JsonMapper.builder()
                    .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                    .build();

            return jsonMapper.readValue(inputStream,
                    jsonMapper.getTypeFactory().constructCollectionType(List.class, User.class));

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
}
