package automationpackage.Saucedemo.utils;

import java.util.Properties;
import java.io.InputStream;

public class ConfigReader {

    private static Properties prop = new Properties();

    static {
        try {
            InputStream is = ConfigReader.class
                    .getClassLoader()
                    .getResourceAsStream("config.properties");
            prop.load(is);
        } catch (Exception e) {
            throw new RuntimeException("Config load failed");
        }
    }

    public static String get(String key) {
        return prop.getProperty(key);
    }
}
