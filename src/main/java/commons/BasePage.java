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

public class BasePage {

    public void sleepThread(long timeInSeconds) {
        try {
            Thread.sleep(timeInSeconds * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void setImplicitlyWait(WebDriver driver, long timeInSeconds) {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(timeInSeconds));
    }

    public void navigateToUrl(WebDriver driver, String url) {
        driver.navigate().to(url);
    }

    public void refreshCurrentPage(WebDriver driver) {
        driver.navigate().refresh();
    }

    public void backToPreviousPage(WebDriver driver) {
        driver.navigate().back();
    }

    public void forwardToNextPage(WebDriver driver) {
        driver.navigate().forward();
    }

    public String getPageTitle(WebDriver driver) {
        return driver.getTitle();
    }

    public String getCurrentUrl(WebDriver driver) {
        return driver.getCurrentUrl();
    }

    public String getPageSource(WebDriver driver) {
        return driver.getPageSource();
    }

    public Set<Cookie> getCookies(WebDriver driver) {
        return driver.manage().getCookies();
    }

    protected void setCookies(WebDriver driver, Set<Cookie> cookies) {
        cookies.forEach(driver.manage()::addCookie);
    }

    protected void waitForNumberOfWindowsToBe(WebDriver driver, int numOfWindows) {
        new WebDriverWait(driver, Duration.ofSeconds(GlobalConstants.LONG_TIMEOUT))
                .until(ExpectedConditions.numberOfWindowsToBe(numOfWindows));
    }

    protected void switchToNewWindow(WebDriver driver, Set<String> oldIds) {
        waitForNumberOfWindowsToBe(driver, oldIds.size() + 1);
        for (String id : driver.getWindowHandles()) {
            if (!oldIds.contains(id)) {
                driver.switchTo().window(id);
                break;
            }
        }
    }

    protected void switchToWindowByTitle(WebDriver driver, String title) {
        for (String id : driver.getWindowHandles()) {
            driver.switchTo().window(id);
            waitForPageLoad(driver);
            if (getPageTitle(driver).equals(title)) break;
        }
    }

    protected void closeAllWindowsExceptParent(WebDriver driver, String parentId) {
        driver.getWindowHandles().stream()
                .filter(id -> !id.equals(parentId))
                .forEach(id -> driver.switchTo().window(id).close());
        driver.switchTo().window(parentId);
    }

    protected Alert getPresentAlert(WebDriver driver) {
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

    protected WebElement getElement(WebDriver driver, String locator, String... restParameter) {
        return driver.findElement(getDynamicLocator(locator, restParameter));
    }

    protected List<WebElement> getListElements(WebDriver driver, String locator, String... restParameter) {
        return driver.findElements(getDynamicLocator(locator, restParameter));
    }

    protected WebElement getVisibleElement(WebDriver driver, String locator, String... restParameter) {
        return new WebDriverWait(driver, Duration.ofSeconds(GlobalConstants.LONG_TIMEOUT))
                .until(ExpectedConditions.visibilityOfElementLocated(getDynamicLocator(locator, restParameter)));
    }

    protected List<WebElement> getVisibleListElements(WebDriver driver, String locator, String... restParameter) {
        return new WebDriverWait(driver, Duration.ofSeconds(GlobalConstants.LONG_TIMEOUT))
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(getDynamicLocator(locator, restParameter)));
    }

    protected WebElement getClickableElement(WebDriver driver, String locator, String... restParameter) {
        return new WebDriverWait(driver, Duration.ofSeconds(GlobalConstants.LONG_TIMEOUT))
                .until(ExpectedConditions.elementToBeClickable(getDynamicLocator(locator, restParameter)));
    }

    protected List<WebElement> getPresentListElements(WebDriver driver, String locator, String... restParameter) {
        return new WebDriverWait(driver, Duration.ofSeconds(GlobalConstants.LONG_TIMEOUT))
                .until(ExpectedConditions.presenceOfAllElementsLocatedBy(getDynamicLocator(locator, restParameter)));
    }

    protected void waitForElementInvisible(WebDriver driver, String locator, String... restParameter) {
        setImplicitlyWait(driver, GlobalConstants.SHORT_TIMEOUT);
        new WebDriverWait(driver, Duration.ofSeconds(GlobalConstants.LONG_TIMEOUT))
                .until(ExpectedConditions.invisibilityOfElementLocated(getDynamicLocator(locator, restParameter)));
        setImplicitlyWait(driver, GlobalConstants.LONG_TIMEOUT);
    }

    protected void waitForListElementsInvisible(WebDriver driver, String locator, String... restParameter) {
        setImplicitlyWait(driver, GlobalConstants.SHORT_TIMEOUT);
        List<WebElement> elementList = getListElements(driver, locator, restParameter);
        setImplicitlyWait(driver, GlobalConstants.LONG_TIMEOUT);
        if (!elementList.isEmpty()) {
            new WebDriverWait(driver, Duration.ofSeconds(GlobalConstants.LONG_TIMEOUT))
                    .until(ExpectedConditions.invisibilityOfAllElements(elementList));
        }
    }

    protected void waitForElementSelected(WebDriver driver, String locator, String... restParameter) {
        new WebDriverWait(driver, Duration.ofSeconds(GlobalConstants.LONG_TIMEOUT))
                .until(ExpectedConditions.elementToBeSelected(getDynamicLocator(locator, restParameter)));
    }

    protected void waitForElementTextToBe(WebDriver driver, String locator, String text, String... restParameter) {
        new WebDriverWait(driver, Duration.ofSeconds(GlobalConstants.LONG_TIMEOUT))
                .until(ExpectedConditions.textToBePresentInElementLocated(getDynamicLocator(locator, restParameter), text));
    }

    protected boolean isElementDisplayed(WebDriver driver, String locator, String... restParameter) {
        setImplicitlyWait(driver, GlobalConstants.SHORT_TIMEOUT);
        List<WebElement> elements = getListElements(driver, locator, restParameter);
        setImplicitlyWait(driver, GlobalConstants.LONG_TIMEOUT);

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

    protected void selectOptionInCustomDropdown(WebDriver driver, String drdLocator, String optLocator, String value) {
        clickOnElement(getClickableElement(driver, drdLocator));
        for (WebElement option : getPresentListElements(driver, optLocator)) {
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

    protected void switchToAvailableFrame(WebDriver driver, String frameLocator, String... restParameter) {
        new WebDriverWait(driver, Duration.ofSeconds(GlobalConstants.LONG_TIMEOUT))
                .until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(getDynamicLocator(frameLocator, restParameter)));
    }

    protected void switchToDefaultContent(WebDriver driver) {
        driver.switchTo().defaultContent();
    }

    /* Interactions Actions */

    protected void hoverOnElement(WebDriver driver, WebElement webElement) {
        new Actions(driver).moveToElement(webElement).perform();
    }

    protected void clickAndHoldOnElement(WebDriver driver, WebElement webElement) {
        new Actions(driver).clickAndHold(webElement).perform();
    }

    protected void releaseElement(WebDriver driver) {
        new Actions(driver).release().perform();
    }

    protected void doubleClickOnElement(WebDriver driver, WebElement webElement) {
        new Actions(driver).doubleClick(webElement).perform();
    }

    protected void rightClickOnElement(WebDriver driver, WebElement webElement) {
        new Actions(driver).contextClick(webElement).perform();
    }

    protected void dragAndDropElement(WebDriver driver, WebElement srcElement, WebElement dstElement) {
        new Actions(driver).dragAndDrop(srcElement, dstElement).perform();
    }

    protected void sendKeyPressToElementByActions(WebDriver driver, WebElement webElement, Keys key) {
        new Actions(driver).sendKeys(webElement, key).perform();
    }

    protected void scrollToElementByActions(WebDriver driver, WebElement webElement) {
        new Actions(driver).scrollToElement(webElement).perform();
    }

    /* JavascriptExecutor */

    protected void clickOnElementByJS(WebDriver driver, WebElement webElement) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", webElement);
    }

    protected void sendKeysToElementByJS(WebDriver driver, WebElement webElement, String keyToSend) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].setAttribute('value', '" + keyToSend + "')", webElement);
    }

    protected void scrollToBottomPageByJS(WebDriver driver) {
        ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,document.body.scrollHeight)");
    }

    protected void scrollToElementOnTopByJS(WebDriver driver, WebElement webElement) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", webElement);
    }

    protected void scrollToElementOnBottomByJS(WebDriver driver, WebElement webElement) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(false);", webElement);
    }

    protected void highlightElement(WebDriver driver, WebElement webElement) {
        String curStyle = webElement.getDomAttribute("style");
        String newStyle = "border: 2px solid red; border-style: dashed;";
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].setAttribute('style', arguments[1])", webElement, newStyle);
        sleepThread(1);
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].setAttribute('style', arguments[1])", webElement, curStyle);
    }

    protected void removeAttributeInDOM(WebDriver driver, WebElement webElement, String attrName) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].removeAttribute('" + attrName + "');", webElement);
    }

    protected void setAttributeInDOM(WebDriver driver, WebElement webElement, String attrName, String attrValue) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].setAttribute('" + attrName + "', '" + attrValue + "');", webElement);
    }

    protected String getAttributeInDOMByJS(WebDriver driver, WebElement webElement, String attrName) {
        return (String) ((JavascriptExecutor) driver).executeScript(
                "return arguments[0].getAttribute('" + attrName + "');", webElement);
    }

    protected String getValidationMsg(WebDriver driver, WebElement webElement) {
        return (String) ((JavascriptExecutor) driver).executeScript(
                "return arguments[0].validationMessage;", webElement);
    }

    protected boolean isImageLoaded(WebDriver driver, WebElement webElement) {
        String s = "return arguments[0].complete && typeof arguments[0].naturalWidth != 'undefined' && arguments[0].naturalWidth > 0";
        return (boolean) ((JavascriptExecutor) driver).executeScript(s, webElement);
    }

    protected void waitForPageLoad(WebDriver driver) {
        new WebDriverWait(driver, Duration.ofSeconds(GlobalConstants.LONG_TIMEOUT))
                .until(ExpectedConditions.jsReturnsValue("return document.readyState == 'complete'"));
    }

}
