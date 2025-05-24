package commons;

public class EnumList {

    public enum Browser {
        CHROME, EDGE, FIREFOX, SAFARI, CHROME_HEADLESS, FIREFOX_HEADLESS, EDGE_HEADLESS
    }

    public enum OS {
        WINDOWS, MAC, LINUX
    }

    public enum Platform {
        LOCAL, GRID, BROWSER_STACK, SAUCE_LABS, LAMBDA_TEST
    }
}
