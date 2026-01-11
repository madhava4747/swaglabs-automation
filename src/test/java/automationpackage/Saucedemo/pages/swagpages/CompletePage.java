package automationpackage.Saucedemo.pages.swagpages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import automationpackage.Saucedemo.utils.TestLogCollector;

public class CompletePage extends BasePage {

    private final By successMsg = By.className("complete-header");
    private final By errorMsg = By.cssSelector("[data-test='error']");
    

    public CompletePage(WebDriver driver) {
        super(driver);
    }

    public String getSuccessMessage() {
        String msg = getText(successMsg);
        TestLogCollector.log("INFO", "Order success message: " + msg);
        return msg;
    }
    public boolean isSucessErrorDisplayed() {
		boolean displayed = driver.findElements(errorMsg).size() > 0;
        TestLogCollector.log("INFO", "Success error displayed: " + displayed);
		// TODO Auto-generated method stub
		return displayed;
	}
}
