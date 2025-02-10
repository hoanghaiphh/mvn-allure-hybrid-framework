package testData;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

import java.net.URL;

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

    @Getter
    public static class Name {
        @JsonProperty("firstname")
        private String firstName;

        @JsonProperty("lastname")
        private String lastName;
    }

    @Getter
    public static class Login {
        @JsonProperty("email")
        private String email;

        @JsonProperty("password")
        private String password;
    }

    @Getter
    @JsonProperty("name")
    private Name name;

    @Getter
    @JsonProperty("login")
    private Login login;

    @Getter
    @JsonProperty("company")
    private String company;

    @Getter
    @JsonProperty("skills")
    private String[] skills;

    @Getter
    @Setter
    private String randomEmail;
}
