package testData;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

import java.net.URL;
import java.util.List;

public class UserInfoJson {

    public static UserInfoJson getUserInfo() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            URL jsonFile = UserInfoJson.class.getClassLoader().getResource("testDataUserInfo.json");
            return objectMapper.readValue(jsonFile, UserInfoJson.class);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
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
