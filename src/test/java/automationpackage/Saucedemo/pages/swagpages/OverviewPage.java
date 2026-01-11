package automationpackage.Saucedemo.pages.swagpages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import automationpackage.Saucedemo.utils.TestLogCollector;

public class OverviewPage extends BasePage {

    private final By title = By.className("title");
    private final By finishBtn = By.id("finish");
    private final By errorMsg = By.cssSelector("[data-test='error']");
   

    public OverviewPage(WebDriver driver) {
        super(driver);
    }

    public boolean isDisplayed() {
        boolean visible = isDisplayed(title);
        TestLogCollector.log("INFO", "Overview page displayed: " + visible);
        return visible;
    }

    public void finishOrder() {
        TestLogCollector.log("INFO", "Finishing order");
        click(finishBtn);
    }

	public boolean isOverviewErrorDisplayed() {
		boolean displayed = driver.findElements(errorMsg).size() > 0;
        TestLogCollector.log("INFO", "Overview error displayed: " + displayed);
		// TODO Auto-generated method stub
		return displayed;
	}
}
