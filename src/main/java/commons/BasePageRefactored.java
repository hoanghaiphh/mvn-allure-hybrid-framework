package commons;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class BasePageRefactored {

    private WebDriver driver;

    protected BasePageRefactored(WebDriver driver) {
        this.driver = driver;
    }

    public void sleepThread(long timeInSeconds) {
        try {
            Thread.sleep(timeInSeconds * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void setImplicitlyWait(long timeInSeconds) {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(timeInSeconds));
    }

    public void navigateToUrl(String url) {
        driver.navigate().to(url);
    }

    public void refreshCurrentPage() {
        driver.navigate().refresh();
    }

    public void backToPreviousPage() {
        driver.navigate().back();
    }

    public void forwardToNextPage() {
        driver.navigate().forward();
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public String getPageSource() {
        return driver.getPageSource();
    }

    public Set<Cookie> getCookies() {
        return driver.manage().getCookies();
    }

    protected void setCookies(Set<Cookie> cookies) {
        cookies.forEach(driver.manage()::addCookie);
    }

    protected void waitForNumberOfWindowsToBe(int numOfWindows) {
        new WebDriverWait(driver, Duration.ofSeconds(GlobalConstants.LONG_TIMEOUT))
                .until(ExpectedConditions.numberOfWindowsToBe(numOfWindows));
    }

    protected void switchToNewWindow(Set<String> oldIds) {
        waitForNumberOfWindowsToBe(oldIds.size() + 1);
        for (String id : driver.getWindowHandles()) {
            if (!oldIds.contains(id)) {
                driver.switchTo().window(id);
                break;
            }
        }
    }

    protected void switchToWindowByTitle(String title) {
        for (String id : driver.getWindowHandles()) {
            driver.switchTo().window(id);
            waitForPageLoad();
            if (getPageTitle().equals(title)) break;
        }
    }

    protected void closeAllWindowsExceptParent(String parentId) {
        driver.getWindowHandles().stream()
                .filter(id -> !id.equals(parentId))
                .forEach(id -> driver.switchTo().window(id).close());
        driver.switchTo().window(parentId);
    }

    protected Alert getPresentAlert() {
        return new WebDriverWait(driver, Duration.ofSeconds(GlobalConstants.LONG_TIMEOUT))
                .until(ExpectedConditions.alertIsPresent());
    }

    protected String getAlertText(Alert alert) {
        return alert.getText();
    }

    protected void acceptAlert(Alert alert) {
        alert.accept();
    }

    protected void dismissAlert(Alert alert) {
        alert.dismiss();
    }

    protected void sendKeyToAlert(Alert alert, String keyToSend) {
        alert.sendKeys(keyToSend);
    }

    /* WebElement */

    private By getByLocator(String locator) {
        String uCase = locator.toUpperCase();
        if (uCase.startsWith("CSS")) return By.cssSelector(locator.substring(4));
        else if (uCase.startsWith("XPATH")) return By.xpath(locator.substring(6));
        else if (uCase.startsWith("ID")) return By.id(locator.substring(3));
        else if (uCase.startsWith("CLASS")) return By.className(locator.substring(6));
        else if (uCase.startsWith("NAME")) return By.name(locator.substring(5));
        else throw new RuntimeException("Locator type is not supported!");
    }

    private By getDynamicLocator(String dynamicLocator, String... restParameter) {
        String locator = String.format(dynamicLocator, (Object[]) restParameter);
        return getByLocator(locator);
    }

    protected WebElement getElement(String locator, String... restParameter) {
        return driver.findElement(getDynamicLocator(locator, restParameter));
    }

    protected List<WebElement> getListElements(String locator, String... restParameter) {
        return driver.findElements(getDynamicLocator(locator, restParameter));
    }

    protected WebElement getVisibleElement(String locator, String... restParameter) {
        return new WebDriverWait(driver, Duration.ofSeconds(GlobalConstants.LONG_TIMEOUT))
                .until(ExpectedConditions.visibilityOfElementLocated(getDynamicLocator(locator, restParameter)));
    }

    protected List<WebElement> getVisibleListElements(String locator, String... restParameter) {
        return new WebDriverWait(driver, Duration.ofSeconds(GlobalConstants.LONG_TIMEOUT))
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(getDynamicLocator(locator, restParameter)));
    }

    protected WebElement getClickableElement(String locator, String... restParameter) {
        return new WebDriverWait(driver, Duration.ofSeconds(GlobalConstants.LONG_TIMEOUT))
                .until(ExpectedConditions.elementToBeClickable(getDynamicLocator(locator, restParameter)));
    }

    protected List<WebElement> getPresentListElements(String locator, String... restParameter) {
        return new WebDriverWait(driver, Duration.ofSeconds(GlobalConstants.LONG_TIMEOUT))
                .until(ExpectedConditions.presenceOfAllElementsLocatedBy(getDynamicLocator(locator, restParameter)));
    }

    protected void waitForElementInvisible(String locator, String... restParameter) {
        setImplicitlyWait(GlobalConstants.SHORT_TIMEOUT);
        new WebDriverWait(driver, Duration.ofSeconds(GlobalConstants.LONG_TIMEOUT))
                .until(ExpectedConditions.invisibilityOfElementLocated(getDynamicLocator(locator, restParameter)));
        setImplicitlyWait(GlobalConstants.LONG_TIMEOUT);
    }

    protected void waitForListElementsInvisible(String locator, String... restParameter) {
        setImplicitlyWait(GlobalConstants.SHORT_TIMEOUT);
        List<WebElement> elementList = getListElements(locator, restParameter);
        setImplicitlyWait(GlobalConstants.LONG_TIMEOUT);
        if (!elementList.isEmpty()) {
            new WebDriverWait(driver, Duration.ofSeconds(GlobalConstants.LONG_TIMEOUT))
                    .until(ExpectedConditions.invisibilityOfAllElements(elementList));
        }
    }

    protected void waitForElementSelected(String locator, String... restParameter) {
        new WebDriverWait(driver, Duration.ofSeconds(GlobalConstants.LONG_TIMEOUT))
                .until(ExpectedConditions.elementToBeSelected(getDynamicLocator(locator, restParameter)));
    }

    protected void waitForElementTextToBe(String locator, String text, String... restParameter) {
        new WebDriverWait(driver, Duration.ofSeconds(GlobalConstants.LONG_TIMEOUT))
                .until(ExpectedConditions.textToBePresentInElementLocated(getDynamicLocator(locator, restParameter), text));
    }

    protected boolean isElementDisplayed(String locator, String... restParameter) {
        setImplicitlyWait(GlobalConstants.SHORT_TIMEOUT);
        List<WebElement> elements = getListElements(locator, restParameter);
        setImplicitlyWait(GlobalConstants.LONG_TIMEOUT);

        if (elements.isEmpty()) return false;
        else return elements.get(0).isDisplayed();
        /*  .get(0) -> IndexOutOfBoundsException if list null (Java 8+)
            .getFirst() -> Null if list null (Java 21+) */
    }

    protected boolean isElementSelected(WebElement webElement) {
        return webElement.isSelected();
    }

    protected boolean isElementEnabled(WebElement webElement) {
        return webElement.isEnabled();
    }

    protected String getElementText(WebElement webElement) {
        return webElement.getText();
    }

    protected List<String> getListElementsText(List<WebElement> webElementList) {
        return webElementList.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
        /*  .collect(Collectors.toList()) -> mutable list (Java 8+)
            .toList() -> immutable list (Java 16+) */
    }

    protected String getElementAttributeValue(WebElement webElement, String attributeName) {
        return webElement.getDomAttribute(attributeName);
    }

    protected String getElementPropertyValue(WebElement webElement, String propertyName) {
        return webElement.getDomProperty(propertyName);
    }

    protected String getElementCssValue(WebElement webElement, String propertyName) {
        return webElement.getCssValue(propertyName);
    }

    protected String getHexFromRGBA(String rgbaValue) {
        return Color.fromString(rgbaValue).asHex();
    }

    protected void clickOnElement(WebElement webElement) {
        webElement.click();
    }

    protected void sendKeysToElement(WebElement webElement, String keyToSend) {
        webElement.sendKeys(keyToSend);
    }

    protected void clearElementText(WebElement webElement) {
        webElement.clear();
    }

    protected void clearListElementsText(List<WebElement> webElementList) {
        webElementList.forEach(WebElement::clear);
    }

    protected void selectCheckboxOrRadio(WebElement webElement) {
        if (!isElementSelected(webElement)) clickOnElement(webElement);
    }

    protected void deselectCheckbox(WebElement webElement) {
        if (isElementSelected(webElement)) clickOnElement(webElement);
    }

    protected void selectOptionInDefaultDropdown(WebElement dropdown, String value) {
        new Select(dropdown).selectByVisibleText(value);
    }

    protected void selectOptionInCustomDropdown(String drdLocator, String optLocator, String value) {
        clickOnElement(getClickableElement(drdLocator));
        for (WebElement option : getPresentListElements(optLocator)) {
            if (option.getText().trim().equals(value)) {
                option.click();
                break;
            }
        }
    }

    protected String getSelectedOptionInDefaultDropdown(WebElement dropdown) {
        return new Select(dropdown).getFirstSelectedOption().getText();
    }

    protected boolean isDefaultDropdownMultiple(WebElement dropdown) {
        return new Select(dropdown).isMultiple();
    }

    protected void switchToAvailableFrame(String frameLocator, String... restParameter) {
        new WebDriverWait(driver, Duration.ofSeconds(GlobalConstants.LONG_TIMEOUT))
                .until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(getDynamicLocator(frameLocator, restParameter)));
    }

    protected void switchToDefaultContent() {
        driver.switchTo().defaultContent();
    }

    /* Interactions Actions */

    protected void hoverOnElement(WebElement webElement) {
        new Actions(driver).moveToElement(webElement).perform();
    }

    protected void clickAndHoldOnElement(WebElement webElement) {
        new Actions(driver).clickAndHold(webElement).perform();
    }

    protected void releaseElement() {
        new Actions(driver).release().perform();
    }

    protected void doubleClickOnElement(WebElement webElement) {
        new Actions(driver).doubleClick(webElement).perform();
    }

    protected void rightClickOnElement(WebElement webElement) {
        new Actions(driver).contextClick(webElement).perform();
    }

    protected void dragAndDropElement(WebElement srcElement, WebElement dstElement) {
        new Actions(driver).dragAndDrop(srcElement, dstElement).perform();
    }

    protected void scrollToElementByActions(WebElement webElement) {
        new Actions(driver).scrollToElement(webElement).perform();
    }

    protected void pressKeyOnElement(WebElement webElement, Keys key) {
        new Actions(driver).sendKeys(webElement, key).perform();
    }

    protected void pressKey(Keys key) {
        new Actions(driver).sendKeys(key).perform();
    }

    /* JavascriptExecutor */

    protected void clickOnElementByJS(WebElement webElement) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", webElement);
    }

    protected void sendKeysToElementByJS(WebElement webElement, String keyToSend) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].setAttribute('value', '" + keyToSend + "')", webElement);
    }

    protected void scrollToBottomPageByJS() {
        ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,document.body.scrollHeight)");
    }

    protected void scrollToElementOnTopByJS(WebElement webElement) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", webElement);
    }

    protected void scrollToElementOnBottomByJS(WebElement webElement) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(false);", webElement);
    }

    protected void highlightElement(WebElement webElement) {
        String curStyle = webElement.getDomAttribute("style");
        String newStyle = "border: 2px solid red; border-style: dashed;";
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].setAttribute('style', arguments[1])", webElement, newStyle);
        sleepThread(1);
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].setAttribute('style', arguments[1])", webElement, curStyle);
    }

    protected void removeAttributeInDOM(WebElement webElement, String attrName) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].removeAttribute('" + attrName + "');", webElement);
    }

    protected void setAttributeInDOM(WebElement webElement, String attrName, String attrValue) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].setAttribute('" + attrName + "', '" + attrValue + "');", webElement);
    }

    protected String getAttributeInDOMByJS(WebElement webElement, String attrName) {
        return (String) ((JavascriptExecutor) driver).executeScript(
                "return arguments[0].getAttribute('" + attrName + "');", webElement);
    }

    protected String getValidationMsg(WebElement webElement) {
        return (String) ((JavascriptExecutor) driver).executeScript(
                "return arguments[0].validationMessage;", webElement);
    }

    protected boolean isImageLoaded(WebElement webElement) {
        String s = "return arguments[0].complete && typeof arguments[0].naturalWidth != 'undefined' && arguments[0].naturalWidth > 0";
        return (boolean) ((JavascriptExecutor) driver).executeScript(s, webElement);
    }

    protected void waitForPageLoad() {
        new WebDriverWait(driver, Duration.ofSeconds(GlobalConstants.LONG_TIMEOUT))
                .until(ExpectedConditions.jsReturnsValue("return document.readyState == 'complete'"));
    }

}
