package automationpackage.Saucedemo.tests;

import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.qameta.allure.*;

import automationpackage.Saucedemo.base.BaseTest;
import automationpackage.Saucedemo.pages.swagpages.*;
import automationpackage.Saucedemo.utils.ExcelUtil;

@Epic("Swag Labs")
@Feature("End-to-End Purchase Flow")
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
        setAllureSeverity(severity);

        // -------- Login --------
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.login(username, password);

        // -------- NEGATIVE SCENARIO --------
        if (expectedResult.equalsIgnoreCase("ERROR")) {

            Allure.step("Validating negative login scenario");

            Assert.assertTrue(
                    loginPage.isErrorDisplayed(),
                    "Expected login error message but it was not displayed"
            );

            Allure.step("Negative login scenario validated successfully");
            return; // ✅ PASS the test
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
        Assert.assertTrue(
                cartPage.isDisplayed(),
                "Cart page not displayed"
        );
        cartPage.clickCheckout();

        // -------- Checkout --------
        CheckoutPage checkoutPage = new CheckoutPage(getDriver());
        Assert.assertTrue(
                checkoutPage.isDisplayed(),
                "Checkout page not displayed"
        );

        checkoutPage.enterCheckoutDetails(firstName, lastName, zip);
        checkoutPage.clickContinue();

        // -------- Overview --------
        OverviewPage overviewPage = new OverviewPage(getDriver());
        Assert.assertTrue(
                overviewPage.isDisplayed(),
                "Overview page not displayed"
        );
        overviewPage.finishOrder();

        // -------- Complete --------
        CompletePage completePage = new CompletePage(getDriver());
        Assert.assertEquals(
                completePage.getSuccessMessage(),
                "Thank you for your order!",
                "Order confirmation message mismatch"
        );

        Allure.step("Order placed successfully");
    }

    /* =========================
       ALLURE SEVERITY HELPER
    ========================== */
    private void setAllureSeverity(String severity) {
        SeverityLevel level;

        switch (severity.toLowerCase()) {
            case "blocker":
                level = SeverityLevel.BLOCKER;
                break;
            case "critical":
                level = SeverityLevel.CRITICAL;
                break;
            case "minor":
                level = SeverityLevel.MINOR;
                break;
            case "normal":
                level = SeverityLevel.NORMAL;
                break;
            default:
                level = SeverityLevel.TRIVIAL;
        }

        Allure.label("severity", level.name().toLowerCase());
    }
}
