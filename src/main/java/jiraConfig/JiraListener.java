package jiraConfig;

import commons.BasePage;
import commons.BaseTest;
import commons.GlobalConstants;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.lang.reflect.Method;

public class JiraListener implements ITestListener {
    @Override
    public void onTestFailure(ITestResult result) {
        Method method = result.getMethod().getConstructorOrMethod().getMethod();

        JiraCreateIssue annotation = method.getAnnotation(JiraCreateIssue.class);
        if (annotation != null && annotation.isCreateIssue()) {
            JiraServiceProvider jiraServiceProvider = new JiraServiceProvider(GlobalConstants.JIRA_URL,
                    GlobalConstants.JIRA_USERNAME, GlobalConstants.JIRA_API_KEY, GlobalConstants.JIRA_PROJECT_KEY);

            String browserName = BasePage.getCurrentBrowserName(BaseTest.getDriverThreadLocal().get());
            String issueSummary = method.getName() + " FAILED in Automation Testing on " + browserName;

            StringBuilder issueDescription = new StringBuilder("Failure Reason from Automation Testing\n\n");
            if (result.getThrowable() != null) {
                issueDescription.append(ExceptionUtils.getFullStackTrace(result.getThrowable()));
            } else {
                issueDescription.append("No exception message available.");
            }

            jiraServiceProvider.createJiraIssue("Bug", issueSummary, issueDescription.toString());
        }
    }
}
