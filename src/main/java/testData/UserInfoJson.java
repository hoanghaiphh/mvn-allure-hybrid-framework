package testData;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

import java.io.InputStream;
import java.util.List;

public class UserInfoJson {

    public static UserInfoJson getUserInfo() {
        try (InputStream inputStream = UserInfoJson.class.getClassLoader()
                .getResourceAsStream("testData/testDataUserInfo.json")) {
            if (inputStream == null) throw new RuntimeException("File not found!");

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            return objectMapper.readValue(inputStream, UserInfoJson.class);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // if json key <> variable --> add annotation @JsonProperty(jsonKey) to map value, else --> not necessary
    @Getter
    public static class Name {
        private String firstName;
        private String lastName;
    }

    @Getter
    public static class Login {
        private String email;
        private String password;
    }

    @Getter
    private Name name;

    @Getter
    private Login login;

    @Getter
    private String company;

    @Getter
    private String[] languages;

    @Getter
    private List<String> buildTools;

    @Getter
    @Setter
    private String randomEmail;
}
