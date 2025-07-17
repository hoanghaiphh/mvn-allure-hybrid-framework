package utilities;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class PropertiesConfig {

    private final Properties properties;
    private static volatile PropertiesConfig instance;

    private PropertiesConfig(String environment) {
        String str = "environments/env-%s.properties";
        String propertyFile = String.format(str, environment);

        try (InputStream input = getClass().getClassLoader().getResourceAsStream(propertyFile)) {
            if (input == null) throw new RuntimeException("Configuration properties not found at " + propertyFile);

            try (InputStreamReader reader = new InputStreamReader(input, StandardCharsets.UTF_8)) {
                properties = new Properties();
                properties.load(reader);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error while loading properties from " + propertyFile, e);
        }
    }

    public static PropertiesConfig getEnvironmentProperties() {
        if (instance == null) {
            synchronized (PropertiesConfig.class) {
                if (instance == null) {
                    String systemEnv = System.getProperty("env", "local").toLowerCase();
                    instance = new PropertiesConfig(systemEnv);
                }
            }
        }
        return instance;
    }

    public String getPropertyValue(String key) {
        String value = properties.getProperty(key);
        if (value == null) throw new RuntimeException(key + " not specified in the properties file!");
        return value;
    }

}
