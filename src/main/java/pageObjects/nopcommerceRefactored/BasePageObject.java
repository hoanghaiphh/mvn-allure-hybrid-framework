package pageObjects.nopcommerceRefactored;

import commons.BasePageRefactored;
import org.openqa.selenium.WebElement;
import io.qameta.allure.Step;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import testData.UserInfoPOJO;
import utilities.SQLUtils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static pageUIs.nopcommerce.BasePageUI.*;

public class BasePageObject extends BasePageRefactored {

    public BasePageObject(WebDriver driver) {
        super(driver);
    }

    public void clickOnRegisterLink() {
        clickOnElement(getClickableElement(REGISTER_LINK));
    }

    @Step("Verify that My Account link was displayed in header")
    public boolean isMyAccountLinkDisplayed() {
        return isElementDisplayed(MY_ACCOUNT_LINK);
    }

    public void clickOnMyAccountLink() {
        clickOnElement(getClickableElement(MY_ACCOUNT_LINK));
    }

    public void clickOnLoginLink() {
        clickOnElement(getClickableElement(LOGIN_LINK));
    }

    public void clickOnLogoutLink() {
        clickOnElement(getClickableElement(LOGOUT_LINK));
    }

    @Step("Click on {0} link in header")
    public void clickOnHeaderLink(String pageName) {
        clickOnElement(getClickableElement(DYNAMIC_HEADER_LINK_TEXT, pageName));
    }

    // Pattern Object

    @Step("Input value to {0} textbox: {1}")
    public void sendKeysToTextboxByID(String textboxID, String keyToSend) {
        WebElement element = getVisibleElement(DYNAMIC_TEXTBOX_ID, textboxID);
        clearElementText(element);
        sendKeysToElement(element, keyToSend);
    }

    @Step("Get value in {0} textbox")
    public String getValueInTextboxByID(String textboxID) {
        return getElementAttributeValue(getVisibleElement(DYNAMIC_TEXTBOX_ID, textboxID), "value");
    }

    @Step("Click on {0} button")
    public void clickOnButtonByText(String buttonText) {
        clickOnElement(getClickableElement(DYNAMIC_BUTTON_TEXT, buttonText));
    }

    @Step("Select {0}")
    public void selectCheckboxOrRadioByID(String id) {
        selectCheckboxOrRadio(getClickableElement(DYNAMIC_CHECKBOX_RADIO_ID, id));
    }

    @Step("{0} selected")
    public boolean isCheckboxOrRadioByIDSelected(String id) {
        return isElementSelected(getVisibleElement(DYNAMIC_CHECKBOX_RADIO_ID, id));
    }

    @Step("Click on {0} link in header")
    public void clickOnHeaderLinkByText(String headerLinkText) {
        clickOnElement(getClickableElement(DYNAMIC_HEADER_LINK_TEXT, headerLinkText));
    }

    @Step("{0} link displayed in header")
    public boolean isHeaderLinkByTextDisplayed(String headerLinkText) {
        return isElementDisplayed(DYNAMIC_HEADER_LINK_TEXT, headerLinkText);
    }

    public void loginByCookies(Set<Cookie> cookies) {
        setCookies(cookies);
        refreshCurrentPage();
    }

    @Step("Get User Information from test data")
    public Map<String, Object> getUserInformationFromTestData(UserInfoPOJO userInfo) {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("Email", userInfo.getEmailAddress());
        result.put("FirstName", userInfo.getFirstName());
        result.put("LastName", userInfo.getLastName());
        result.put("Company", userInfo.getCompanyName());
        return result;
    }

    @Step("Get Information of User {1} from database")
    public Map<String, Object> getUserInformationFromDatabase(SQLUtils sql, String emailAddress) {
        String sqlQuery = """
                SELECT [Email],[FirstName],[LastName],[Company]
                FROM [nopcommerce].[dbo].[Customer]
                WHERE [Email] = ?;
                """;

        List<Map<String, Object>> result = sql.executeSELECTQuery(sqlQuery, emailAddress);

        if (result.size() == 1) {
            return result.get(0);
        } else if (result.isEmpty()) {
            throw new IllegalStateException("User with email: " + emailAddress + " not found in the database !!!");
        } else {
            String msg = "Found " + result.size() + " emails: " + emailAddress + " !!! Expected exactly 1 record.";
            throw new IllegalStateException(msg);
        }
    }

    @Step("Delete User {1} from database")
    public void deleteUserFromDatabase(SQLUtils sql, String emailAddress) {
        String sqlQuery = "DELETE FROM [nopcommerce].[dbo].[Customer] WHERE [Email] = ?";
        sql.executeDELETEQuery(sqlQuery, emailAddress);
    }

    @Step("Verify that User {1} was deleted from database")
    public boolean isUserDeletedFromDatabase(SQLUtils sql, String emailAddress) {
        String sqlQuery = "SELECT [Email] FROM [nopcommerce].[dbo].[Customer] WHERE [Email] = ?;";
        List<Map<String, Object>> result = sql.executeSELECTQuery(sqlQuery, emailAddress);
        boolean status;
        if (result.isEmpty()) {
            status = true;
        } else {
            status = false;
        }
        return status;
    }

    @Step("Delete null records from database")
    public void deleteNullRecordsFromDatabase(SQLUtils sql) {
        String sqlQuery = """
                DELETE FROM [nopcommerce].[dbo].[Customer]
                WHERE [Email] IS NULL
                  AND [FirstName] IS NULL
                  AND [LastName] IS NULL;
                """;
        sql.executeDELETEQuery(sqlQuery);
    }

}
