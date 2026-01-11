package automationpackage.Saucedemo.pages.swagpages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import automationpackage.Saucedemo.utils.TestLogCollector;

public class ProductPage extends BasePage {

    private final By title = By.className("title");
    private final By addToCart = By.id("add-to-cart-sauce-labs-backpack");
    private final By cartIcon = By.className("shopping_cart_link");
    private final By errorMsg = By.cssSelector("[data-test='error']");

    public ProductPage(WebDriver driver) {
        super(driver);
    }

    public boolean isDisplayed() {
        boolean visible = isDisplayed(title);
        TestLogCollector.log("INFO", "Product page displayed: " + visible);
        return visible;
    }

    public void addProductToCart() {
        TestLogCollector.log("INFO", "Adding product to cart");
        click(addToCart);
    }

    public void openCart() {
        TestLogCollector.log("INFO", "Navigating to cart page");
        click(cartIcon);
    }
    
	public boolean isAddToCartErrorDisplayed() {
		boolean displayed = driver.findElements(errorMsg).size() > 0;
        TestLogCollector.log("INFO", "Product error displayed: " + displayed);
		// TODO Auto-generated method stub
		return displayed;
	}
}
