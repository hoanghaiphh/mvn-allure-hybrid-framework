package jiraConfig;

import commons.BaseTest;
import net.rcarz.jiraclient.*;
import net.rcarz.jiraclient.Issue.FluentCreate;

public class JiraServiceProvider extends BaseTest {
    private JiraClient jira;
    private String projectKey;
    private String jiraUrl;

    public JiraServiceProvider(String url, String username, String password, String project) {
        try {
            this.jiraUrl = url;
            BasicCredentials credentials = new BasicCredentials(username, password);
            jira = new JiraClient(url, credentials);
            this.projectKey = project;

            jira.getProjects();
        } catch (JiraException e) {
            log.error("Jira connection failed!", e);
            jira = null;
        }
    }

    public void createJiraIssue(String issueType, String summary, String description) {
        if (jira != null) {
            try {
                Issue.SearchResult sr = jira.searchIssues(
                        "project = " + projectKey + " AND summary ~ \"" + summary + "\"");
                if (sr.issues.isEmpty()) {
                    FluentCreate fluentCreate = jira.createIssue(projectKey, issueType);
                    fluentCreate.field(Field.SUMMARY, summary);
                    fluentCreate.field(Field.DESCRIPTION, description);
                    Issue newIssue = fluentCreate.execute();

                    log.info("New issue URL: {}/browse/{}", jiraUrl, newIssue.getKey());
                } else {
                    log.info("Same issue already exists on Jira with ID: {}", sr.issues.get(0).getKey());
                }
            } catch (JiraException e) {
                log.error("Failed to create Jira issue!", e);
            }
        }
    }
}