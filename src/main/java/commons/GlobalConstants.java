package commons;

public class GlobalConstants {
    public static final String PROJECT_PATH = System.getProperty("user.dir");
    public static final String OS_NAME = System.getProperty("os.name");

    public static final String NOPCOMMERCE_LOCAL = "http://local.nopcommerce.com/";
    public static final String SAUCE_DEMO = "https://www.saucedemo.com/";
    public static final String JQUERY_FILE_UPLOAD = "https://blueimp.github.io/jQuery-File-Upload/";
    public static final String JQUERY_DATA_TABLE_1
            = "https://www.jqueryscript.net/demo/CRUD-Data-Grid-Plugin-jQuery-Quickgrid/";
    public static final String JQUERY_DATA_TABLE_2
            = "https://www.jqueryscript.net/demo/jQuery-Dynamic-Data-Grid-Plugin-appendGrid/";

    public static final long SHORT_TIMEOUT = 3;
    public static final long LONG_TIMEOUT = 30;

    public static final String ALLURE_RESULTS_FOLDER_PATH = PROJECT_PATH + "/allure-results/";
    public static final String BROWSER_LOGS_FOLDER_PATH = PROJECT_PATH + "/browserLogs/";

    public static final String BROWSERSTACK_USERNAME = "hoanghaiphan_abM5ye";
    public static final String BROWSERSTACK_ACCESS_KEY = "qD48tcbxg3mR4yCixi1y";
    public static final String BROWSERSTACK_URL = "https://" + BROWSERSTACK_USERNAME + ":" + BROWSERSTACK_ACCESS_KEY
            + "@hub-cloud.browserstack.com/wd/hub";

    public static final String SAUCELABS_USERNAME = "oauth-hoanghai.phh-6f893";
    public static final String SAUCELABS_ACCESS_KEY = "158fee65-9928-4c43-bb8b-51efef8bff13";
    public static final String SAUCELABS_DATA_CENTER = "eu-central-1";
    public static final String SAUCELABS_URL = "https://" + SAUCELABS_USERNAME + ":" + SAUCELABS_ACCESS_KEY
            + "@ondemand." + SAUCELABS_DATA_CENTER + ".saucelabs.com:443/wd/hub";

}
