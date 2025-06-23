package utilities;

import java.io.InputStream;
import java.util.Properties;

public class PropertiesConfig {

    private final Properties properties;

    private PropertiesConfig(String environment) {
        String str = "environments/env-%s.properties";
        String propertyFile = String.format(str, environment);

        try (InputStream input = getClass().getClassLoader().getResourceAsStream(propertyFile)) {
            if (input == null) throw new RuntimeException("Configuration properties not found at " + propertyFile);

            properties = new Properties();
            properties.load(input);

        } catch (Exception e) {
            throw new RuntimeException("Error while loading properties from " + propertyFile, e);
        }
    }

    public static PropertiesConfig getEnvironmentProperties() {
        String systemEnv = System.getProperty("env", "local").toLowerCase();
        return new PropertiesConfig(systemEnv);
    }

    public String getPropertyValue(String key) {
        String value = properties.getProperty(key);
        if (value == null) throw new RuntimeException(key + " not specified in the properties file!");
        return value;
    }

}
