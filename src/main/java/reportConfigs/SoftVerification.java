package reportConfigs;

import commons.BaseTest;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Assert;
import org.testng.Reporter;

public class SoftVerification extends BaseTest {

    private static volatile SoftVerification instance;

    public static SoftVerification getSoftVerification() {
        if (instance == null) {
            synchronized (SoftVerification.class) {
                if (instance == null) {
                    instance = new SoftVerification();
                }
            }
        }
        return instance;
    }

    public boolean verifyTrue(boolean condition) {
        boolean status = true;
        try {
            Assert.assertTrue(condition);
            verificationPassed("");
        } catch (Throwable t) {
            status = false;
            VerificationFailures.getFailures().addFailureForTest(Reporter.getCurrentTestResult(), t);
            Reporter.getCurrentTestResult().setThrowable(t);
            verificationFailed(t.getMessage());
        }
        return status;
    }

    public boolean verifyFalse(boolean condition) {
        boolean status = true;
        try {
            Assert.assertFalse(condition);
            verificationPassed("");
        } catch (Throwable t) {
            status = false;
            VerificationFailures.getFailures().addFailureForTest(Reporter.getCurrentTestResult(), t);
            Reporter.getCurrentTestResult().setThrowable(t);
            verificationFailed(t.getMessage());
        }
        return status;
    }

    public boolean verifyEquals(Object actual, Object expected) {
        boolean status = true;
        try {
            Assert.assertEquals(actual, expected);
            verificationPassed(": value = " + expected);
        } catch (Throwable t) {
            status = false;
            VerificationFailures.getFailures().addFailureForTest(Reporter.getCurrentTestResult(), t);
            Reporter.getCurrentTestResult().setThrowable(t);
            verificationFailed(t.getMessage());
        }
        return status;
    }

    @Step("=== VERIFICATION PASSED{0}")
    private void verificationPassed(Object value) {
    }

    @Step("=== VERIFICATION FAILED: {0}")
    private void verificationFailed(String failureMessage) {
        allureAttachScreenshot();
    }

    @Attachment(value = "verification failure screenshot", type = "image/png")
    private byte[] allureAttachScreenshot() {
        return ((TakesScreenshot) driverThreadLocal.get()).getScreenshotAs(OutputType.BYTES);
    }

}
