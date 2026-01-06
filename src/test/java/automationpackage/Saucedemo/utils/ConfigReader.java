package automationpackage.Saucedemo.utils;

import java.util.Properties;
import java.io.InputStream;

public class ConfigReader {

    private static final Properties prop = new Properties();

    static {
        try {
            InputStream is = ConfigReader.class
                    .getClassLoader()
                    .getResourceAsStream("config.properties");

            if (is == null) {
                throw new RuntimeException(
                        "config.properties not found. " +
                        "Make sure it is placed under src/test/resources"
                );
            }

            prop.load(is);

        } catch (Exception e) {
            throw new RuntimeException("Config load failed", e);
        }
    }

    private ConfigReader() {
        // prevent object creation
    }

    public static String get(String key) {
        String value = prop.getProperty(key);
        if (value == null) {
            throw new RuntimeException("Property not found in config.properties: " + key);
        }
        return value.trim();
    }
}
