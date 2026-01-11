package automationpackage.Saucedemo.pages.swagpages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import automationpackage.Saucedemo.utils.TestLogCollector;

public class CartPage extends BasePage {

    private final By cartTitle = By.className("title");
    private final By checkoutBtn = By.id("checkout");
    private final By errorMsg = By.cssSelector("[data-test='error']");
    

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public boolean isDisplayed() {
        boolean visible = isDisplayed(cartTitle);
        TestLogCollector.log("INFO", "Cart page displayed: " + visible);
        return visible;
    }

    public void clickCheckout() {
        TestLogCollector.log("INFO", "Clicking Checkout button");
        click(checkoutBtn);
    }

	public boolean isEmptyCartErrorDisplayed() {
		boolean displayed = driver.findElements(errorMsg).size() > 0;
        TestLogCollector.log("INFO", "Cart Page error displayed: " + displayed);
		// TODO Auto-generated method stub
		return displayed;
	}
}
