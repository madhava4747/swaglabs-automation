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

        String env = System.getProperty("env", "qa");
        String browser = System.getProperty("browser", "chrome");

        Files.writeString(
                allureResults.resolve("environment.properties"),
                String.format(
                        "Environment=%s%nBaseURL=%s%nBrowser=%s%nTester=Madhav%n",
                        env,
                        ConfigReader.get("base.url"),
                        browser
                )
        );
    }

    @BeforeMethod(alwaysRun = true)
    public void setup() {

        String browser = System.getProperty("browser", "chrome");
        boolean headless = Boolean.parseBoolean(
                ConfigReader.get("headless")
        );

        TestLogCollector.log(
                "INFO",
                "Browser started: " + browser +
                        " | Headless: " + headless +
                        " | Thread: " + Thread.currentThread().getName()
        );

        DriverFactory
                .getDriver(browser, headless)
                .get(ConfigReader.get("base.url"));
    }

    protected WebDriver getDriver() {
        return DriverFactory.getDriver(
                System.getProperty("browser", "chrome"),
                Boolean.parseBoolean(ConfigReader.get("headless"))
        );
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
            TestLogCollector.log("PASS", "TEST PASSED: " + result.getName());
        }

        if (result.getStatus() == ITestResult.SKIP) {
            TestLogCollector.log("SKIP", "TEST SKIPPED: " + result.getName());
        }

        DriverFactory.quitDriver();
    }

    @AfterSuite(alwaysRun = true)
    public void afterSuite() {
        TestLogCollector.log("INFO", "===== TEST SUITE FINISHED =====");
        TestLogCollector.flushToFile();
    }
}
