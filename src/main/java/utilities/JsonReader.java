package utilities;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class JsonReader {

    private final String fileName;
    private final String jsonContent;

    private static final Map<String, JsonReader> instances = new ConcurrentHashMap<>();

    public static JsonReader getInstance(String fileName) {
        return instances.computeIfAbsent(fileName, JsonReader::new);
    }

    private JsonReader(String fileName) {
        this.fileName = fileName;

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName)) {
            if (inputStream == null) {
                throw new RuntimeException("File not found in classpath: " + fileName);
            } else {
                this.jsonContent = new String(inputStream.readAllBytes());
                System.out.printf("\nSuccessfully loaded JSON file %s\n", fileName);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error reading JSON file: " + fileName, e);
        }
    }

    public Object getValueOf(String jsonPath) {
        try {
            return JsonPath.read(this.jsonContent, jsonPath);
        } catch (PathNotFoundException e) {
            System.err.printf("JSON Path '%s' not found in file %s", jsonPath, fileName);
            return null;
        } catch (Exception e) {
            throw new RuntimeException("Error evaluating JSON Path '" + jsonPath + "' in file " + fileName, e);
        }
    }

    // test
    public static void main(String[] args) {
        JsonReader jsonReader = JsonReader.getInstance("testData/testDataUserInfo.json");
        Object firstName = jsonReader.getValueOf("$.name.firstName");
        Object company = jsonReader.getValueOf("$.company");
        System.out.printf("""
                        First Name: %s (Type: %s)
                        Company: %s (Type: %s)
                        """,
                firstName, (firstName != null ? firstName.getClass().getSimpleName() : "null"),
                company, (company != null ? company.getClass().getSimpleName() : "null"));

        jsonReader = JsonReader.getInstance("testData/testDataUserInfo.json");
        System.out.println("\n(should not reload file)");
        Object email = jsonReader.getValueOf("$.login.email");
        Object languages = jsonReader.getValueOf("$.languages");
        Object name = jsonReader.getValueOf("$.name");
        System.out.printf("""
                        Email: %s (Type: %s)
                        Languages: %s (Type: %s)
                        Name: %s (Type: %s)
                        """,
                email, (email != null ? email.getClass().getSimpleName() : "null"),
                languages, (languages != null ? languages.getClass().getSimpleName() : "null"),
                name, (name != null ? name.getClass().getSimpleName() : "null"));

        jsonReader = JsonReader.getInstance("testData/testDataUserInfo1.json");
        Object usersObj = jsonReader.getValueOf("$.users");
        List<Map<String, Object>> usersList;
        if (usersObj instanceof List) {
            usersList = (List<Map<String, Object>>) usersObj;
            usersList.forEach(System.out::println);
        }

        jsonReader = JsonReader.getInstance("testData/testDataUserInfo2.json");
        usersObj = jsonReader.getValueOf("$");
        if (usersObj instanceof List) {
            usersList = (List<Map<String, Object>>) usersObj;
            usersList.forEach(System.out::println);
        }

        System.out.printf("\nNon existent key: %s\n", jsonReader.getValueOf("$.nonExistentKey"));
    }
}