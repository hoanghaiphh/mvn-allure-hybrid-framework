package reportConfigs;

import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.internal.Utils;

import java.util.List;

public class MethodListener implements IInvokedMethodListener {
    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult result) {

    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult result) {
        Reporter.setCurrentTestResult(result);
        if (method.isTestMethod()) {
            VerificationFailures allFailures = VerificationFailures.getFailures();

            // Add an existing failure for the result to the failure list.
            if (result.getThrowable() != null) {
                allFailures.addFailureForTest(result, result.getThrowable());
            }

            List<Throwable> failures = allFailures.getFailuresForTest(result);
            if (!failures.isEmpty()) {
                result.setStatus(ITestResult.FAILURE);
                String lastFailure = failures.get(failures.size() - 1).toString();

                if (failures.size() == 1) {
                    result.setThrowable(failures.get(0));

                } else if (failures.size() == 2 && lastFailure.contains("java.lang.AssertionError")) {
                    String message = "1 Verification failure\n" + Utils.shortStackTrace(failures.get(0), false) + "\n";
                    result.setThrowable(new Throwable(message));

                } else {
                    StringBuffer message = new StringBuffer();
                    int adjustSize = 0;
                    if (lastFailure.contains("java.lang.AssertionError")) {
                        adjustSize = failures.size() - 1;
                        message.append(adjustSize + " Verification failures\n");
                    } else {
                        adjustSize = failures.size();
                        message.append(adjustSize + " Failures, including " + (adjustSize - 1) + " Verification & 1 Other\n");
                    }

                    for (int i = 0; i < adjustSize; i++) {
                        message.append("Failure " + (i + 1) + " of " + adjustSize + "\n");
                        message.append(Utils.shortStackTrace(failures.get(i), false)).append("\n\n");
                    }
                    result.setThrowable(new Throwable(message.toString()));
                }
            }
        }
    }
}
