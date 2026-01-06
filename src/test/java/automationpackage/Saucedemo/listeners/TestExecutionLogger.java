package automationpackage.Saucedemo.listeners;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.testng.*;

import automationpackage.Saucedemo.utils.TestLogCollector;

public class TestExecutionLogger implements ISuiteListener, ITestListener {

    @Override
    public void onStart(ISuite suite) {
        TestLogCollector.log("INFO", "===== TEST SUITE STARTED =====");
    }

    @Override
    public void onFinish(ISuite suite) {
        TestLogCollector.log("INFO", "===== TEST SUITE FINISHED =====");
        writeLogsToFile();   // ✅ now matches
    }


    @Override
    public void onTestStart(ITestResult result) {

        Object[] params = result.getParameters();
        String paramString = params.length > 0
            ? java.util.Arrays.toString(params)
            : "No parameters";

        TestLogCollector.log(
            "INFO",
            "TEST STARTED: " + result.getMethod().getMethodName() +
            " | Data: " + paramString
        );
    }


    @Override
    public void onTestSuccess(ITestResult result) {
        TestLogCollector.log(
            "PASS",
            "TEST PASSED: " + result.getMethod().getMethodName()
        );
    }

    @Override
    public void onTestFailure(ITestResult result) {

        Throwable t = result.getThrowable();
        String status;
        String reason;

        if (t instanceof AssertionError) {
            status = "FAIL";
            reason = "Assertion failed: " + t.getMessage();
        } else {
            status = "BROKEN";
            String msg = t.getMessage();
            reason = t.getClass().getSimpleName() +
                     (msg != null ? " : " + msg.split("\n")[0] : "");
        }

        TestLogCollector.log(
            status,
            "TEST " + status + ": " + result.getMethod().getMethodName() +
            " | Reason: " + reason
        );
    }



    @Override
    public void onTestSkipped(ITestResult result) {

        String reason = result.getThrowable() != null
            ? result.getThrowable().getMessage()
            : "Condition-based skip";

        TestLogCollector.log(
            "SKIP",
            "TEST SKIPPED: " + result.getMethod().getMethodName() +
            " | Reason: " + reason
        );
    }

    private void writeLogsToFile() {
        try {
            Path logDir = Paths.get("target/test-logs");
            Files.createDirectories(logDir);

            Path logFile = logDir.resolve("execution.log");
            Files.write(logFile, TestLogCollector.getLogs());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
