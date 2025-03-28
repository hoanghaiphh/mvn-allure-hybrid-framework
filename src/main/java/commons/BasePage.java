package commons;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Set;

public class BasePage {

    public static BasePage getBasePage() {
        return new BasePage();
    }

    public void sleepInSeconds(long timeInSecond) {
        try {
            Thread.sleep(timeInSecond * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

//  Browser ------------------------------------------------------------------------------------------------------------

    public static String getCurrentBrowserName(WebDriver driver) {
        return ((RemoteWebDriver) driver).getCapabilities().getBrowserName();
    }

    public void openUrl(WebDriver driver, String pageUrl) {
        driver.get(pageUrl);
    }

    protected String getPageTitle(WebDriver driver) {
        return driver.getTitle();
    }

    protected String getCurrentUrl(WebDriver driver) {
        return driver.getCurrentUrl();
    }

    protected String getPageSource(WebDriver driver) {
        return driver.getPageSource();
    }

    protected void backToPreviousPage(WebDriver driver) {
        driver.navigate().back();
    }

    protected void forwardToNextPage(WebDriver driver) {
        driver.navigate().forward();
    }

    public void refreshCurrentPage(WebDriver driver) {
        driver.navigate().refresh();
    }

    protected Alert waitForAlertPresence(WebDriver driver) {
        return new WebDriverWait(driver, Duration.ofSeconds(GlobalConstants.LONG_TIMEOUT))
                .until(ExpectedConditions.alertIsPresent());
    }

    protected void acceptAlert(WebDriver driver) {
        waitForAlertPresence(driver).accept();
    }

    protected void dismissAlert(WebDriver driver) {
        waitForAlertPresence(driver).dismiss();
    }

    protected String getAlertText(WebDriver driver) {
        return waitForAlertPresence(driver).getText();
    }

    protected void sendKeyToAlert(WebDriver driver, String keyToSend) {
        waitForAlertPresence(driver).sendKeys(keyToSend);
    }

    private String getWindowID(WebDriver driver) {
        return driver.getWindowHandle();
    }

    private Set<String> getAllWindowIDs(WebDriver driver) {
        return driver.getWindowHandles();
    }

    private void switchToWindow(WebDriver driver, String windowHandle) {
        driver.switchTo().window(windowHandle);
    }

    protected void switchToWindowByID(WebDriver driver, String parentID) {
        Set<String> allWindows = getAllWindowIDs(driver);
        for (String runWindow : allWindows) {
            if (!runWindow.equals(parentID)) {
                switchToWindow(driver, runWindow);
                break;
            }
        }
    }

    protected void switchToWindowByTitle(WebDriver driver, String title) {
        Set<String> allWindows = getAllWindowIDs(driver);
        for (String runWindow : allWindows) {
            switchToWindow(driver, runWindow);
            if (driver.getTitle().equals(title)) {
                break;
            }
        }
    }

    protected void closeAllWindowsExceptParent(WebDriver driver, String parentID) {
        Set<String> allWindows = getAllWindowIDs(driver);
        for (String runWindow : allWindows) {
            if (!runWindow.equals(parentID)) {
                switchToWindow(driver, runWindow);
                driver.close();
            }
        }
        switchToWindow(driver, parentID);
    }

    public Set<Cookie> getCookies(WebDriver driver) {
        return driver.manage().getCookies();
    }

    public void setCookies(WebDriver driver, Set<Cookie> cookies) {
        for (Cookie cookie : cookies) {
            driver.manage().addCookie(cookie);
        }
    }

//  Element ------------------------------------------------------------------------------------------------------------

    private By getByLocator(String byLocator) {
        By by = null;
        if (byLocator.toUpperCase().startsWith("CSS")) {
            by = By.cssSelector(byLocator.substring(4));
        } else if (byLocator.toUpperCase().startsWith("XPATH")) {
            by = By.xpath(byLocator.substring(6));
        } else if (byLocator.toUpperCase().startsWith("ID")) {
            by = By.id(byLocator.substring(3));
        } else if (byLocator.toUpperCase().startsWith("CLASS")) {
            by = By.className(byLocator.substring(6));
        } else if (byLocator.toUpperCase().startsWith("NAME")) {
            by = By.name(byLocator.substring(5));
        } else {
            throw new RuntimeException("Locator type is not supported !!!");
        }
        return by;
    }

    private String castParameter(String dynamicLocator, String... restParameter) {
        return String.format(dynamicLocator, (Object[]) restParameter);
    }

    private By getDynamicLocator(String dynamicLocator, String... restParameter) {
        return getByLocator(castParameter(dynamicLocator, restParameter));
    }

    protected void waitForElementVisible(WebDriver driver, String locator, String... restParameter) {
        new WebDriverWait(driver, Duration.ofSeconds(GlobalConstants.LONG_TIMEOUT))
                .until(ExpectedConditions.visibilityOfElementLocated(getDynamicLocator(locator, restParameter)));
    }

    protected void waitForAllElementsVisible(WebDriver driver, String locator, String... restParameter) {
        new WebDriverWait(driver, Duration.ofSeconds(GlobalConstants.LONG_TIMEOUT))
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(getDynamicLocator(locator, restParameter)));
    }

    protected void waitForElementInvisible(WebDriver driver, String locator, String... restParameter) {
        setImplicitlyWait(driver, GlobalConstants.SHORT_TIMEOUT);
        new WebDriverWait(driver, Duration.ofSeconds(GlobalConstants.LONG_TIMEOUT))
                .until(ExpectedConditions.invisibilityOfElementLocated(getDynamicLocator(locator, restParameter)));
        setImplicitlyWait(driver, GlobalConstants.LONG_TIMEOUT);
    }

    public void waitForElementClickable(WebDriver driver, String locator, String... restParameter) {
        new WebDriverWait(driver, Duration.ofSeconds(GlobalConstants.LONG_TIMEOUT))
                .until(ExpectedConditions.elementToBeClickable(getDynamicLocator(locator, restParameter)));
    }

    protected void waitForElementSelected(WebDriver driver, String locator, String... restParameter) {
        new WebDriverWait(driver, Duration.ofSeconds(GlobalConstants.LONG_TIMEOUT))
                .until(ExpectedConditions.elementToBeSelected(getDynamicLocator(locator, restParameter)));
    }

    private WebElement getElement(WebDriver driver, String locator, String... restParameter) {
        return driver.findElement(getDynamicLocator(locator, restParameter));
    }

    protected List<WebElement> getListElements(WebDriver driver, String locator, String... restParameter) {
        return driver.findElements(getDynamicLocator(locator, restParameter));
    }

    public void clickOnElement(WebDriver driver, String locator, String... restParameter) {
        getElement(driver, locator, restParameter).click();
    }

    protected void clearElementText(WebDriver driver, String locator, String... restParameter) {
        getElement(driver, locator, restParameter).clear();
    }

    protected void clearAllElementsText(WebDriver driver, String locator) {
        List<WebElement> allElements = getListElements(driver, locator);
        for (WebElement element : allElements) {
            element.clear();
        }
    }

    public void sendKeysToElement(WebDriver driver, String locator, String keyToSend, String... restParameter) {
        getElement(driver, locator, restParameter).sendKeys(keyToSend);
    }

    private Select getDropdown(WebDriver driver, String locator, String... restParameter) {
        return new Select(getElement(driver, locator, restParameter));
    }

    public void selectOptionInDropdown(WebDriver driver, String locator, String option, String... restParameter) {
        getDropdown(driver, locator, restParameter).selectByVisibleText(option);
    }

    public String getSelectedOptionInDropdown(WebDriver driver, String locator, String... restParameter) {
        return getDropdown(driver, locator, restParameter).getFirstSelectedOption().getText();
    }

    protected boolean isDropdownMultiple(WebDriver driver, String locator, String... restParameter) {
        return getDropdown(driver, locator, restParameter).isMultiple();
    }

    protected void selectOptionInCustomDropdown(WebDriver driver, String dropdownLocator, String optionsLocator, String optionValue) {
        getElement(driver, dropdownLocator).click();
        sleepInSeconds(1);
        List<WebElement> allOptions = new WebDriverWait(driver, Duration.ofSeconds(GlobalConstants.LONG_TIMEOUT))
                .until(ExpectedConditions.presenceOfAllElementsLocatedBy(getByLocator(optionsLocator)));
        for (WebElement option : allOptions) {
            if (option.getText().trim().equals(optionValue)) {
                option.click();
                sleepInSeconds(1);
                break;
            }
        }
    }

    public String getElementText(WebDriver driver, String locator, String... restParameter) {
        return getElement(driver, locator, restParameter).getText();
    }

    public String getAttributeValue(WebDriver driver, String locator, String attributeName, String... restParameter) {
        return getElement(driver, locator, restParameter).getAttribute(attributeName);
    }

    protected String getCssValue(WebDriver driver, String locator, String propertyName, String... restParameter) {
        return getElement(driver, locator, restParameter).getCssValue(propertyName);
    }

    protected String getHexFromRGBA(String rgbaValue) {
        return Color.fromString(rgbaValue).asHex();
    }

    protected int getListElementsSize(WebDriver driver, String locator, String... restParameter) {
        return getListElements(driver, locator, restParameter).size();
    }

    private void setImplicitlyWait(WebDriver driver, long timeInSeconds) {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(timeInSeconds));
    }

    public boolean isElementDisplayed(WebDriver driver, String locator, String... restParameter) {
        setImplicitlyWait(driver, GlobalConstants.SHORT_TIMEOUT);
        List<WebElement> elements = getListElements(driver, locator, restParameter);
        setImplicitlyWait(driver, GlobalConstants.LONG_TIMEOUT);
        if (elements.isEmpty()) {
            return false;
        } else {
            return elements.get(0).isDisplayed();
        }
    }

    public boolean isElementSelected(WebDriver driver, String locator, String... restParameter) {
        return getElement(driver, locator, restParameter).isSelected();
    }

    protected boolean isElementEnabled(WebDriver driver, String locator, String... restParameter) {
        return getElement(driver, locator, restParameter).isEnabled();
    }

    protected void selectCheckboxOrRadio(WebDriver driver, String locator, String... restParameter) {
        if (!isElementSelected(driver, locator, restParameter)) {
            clickOnElement(driver, locator, restParameter);
        }
    }

    protected void deselectCheckbox(WebDriver driver, String locator, String... restParameter) {
        if (isElementSelected(driver, locator, restParameter)) {
            clickOnElement(driver, locator, restParameter);
        }
    }

    protected void switchToFrame(WebDriver driver, String locator, String... restParameter) {
        driver.switchTo().frame(getElement(driver, locator, restParameter));
    }

    protected void switchToDefaultContent(WebDriver driver) {
        driver.switchTo().defaultContent();
    }

//  Actions ------------------------------------------------------------------------------------------------------------

    protected void hoverOnElement(WebDriver driver, String locator, String... restParameter) {
        new Actions(driver).moveToElement(getElement(driver, locator, restParameter)).perform();
    }

    protected void clickAndHoldOnElement(WebDriver driver, String locator, String... restParameter) {
        new Actions(driver).clickAndHold(getElement(driver, locator, restParameter)).perform();
    }

    protected void releaseElement(WebDriver driver) {
        new Actions(driver).release().perform();
    }

    protected void doubleClickOnElement(WebDriver driver, String locator, String... restParameter) {
        new Actions(driver).doubleClick(getElement(driver, locator, restParameter)).perform();
    }

    protected void rightClickOnElement(WebDriver driver, String locator, String... restParameter) {
        new Actions(driver).contextClick(getElement(driver, locator, restParameter)).perform();
    }

    protected void dragAndDropElement(WebDriver driver, String sourceLocator, String targetLocator) {
        new Actions(driver).dragAndDrop(getElement(driver, sourceLocator), getElement(driver, targetLocator)).perform();
    }

    protected void sendKeyPressToElementByActions(WebDriver driver, String locator, Keys key, String... restParameter) {
        new Actions(driver).sendKeys(getElement(driver, locator, restParameter), key).perform();
    }

    protected void scrollToElementByActions(WebDriver driver, String locator, String... restParameter) {
        new Actions(driver).scrollToElement(getElement(driver, locator, restParameter)).perform();
    }

//  JavascriptExecutor -------------------------------------------------------------------------------------------------

    protected void hightlightElement(WebDriver driver, String locator, String... restParameter) {
        WebElement element = getElement(driver, locator, restParameter);
        String originalStyle = element.getAttribute("style");
        ((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute('style', arguments[1])", element, "border: 2px solid red; border-style: dashed;");
        sleepInSeconds(1);
        ((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute('style', arguments[1])", element, originalStyle);
    }

    protected void clickOnElementByJS(WebDriver driver, String locator, String... restParameter) {
        WebElement element = getElement(driver, locator, restParameter);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        sleepInSeconds(1);
    }

    protected void scrollToElementOnTopByJS(WebDriver driver, String locator, String... restParameter) {
        WebElement element = getElement(driver, locator, restParameter);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    protected void scrollToElementOnBottomByJS(WebDriver driver, String locator, String... restParameter) {
        WebElement element = getElement(driver, locator, restParameter);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(false);", element);
    }

    protected void scrollToBottomPageByJS(WebDriver driver) {
        ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,document.body.scrollHeight)");
    }

    protected void sendKeysToElementByJS(WebDriver driver, String locator, String keyToSend, String... restParameter) {
        WebElement element = getElement(driver, locator, restParameter);
        ((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute('value', '" + keyToSend + "')", element);
    }

    protected String getAttributeInDOMByJS(WebDriver driver, String locator, String attributeName, String... restParameter) {
        WebElement element = getElement(driver, locator, restParameter);
        return (String) ((JavascriptExecutor) driver).executeScript(
                "return arguments[0].getAttribute('" + attributeName + "');", element);
    }

    protected void setAttributeInDOM(WebDriver driver, String locator, String attributeName, String attributeValue, String... restParameter) {
        WebElement element = getElement(driver, locator, restParameter);
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].setAttribute('" + attributeName + "', '" + attributeValue + "');", element);
    }

    protected void removeAttributeInDOM(WebDriver driver, String locator, String attributeName, String... restParameter) {
        WebElement element = getElement(driver, locator, restParameter);
        ((JavascriptExecutor) driver).executeScript("arguments[0].removeAttribute('" + attributeName + "');", element);
    }

    protected String getValidationMsg(WebDriver driver, String locator, String... restParameter) {
        WebElement element = getElement(driver, locator, restParameter);
        return (String) ((JavascriptExecutor) driver).executeScript("return arguments[0].validationMessage;", element);
    }

    protected boolean isImageLoaded(WebDriver driver, String locator, String... restParameter) {
        WebElement element = getElement(driver, locator, restParameter);
        return (boolean) ((JavascriptExecutor) driver).executeScript("return arguments[0].complete "
                + "&& typeof arguments[0].naturalWidth != 'undefined' && arguments[0].naturalWidth > 0", element);
    }

}
