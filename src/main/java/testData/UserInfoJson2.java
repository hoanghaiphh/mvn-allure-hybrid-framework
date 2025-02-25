package testData;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

import java.io.InputStream;
import java.util.List;

public class UserInfoJson2 {

    public static List<User> getUserInfo() {
        try (InputStream inputStream = UserInfoJson2.class.getClassLoader()
                .getResourceAsStream("testData/testDataUserInfo2.json")) {
            if (inputStream == null) throw new RuntimeException("Không tìm thấy file!");

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            return objectMapper.readValue(inputStream,
                    objectMapper.getTypeFactory().constructCollectionType(List.class, User.class));

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
