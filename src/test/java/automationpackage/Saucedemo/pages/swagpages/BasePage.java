package automationpackage.Saucedemo.pages.swagpages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import automationpackage.Saucedemo.utils.WaitUtil;

public abstract class BasePage {

    protected WebDriver driver;
    protected WaitUtil wait;
    

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WaitUtil(driver);
    }

    protected WebElement waitVisible(By locator) {
        return wait.waitForVisibility(locator);
    }

    protected WebElement waitClickable(By locator) {
        return wait.waitForClickable(locator);
    }

    protected void click(By locator) {
        waitClickable(locator).click();
    }

    protected void type(By locator, String text) {
        WebElement el = waitVisible(locator);
        el.clear();
        el.sendKeys(text);
    }

    protected String getText(By locator) {
        return waitVisible(locator).getText();
    }

    protected boolean isDisplayed(By locator) {
        return waitVisible(locator).isDisplayed();
    }
}
