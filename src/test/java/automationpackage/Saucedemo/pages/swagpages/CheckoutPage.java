package automationpackage.Saucedemo.pages.swagpages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import automationpackage.Saucedemo.utils.TestLogCollector;

public class CheckoutPage extends BasePage {

    private final By title = By.className("title");
    private final By firstName = By.id("first-name");
    private final By lastName = By.id("last-name");
    private final By zip = By.id("postal-code");
    private final By continueBtn = By.id("continue");

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    public boolean isDisplayed() {
        boolean visible = isDisplayed(title);
        TestLogCollector.log("INFO", "Checkout page displayed: " + visible);
        return visible;
    }

    public void enterCheckoutDetails(String fn, String ln, String zp) {
        TestLogCollector.log("INFO", "Entering checkout details");
        type(firstName, fn);
        type(lastName, ln);
        type(zip, zp);
    }

    public void clickContinue() {
        TestLogCollector.log("INFO", "Clicking Continue");
        click(continueBtn);
    }
}
