package automationpackage.Saucedemo.pages.swagpages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import automationpackage.Saucedemo.utils.TestLogCollector;

public class LoginPage extends BasePage {

    private final By username = By.id("user-name");
    private final By password = By.id("password");
    private final By loginBtn = By.id("login-button");
    private final By errorMsg = By.cssSelector("[data-test='error']");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void login(String user, String pass) {

        TestLogCollector.log("INFO", "Login started for user: " + user);

        type(username, user);
        type(password, pass);
        click(loginBtn);

        TestLogCollector.log("INFO", "Login button clicked");
    }

    public boolean isErrorDisplayed() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
            return wait.until(
                ExpectedConditions.presenceOfElementLocated(errorMsg)
            ).isDisplayed();
        } catch (Exception e) {
            return false; // prevents browser-crash failure
        }
    }
}
