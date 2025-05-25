package factoryBrowser;

public class BrowserNotSupportedException extends IllegalStateException {

    public BrowserNotSupportedException(String browserName) {
        super(browserName.toUpperCase() + " is not supported!");
    }

}
