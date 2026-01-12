package automationpackage.Saucedemo.utils;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverFactory {

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static WebDriver getDriver(String browser, boolean headless) {

        if (driver.get() == null) {

            switch (browser.toLowerCase()) {

                case "edge":
                    WebDriverManager.edgedriver().setup();

                    EdgeOptions edgeOptions = new EdgeOptions();
                    if (headless) {
                        edgeOptions.addArguments("--headless=new");
                    }

                    driver.set(new EdgeDriver(edgeOptions));
                    break;

                case "firefox":
                    WebDriverManager.firefoxdriver().setup();

                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    if (headless) {
                        firefoxOptions.addArguments("-headless");
                    }

                    driver.set(new FirefoxDriver(firefoxOptions));
                    break;

                default: // chrome
                    WebDriverManager.chromedriver().setup();

                    ChromeOptions chromeOptions = new ChromeOptions();
                    chromeOptions.addArguments("--incognito");
                    chromeOptions.addArguments("--disable-notifications");

                    if (headless) {
                        chromeOptions.addArguments("--headless=new");
                    }

                    Map<String, Object> prefs = new HashMap<>();
                    prefs.put("credentials_enable_service", false);
                    prefs.put("profile.password_manager_enabled", false);
                    chromeOptions.setExperimentalOption("prefs", prefs);

                    driver.set(new ChromeDriver(chromeOptions));
            }

            driver.get().manage().window().maximize();
        }

        return driver.get();
    }

    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}
