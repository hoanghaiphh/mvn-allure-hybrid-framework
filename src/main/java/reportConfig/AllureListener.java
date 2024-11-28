package reportConfig;

import commons.BaseTest;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class AllureListener extends BaseTest implements ITestListener {

    // Screenshot attachments for Allure
    @Attachment(value = "{0} FAILURE SCREENSHOT", type = "image/png")
    private byte[] attachScreenshot(String title, WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    // Text attachments for Allure
    @Attachment(value = "{0}", type = "text/plain")
    private String attachTextLog(String title, String log) {
        return log;
    }

    // HTML attachments for Allure
    @Attachment(value = "{0}", type = "text/html")
    private String attachHtml(String html) {
        return html;
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        String testThrowable = iTestResult.getThrowable().toString();
        attachTextLog(iTestResult.getName() + " FAILED", testThrowable);
        if (!testThrowable.contains("Verification failures") && !testThrowable.contains("Verification failure")) {
            WebDriver driver = ((BaseTest) iTestResult.getInstance()).getDriver();
            attachScreenshot(iTestResult.getName(), driver);
        }
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        attachTextLog(iTestResult.getName() + " PASSED", "");
    }

    @Override
    public void onStart(ITestContext iTestContext) {
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
    }

    @Override
    public void onFinish(ITestContext arg0) {
    }

    @Override
    public void onTestStart(ITestResult arg0) {
    }

}