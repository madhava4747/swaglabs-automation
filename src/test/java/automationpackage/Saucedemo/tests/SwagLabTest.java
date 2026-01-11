package automationpackage.Saucedemo.tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.qameta.allure.*;
import automationpackage.Saucedemo.base.BaseTest;
import automationpackage.Saucedemo.utils.ExcelUtil;
import automationpackage.Saucedemo.pages.swagpages.*;

@Epic("Swag Labs")
@Feature("End-to-End Purchase Flow")
public class SwagLabTest extends BaseTest {

    /* =========================
       DATA PROVIDER
    ========================== */
    @DataProvider(name = "excelData", parallel = false)
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

        setAllureSeverity(severity);

        // -------- Login --------
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.login(username, password);

        if (expectedResult.equalsIgnoreCase("ERROR")) {

            Assert.assertTrue(
                    loginPage.isErrorDisplayed(),
                    "Expected login error but error message was NOT shown"
            );

            Allure.step("Negative login validated successfully");
            return;
        }

        // -------- Products --------
        ProductPage productPage = new ProductPage(getDriver());
        Assert.assertTrue(
                productPage.isDisplayed(),
                "Products page not displayed after login"
        );

        if (expectedResult.equalsIgnoreCase("PRODUCT_ERROR")) {

            Assert.assertTrue(
                    productPage.isAddToCartErrorDisplayed(),
                    "Expected product error message not displayed"
            );

            Allure.step("Product page negative scenario validated");
            return;
        }

        productPage.addProductToCart();
        productPage.openCart();

        // -------- Cart --------
        CartPage cartPage = new CartPage(getDriver());
        Assert.assertTrue(cartPage.isDisplayed(), "Cart page not displayed");

        if (expectedResult.equalsIgnoreCase("CART_ERROR")) {

            Assert.assertTrue(
                    cartPage.isEmptyCartErrorDisplayed(),
                    "Expected cart validation error message not displayed"
            );

            Allure.step("Cart page negative scenario validated");
            return;
        }

        cartPage.clickCheckout();

        // -------- Checkout --------
        CheckoutPage checkoutPage = new CheckoutPage(getDriver());
        Assert.assertTrue(checkoutPage.isDisplayed(), "Checkout page not displayed");

        if (expectedResult.equalsIgnoreCase("CHECKOUT_ERROR")) {

            Assert.assertTrue(
                    checkoutPage.isCheckoutErrorDisplayed(),
                    "Expected checkout validation error message not displayed"
            );

            Allure.step("Checkout page negative scenario validated");
            return;
        }

        checkoutPage.enterCheckoutDetails(firstName, lastName, zip);
        checkoutPage.clickContinue();

        // -------- Overview --------
        OverviewPage overviewPage = new OverviewPage(getDriver());
        Assert.assertTrue(overviewPage.isDisplayed(), "Overview page not displayed");

        if (expectedResult.equalsIgnoreCase("OVERVIEW_ERROR")) {

            Assert.assertTrue(
                    overviewPage.isOverviewErrorDisplayed(),
                    "Expected overview validation error message not displayed"
            );

            Allure.step("Overview page negative scenario validated");
            return;
        }

        overviewPage.finishOrder();

        // -------- Complete (SUCCESS ONLY) --------
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
