package automationpackage.Saucedemo.base;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.*;

import org.openqa.selenium.*;
import org.testng.ITestResult;
import org.testng.annotations.*;

import io.qameta.allure.Allure;
import automationpackage.Saucedemo.utils.*;

public class BaseTest {

    @BeforeSuite(alwaysRun = true)
    public void beforeSuite() throws IOException {

        TestLogCollector.log("INFO", "===== TEST SUITE STARTED =====");

        Path allureResults = Paths.get("target", "allure-results");
        Files.createDirectories(allureResults);

        Files.writeString(
                allureResults.resolve("environment.properties"),
                String.format(
                        "Environment=QA%nBaseURL=%s%nBrowser=Edge%nTester=Madhav%n",
                        ConfigReader.get("baseUrl")
                )
        );
    }

    @Parameters("browser")
    @BeforeMethod(alwaysRun = true)
    public void setup(@Optional("edge") String browser) {

        TestLogCollector.log(
                "INFO",
                "Browser started: " + browser +
                        " | Thread: " + Thread.currentThread().getName()
        );

        DriverFactory.getDriver(browser)
                .get(ConfigReader.get("baseUrl"));
    }

    protected WebDriver getDriver() {
        return DriverFactory.getDriver(null);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {

        if (result.getStatus() == ITestResult.FAILURE) {
            TestLogCollector.log(
                    "FAIL",
                    "TEST FAILED: " + result.getName() +
                            " | Reason: " + result.getThrowable()
            );

            Allure.addAttachment(
                    "Failure Screenshot",
                    new ByteArrayInputStream(
                            ((TakesScreenshot) getDriver())
                                    .getScreenshotAs(OutputType.BYTES)
                    )
            );
        }

        if (result.getStatus() == ITestResult.SUCCESS) {
            TestLogCollector.log(
                    "PASS",
                    "TEST PASSED: " + result.getName()
            );
        }

        if (result.getStatus() == ITestResult.SKIP) {
            TestLogCollector.log(
                    "SKIP",
                    "TEST SKIPPED: " + result.getName()
            );
        }

        DriverFactory.quitDriver();
    }

    @AfterSuite(alwaysRun = true)
    public void afterSuite() {

        TestLogCollector.log("INFO", "===== TEST SUITE FINISHED =====");
        TestLogCollector.flushToFile();
    }
}
