package pageObjects.jQuery;

import commons.BasePage;
import utilities.CommonUtils;
import org.openqa.selenium.WebDriver;

import java.util.Arrays;
import java.util.stream.Collectors;

import static pageUIs.jQuery.UploadFilesPUI.*;

public class UploadFilesPO extends BasePage {
    private WebDriver driver;

    public UploadFilesPO(WebDriver driver) {
        this.driver = driver;
    }

    public void addFiles(String... fileNames) {
        String filePath = Arrays.stream(fileNames)
                .map(fileName -> CommonUtils.getFileAbsolutePath("uploadFiles", fileName))
                .collect(Collectors.joining("\n"));

        sendKeysToElement(getElement(driver, ADD_FILES_BUTTON), filePath.trim());
    }

    public boolean isFileAdded(String... fileNames) {
        return Arrays.stream(fileNames)
                .allMatch(fileName -> isElementDisplayed(driver, DYNAMIC_ADDED_FILE, fileName));
    }

    public boolean isFileAllowedToUpload(String... fileNames) {
        return Arrays.stream(fileNames)
                .allMatch(fileName -> {
                    boolean isErrMsgDisplayed = isElementDisplayed(driver, DYNAMIC_ERROR_MESSAGE, fileName);
                    boolean isStartBtnEnabled = isElementEnabled(
                            getVisibleElement(driver, DYNAMIC_ADDED_FILE_ACTION_BUTTON, fileName, "start"));
                    return !isErrMsgDisplayed && isStartBtnEnabled;
                });
    }

    public void actionForAddedFiles(String action, String... fileNames) {
        Arrays.stream(fileNames).forEach(fileName ->
                clickOnElement(getClickableElement(driver, DYNAMIC_ADDED_FILE_ACTION_BUTTON, fileName, action)));
    }

    public boolean isFileUploaded(String... fileNames) {
        return Arrays.stream(fileNames)
                .allMatch(fileName -> isElementDisplayed(driver, DYNAMIC_UPLOADED_FILE, fileName));
    }

    public void deleteUploadedFiles(String... fileNames) {
        Arrays.stream(fileNames).forEach(fileName ->
                clickOnElement(getClickableElement(driver, DYNAMIC_UPLOADED_FILE_DELETE_BUTTON, fileName)));
    }

    public void actionForAllFiles(String action) {
        clickOnElement(getClickableElement(driver, DYNAMIC_ALL_FILES_ACTION_BUTTON, action));
    }

    public void selectAllFiles() {
        selectCheckboxOrRadio(getClickableElement(driver, SELECT_ALL_CHECKBOX));
    }

}
