package automationpackage.Saucedemo.tests;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import io.qameta.allure.*;
import automationpackage.Saucedemo.base.BaseTest;
import automationpackage.Saucedemo.utils.*;
import automationpackage.Saucedemo.pages.swagpages.*;

@Epic("Swag Labs")
@Feature("End-to-End Purchase Flow")
//@Listeners(automationpackage.Saucedemo.listeners.TestExecutionLogger.class)
public class SwagLabTest extends BaseTest {

    /* =========================
       DATA PROVIDER
    ========================== */
	@DataProvider(name = "excelData", parallel = true)
	public Object[][] excelDataProvider() {
	    return ExcelUtil.getTestData(
	    		"testdata/SwagLabsData.xlsx",
	    	    "Purchase"
	    );
	}


    /* =========================
       MAIN E2E TEST
    ========================== */
	
	@Test(dataProvider = "excelData")
	@Story("User completes purchase flow")
	public void verifyCompletePurchaseFlow(
	        String username,
	        String password,
	        String firstName,
	        String lastName,
	        String zip,
	        String severity,
	        String expectedResult) {

	    // -------- Allure Severity --------
	    SeverityLevel allureSeverity;

	    switch (severity.toLowerCase()) {
	        case "blocker":
	            allureSeverity = SeverityLevel.BLOCKER;
	            break;
	        case "critical":
	            allureSeverity = SeverityLevel.CRITICAL;
	            break;
	        case "minor":
	            allureSeverity = SeverityLevel.MINOR;
	            break;
	        case "normal":
	            allureSeverity = SeverityLevel.NORMAL;
	            break;
	        default:
	            allureSeverity = SeverityLevel.TRIVIAL;
	    }
	    Allure.label("severity", allureSeverity.name().toLowerCase());

	    // -------- Login --------
	    LoginPage loginPage = new LoginPage(getDriver());
	    loginPage.login(username, password);

	    if (expectedResult.equalsIgnoreCase("ERROR")) {

	        Assert.assertTrue(
	            loginPage.isErrorDisplayed(),
	            "Expected login error but error message was NOT shown"
	        );

	        // stop test here
	        throw new AssertionError(
	            "Negative login scenario validated (intentional failure)"
	        );

	        // ❌ Force this test to FAIL (because this is a defect validation)
	        //Assert.fail("Login failed as expected for invalid credentials");
	    }
	    // -------- Products --------
	    ProductPage productPage = new ProductPage(getDriver());
	    Assert.assertTrue(
	        productPage.isDisplayed(),
	        "Products page not displayed after login"
	    );

	    productPage.addProductToCart();
	    productPage.openCart();

	    // -------- Cart --------
	    CartPage cartPage = new CartPage(getDriver());
	    Assert.assertTrue(cartPage.isDisplayed(), "Cart page not displayed");
	    cartPage.clickCheckout();

	    // -------- Checkout --------
	    CheckoutPage checkoutPage = new CheckoutPage(getDriver());
	    Assert.assertTrue(checkoutPage.isDisplayed(), "Checkout page not displayed");

	    checkoutPage.enterCheckoutDetails(firstName, lastName, zip);
	    checkoutPage.clickContinue();

	    // -------- Overview --------
	    OverviewPage overviewPage = new OverviewPage(getDriver());
	    Assert.assertTrue(overviewPage.isDisplayed(), "Overview page not displayed");
	    overviewPage.finishOrder();

	    // -------- Complete --------
	    CompletePage completePage = new CompletePage(getDriver());
	    Assert.assertEquals(
	        completePage.getSuccessMessage(),
	        "Thank you for your order!",
	        "Order confirmation message mismatch"
	    );
	}

}
