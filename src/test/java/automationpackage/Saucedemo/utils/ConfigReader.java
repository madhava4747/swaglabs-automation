package automationpackage.Saucedemo.utils;

import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

    private static final Properties prop = new Properties();

    static {
        try {
            // Read env from Jenkins / Maven
            String env = System.getProperty("env", "qa");

            String fileName = "config/" + env + ".properties";

            InputStream is = ConfigReader.class
                    .getClassLoader()
                    .getResourceAsStream(fileName);

            if (is == null) {
                throw new RuntimeException(
                        fileName + " not found. Make sure it exists under src/test/resources/config"
                );
            }

            prop.load(is);

        } catch (Exception e) {
            throw new RuntimeException("Failed to load environment config", e);
        }
    }

    private ConfigReader() {
        // prevent object creation
    }

    public static String get(String key) {
        String value = prop.getProperty(key);
        if (value == null) {
            throw new RuntimeException("Property not found: " + key);
        }
        return value.trim();
    }
}
