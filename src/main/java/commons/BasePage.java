package commons;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import utilities.CommonUtils;

import java.time.Duration;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class BasePage {

    /* Browser */

    public void refreshCurrentPage(WebDriver driver) {
        driver.navigate().refresh();
    }

    public void navigateToUrl(WebDriver driver, String url) {
        driver.navigate().to(url);
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

    private Alert waitForAlertPresence(WebDriver driver) {
        return new WebDriverWait(driver, Duration.ofSeconds(GlobalConstants.LONG_TIMEOUT))
                .until(ExpectedConditions.alertIsPresent());
    }

    public void acceptAlert(WebDriver driver) {
        waitForAlertPresence(driver).accept();
    }

    public void dismissAlert(WebDriver driver) {
        waitForAlertPresence(driver).dismiss();
    }

    public void sendKeyToAlert(WebDriver driver, String keyToSend) {
        waitForAlertPresence(driver).sendKeys(keyToSend);
    }

    public String getAlertText(WebDriver driver) {
        return waitForAlertPresence(driver).getText();
    }

    public Set<Cookie> getCookies(WebDriver driver) {
        return driver.manage().getCookies();
    }

    protected void setCookies(WebDriver driver, Set<Cookie> cookies) {
        cookies.forEach(driver.manage()::addCookie);
    }

    protected void switchToNewWindow(WebDriver driver, Set<String> oldIds) {
        String newId = driver.getWindowHandles().stream()
                .filter(id -> !oldIds.contains(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No new window found!"));
        driver.switchTo().window(newId);
    }

    protected void switchToWindowByTitle(WebDriver driver, String title) {
        for (String id : driver.getWindowHandles()) {
            driver.switchTo().window(id);
            if (getPageTitle(driver).equals(title)) break;
        }
    }

    protected void closeAllWindowsExceptParent(WebDriver driver, String parentId) {
        for (String id : driver.getWindowHandles()) {
            if (!id.equals(parentId)) driver.switchTo().window(id).close();
        }
        driver.switchTo().window(parentId);
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

    private WebElement getElement(WebDriver driver, String locator, String... restParameter) {
        By byLocator = getDynamicLocator(locator, restParameter);
        return driver.findElement(byLocator);
    }

    protected List<WebElement> getListElements(WebDriver driver, String locator, String... restParameter) {
        By byLocator = getDynamicLocator(locator, restParameter);
        return driver.findElements(byLocator);
    }

    protected String getElementText(WebDriver driver, String locator, String... restParameter) {
        return getElement(driver, locator, restParameter).getText();
    }

    protected List<String> getListElementsText(WebDriver driver, String locator, String... restParameter) {
        return getListElements(driver, locator, restParameter)
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
        /*  .collect(Collectors.toList()) -> mutable list (Java 8+)
            .toList() -> immutable list (Java 16+) */
    }

    protected String getAttributeValue(WebDriver driver, String locator, String attributeName, String... restParameter) {
        return getElement(driver, locator, restParameter).getDomAttribute(attributeName);
    }

    protected String getCssValue(WebDriver driver, String locator, String propertyName, String... restParameter) {
        return getElement(driver, locator, restParameter).getCssValue(propertyName);
    }

    protected String getSelectedOptionInDropdown(WebDriver driver, String locator, String... restParameter) {
        WebElement element = getElement(driver, locator, restParameter);
        return new Select(element).getFirstSelectedOption().getText();
    }

    protected String getHexFromRGBA(String rgbaValue) {
        return Color.fromString(rgbaValue).asHex();
    }

    protected void clickOnElement(WebDriver driver, String locator, String... restParameter) {
        getElement(driver, locator, restParameter).click();
    }

    protected void sendKeysToElement(WebDriver driver, String locator, String keyToSend, String... restParameter) {
        getElement(driver, locator, restParameter).sendKeys(keyToSend);
    }

    protected void clearElementText(WebDriver driver, String locator, String... restParameter) {
        getElement(driver, locator, restParameter).clear();
    }

    protected void clearListElementsText(WebDriver driver, String locator, String... restParameter) {
        getListElements(driver, locator, restParameter).forEach(WebElement::clear);
    }

    protected void selectCheckboxOrRadio(WebDriver driver, String locator, String... restParameter) {
        if (!isElementSelected(driver, locator, restParameter)) clickOnElement(driver, locator, restParameter);
    }

    protected void deselectCheckbox(WebDriver driver, String locator, String... restParameter) {
        if (isElementSelected(driver, locator, restParameter)) clickOnElement(driver, locator, restParameter);
    }

    protected void selectOptionInDropdown(WebDriver driver, String locator, String option, String... restParameter) {
        WebElement element = getElement(driver, locator, restParameter);
        new Select(element).selectByVisibleText(option);
    }

    protected void selectOptionInCustomDropdown(WebDriver driver, String dropdownLocator, String optLocator, String value) {
        clickOnElement(driver, dropdownLocator);
        CommonUtils.sleepInSeconds(1);

        List<WebElement> allOptions = new WebDriverWait(driver, Duration.ofSeconds(GlobalConstants.LONG_TIMEOUT))
                .until(ExpectedConditions.presenceOfAllElementsLocatedBy(getDynamicLocator(optLocator)));
        for (WebElement option : allOptions) {
            if (option.getText().trim().equals(value)) {
                option.click();
                CommonUtils.sleepInSeconds(1);
                break;
            }
        }
    }

    protected void switchToFrame(WebDriver driver, String locator, String... restParameter) {
        driver.switchTo().frame(getElement(driver, locator, restParameter));
    }

    protected void switchToDefaultContent(WebDriver driver) {
        driver.switchTo().defaultContent();
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

    protected boolean isElementSelected(WebDriver driver, String locator, String... restParameter) {
        return getElement(driver, locator, restParameter).isSelected();
    }

    protected boolean isElementEnabled(WebDriver driver, String locator, String... restParameter) {
        return getElement(driver, locator, restParameter).isEnabled();
    }

    protected boolean isDropdownMultiple(WebDriver driver, String locator, String... restParameter) {
        WebElement element = getElement(driver, locator, restParameter);
        return new Select(element).isMultiple();
    }


    /* Wait */

    private void setImplicitlyWait(WebDriver driver, long timeInSeconds) {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(timeInSeconds));
    }

    protected void waitForElementVisible(WebDriver driver, String locator, String... restParameter) {
        By byLocator = getDynamicLocator(locator, restParameter);
        new WebDriverWait(driver, Duration.ofSeconds(GlobalConstants.LONG_TIMEOUT))
                .until(ExpectedConditions.visibilityOfElementLocated(byLocator));
    }

    protected void waitForAllElementsVisible(WebDriver driver, String locator, String... restParameter) {
        By byLocator = getDynamicLocator(locator, restParameter);
        new WebDriverWait(driver, Duration.ofSeconds(GlobalConstants.LONG_TIMEOUT))
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(byLocator));
    }

    protected void waitForElementInvisible(WebDriver driver, String locator, String... restParameter) {
        setImplicitlyWait(driver, GlobalConstants.SHORT_TIMEOUT);
        By byLocator = getDynamicLocator(locator, restParameter);
        new WebDriverWait(driver, Duration.ofSeconds(GlobalConstants.LONG_TIMEOUT))
                .until(ExpectedConditions.invisibilityOfElementLocated(byLocator));
        setImplicitlyWait(driver, GlobalConstants.LONG_TIMEOUT);
    }

    protected void waitForElementClickable(WebDriver driver, String locator, String... restParameter) {
        By byLocator = getDynamicLocator(locator, restParameter);
        new WebDriverWait(driver, Duration.ofSeconds(GlobalConstants.LONG_TIMEOUT))
                .until(ExpectedConditions.elementToBeClickable(byLocator));
    }

    protected void waitForElementSelected(WebDriver driver, String locator, String... restParameter) {
        By byLocator = getDynamicLocator(locator, restParameter);
        new WebDriverWait(driver, Duration.ofSeconds(GlobalConstants.LONG_TIMEOUT))
                .until(ExpectedConditions.elementToBeSelected(byLocator));
    }


    /* Actions */

    protected void hoverOnElement(WebDriver driver, String locator, String... restParameter) {
        WebElement element = getElement(driver, locator, restParameter);
        new Actions(driver).moveToElement(element).perform();
    }

    protected void clickAndHoldOnElement(WebDriver driver, String locator, String... restParameter) {
        WebElement element = getElement(driver, locator, restParameter);
        new Actions(driver).clickAndHold(element).perform();
    }

    protected void releaseElement(WebDriver driver) {
        new Actions(driver).release().perform();
    }

    protected void doubleClickOnElement(WebDriver driver, String locator, String... restParameter) {
        WebElement element = getElement(driver, locator, restParameter);
        new Actions(driver).doubleClick(element).perform();
    }

    protected void rightClickOnElement(WebDriver driver, String locator, String... restParameter) {
        WebElement element = getElement(driver, locator, restParameter);
        new Actions(driver).contextClick(element).perform();
    }

    protected void dragAndDropElement(WebDriver driver, String sourceLocator, String targetLocator) {
        WebElement source = getElement(driver, sourceLocator);
        WebElement target = getElement(driver, targetLocator);
        new Actions(driver).dragAndDrop(source, target).perform();
    }

    protected void sendKeyPressToElementByActions(WebDriver driver, String locator, Keys key, String... restParameter) {
        WebElement element = getElement(driver, locator, restParameter);
        new Actions(driver).sendKeys(element, key).perform();
    }

    protected void scrollToElementByActions(WebDriver driver, String locator, String... restParameter) {
        WebElement element = getElement(driver, locator, restParameter);
        new Actions(driver).scrollToElement(element).perform();
    }


    /* JavascriptExecutor */

    protected void clickOnElementByJS(WebDriver driver, String locator, String... restParameter) {
        WebElement element = getElement(driver, locator, restParameter);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    protected void sendKeysToElementByJS(WebDriver driver, String locator, String keyToSend, String... restParameter) {
        WebElement element = getElement(driver, locator, restParameter);
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].setAttribute('value', '" + keyToSend + "')", element);
    }

    protected void scrollToBottomPageByJS(WebDriver driver) {
        ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,document.body.scrollHeight)");
    }

    protected void scrollToElementOnTopByJS(WebDriver driver, String locator, String... restParameter) {
        WebElement element = getElement(driver, locator, restParameter);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    protected void scrollToElementOnBottomByJS(WebDriver driver, String locator, String... restParameter) {
        WebElement element = getElement(driver, locator, restParameter);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(false);", element);
    }

    protected void highlightElement(WebDriver driver, String locator, String... restParameter) {
        WebElement element = getElement(driver, locator, restParameter);
        String originalStyle = element.getDomAttribute("style");
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].setAttribute('style', arguments[1])", element, "border: 2px solid red; border-style: dashed;");
        CommonUtils.sleepInSeconds(1);
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].setAttribute('style', arguments[1])", element, originalStyle);
    }

    protected void removeAttributeInDOM(WebDriver driver, String locator, String attrName, String... restParameter) {
        WebElement element = getElement(driver, locator, restParameter);
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].removeAttribute('" + attrName + "');", element);
    }

    protected void setAttributeInDOM(WebDriver driver, String locator, String attrName, String attrValue, String... restParameter) {
        WebElement element = getElement(driver, locator, restParameter);
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].setAttribute('" + attrName + "', '" + attrValue + "');", element);
    }

    protected String getAttributeInDOMByJS(WebDriver driver, String locator, String attrName, String... restParameter) {
        WebElement element = getElement(driver, locator, restParameter);
        return (String) ((JavascriptExecutor) driver).executeScript(
                "return arguments[0].getAttribute('" + attrName + "');", element);
    }

    protected String getValidationMsg(WebDriver driver, String locator, String... restParameter) {
        WebElement element = getElement(driver, locator, restParameter);
        return (String) ((JavascriptExecutor) driver).executeScript(
                "return arguments[0].validationMessage;", element);
    }

    protected boolean isImageLoaded(WebDriver driver, String locator, String... restParameter) {
        WebElement element = getElement(driver, locator, restParameter);
        return (boolean) ((JavascriptExecutor) driver).executeScript(
                "return arguments[0].complete && typeof arguments[0].naturalWidth != 'undefined' && arguments[0].naturalWidth > 0", element);
    }

}
