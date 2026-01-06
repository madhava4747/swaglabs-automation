package automationpackage.Saucedemo.pages.swagpages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
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
        boolean displayed = driver.findElements(errorMsg).size() > 0;
        TestLogCollector.log("INFO", "Login error displayed: " + displayed);
        return displayed;
    }
}
