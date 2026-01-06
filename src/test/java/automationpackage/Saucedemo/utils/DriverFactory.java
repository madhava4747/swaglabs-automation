package automationpackage.Saucedemo.utils;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverFactory {

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static WebDriver getDriver(String browser) {

        if (driver.get() == null) {

        	if (browser.equalsIgnoreCase("edge")) {

        	    System.setProperty(
        	        "webdriver.edge.driver",
        	        "C:\\Users\\MadhavaraoManchi\\eclipse-workspace\\edgedriver_win64\\msedgedriver.exe"
        	    );

        	    EdgeOptions options = new EdgeOptions();
        	    driver.set(new EdgeDriver(options));
        	}
        	
        	else {
                // Default: Chrome
                WebDriverManager.chromedriver().setup();

                ChromeOptions options = new ChromeOptions();
                options.addArguments("--incognito");
                options.addArguments("--disable-notifications");

                Map<String, Object> prefs = new HashMap<>();
                prefs.put("credentials_enable_service", false);
                prefs.put("profile.password_manager_enabled", false);
                options.setExperimentalOption("prefs", prefs);

                driver.set(new ChromeDriver(options));
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
