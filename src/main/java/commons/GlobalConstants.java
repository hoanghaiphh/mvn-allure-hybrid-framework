package commons;

public class GlobalConstants {
    public static final String PROJECT_PATH = System.getProperty("user.dir");
    public static final String OS_NAME = System.getProperty("os.name");

    public static final String NOPCOMMERCE_LOCAL = "http://local.nopcommerce.com/";
    public static final String SAUCE_DEMO = "https://www.saucedemo.com/";

    public static final long SHORT_TIMEOUT = 3;
    public static final long LONG_TIMEOUT = 30;

    public static final String ALLURE_RESULTS_FOLDER_PATH = PROJECT_PATH + "/allure-results/";
}
