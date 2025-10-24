package testData;

import lombok.Getter;
import lombok.Setter;
import tools.jackson.databind.DeserializationFeature;
import tools.jackson.databind.json.JsonMapper;

import java.io.InputStream;
import java.util.List;

public class UserInfoJson {

    public static UserInfoJson getUserInfo() {
        try (InputStream inputStream = UserInfoJson.class.getClassLoader()
                .getResourceAsStream("testData/testDataUserInfo.json")) {
            if (inputStream == null) throw new RuntimeException("File not found!");

            JsonMapper jsonMapper = JsonMapper.builder()
                    .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                    .build();

            return jsonMapper.readValue(inputStream, UserInfoJson.class);

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
