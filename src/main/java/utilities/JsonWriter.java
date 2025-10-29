package utilities;

import tools.jackson.databind.DeserializationFeature;
import tools.jackson.databind.SerializationFeature;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.type.CollectionType;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class JsonWriter {

    public record LoginInfo(String email, String password) {
    }

    private static final File FILE = new File("src/main/resources/testData/loginInfo.json");

    private static final JsonMapper JSON_MAPPER = JsonMapper.builder()
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            .enable(SerializationFeature.INDENT_OUTPUT)
            .build();

    public static synchronized void saveLoginInfo(String email, String password) {
        List<LoginInfo> loginInfoList = new ArrayList<>();

        try {
            CollectionType type = JSON_MAPPER.getTypeFactory().constructCollectionType(List.class, LoginInfo.class);
            if (FILE.exists() && FILE.length() > 0) loginInfoList = JSON_MAPPER.readValue(FILE, type);

            loginInfoList.add(new LoginInfo(email, password));
            JSON_MAPPER.writeValue(FILE, loginInfoList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<LoginInfo> readLoginInfo() {
        try {
            if (!FILE.exists() || FILE.length() == 0) return new ArrayList<>();

            CollectionType type = JSON_MAPPER.getTypeFactory().constructCollectionType(List.class, LoginInfo.class);
            return JSON_MAPPER.readValue(FILE, type);

        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public static void main(String[] args) {
        saveLoginInfo("json.writer.111@example.com", "pass12345");
        saveLoginInfo("json.writer.222@example.com", "54321pass");
        System.out.println(readLoginInfo());
    }

}
