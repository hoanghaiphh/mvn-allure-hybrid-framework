package pageObjects.jQuery;

import commons.BasePage;
import org.openqa.selenium.WebDriver;
import pageUIs.jQuery.UploadFilesPageUI;
import utilities.CommonUtils;

import java.util.Arrays;
import java.util.stream.Collectors;

public class UploadFilesPO extends BasePage {
    private WebDriver driver;

    public UploadFilesPO(WebDriver driver) {
        this.driver = driver;
    }

    public void addFiles(String... fileNames) {
        String filePath = Arrays.stream(fileNames)
                .map(fileName -> CommonUtils.getFileAbsolutePath("uploadFiles", fileName))
                .collect(Collectors.joining("\n"));

        sendKeysToElement(driver, UploadFilesPageUI.ADD_FILES_BUTTON, filePath.trim());
    }

    public boolean isFileAdded(String... fileNames) {
        return Arrays.stream(fileNames)
                .allMatch(fileName -> isElementDisplayed(driver, UploadFilesPageUI.DYNAMIC_ADDED_FILE, fileName));
    }

    public boolean isFileAllowedToUpload(String... fileNames) {
        return Arrays.stream(fileNames)
                .allMatch(fileName -> {
                    boolean isErrMsgDisplayed = isElementDisplayed(driver,
                            UploadFilesPageUI.DYNAMIC_ERROR_MESSAGE, fileName);
                    boolean isStartBtnEnabled = isElementEnabled(driver,
                            UploadFilesPageUI.DYNAMIC_ADDED_FILE_ACTION_BUTTON, fileName, "start");
                    return !isErrMsgDisplayed && isStartBtnEnabled;
                });
    }

    public void actionForAddedFiles(String action, String... fileNames) {
        Arrays.stream(fileNames)
                .forEach(fileName -> {
                    waitForElementClickable(driver, UploadFilesPageUI.DYNAMIC_ADDED_FILE_ACTION_BUTTON, fileName, action);
                    clickOnElement(driver, UploadFilesPageUI.DYNAMIC_ADDED_FILE_ACTION_BUTTON, fileName, action);
                });
    }

    public boolean isFileUploaded(String... fileNames) {
        return Arrays.stream(fileNames)
                .allMatch(fileName -> isElementDisplayed(driver, UploadFilesPageUI.DYNAMIC_UPLOADED_FILE, fileName));
    }

    public void deleteUploadedFiles(String... fileNames) {
        Arrays.stream(fileNames)
                .forEach(fileName -> {
                    waitForElementClickable(driver, UploadFilesPageUI.DYNAMIC_UPLOADED_FILE_DELETE_BUTTON, fileName);
                    clickOnElement(driver, UploadFilesPageUI.DYNAMIC_UPLOADED_FILE_DELETE_BUTTON, fileName);
                });
    }

    public void actionForAllFiles(String action) {
        waitForElementClickable(driver, UploadFilesPageUI.DYNAMIC_ALL_FILES_ACTION_BUTTON, action);
        clickOnElement(driver, UploadFilesPageUI.DYNAMIC_ALL_FILES_ACTION_BUTTON, action);
    }

    public void selectAllFiles() {
        waitForElementClickable(driver, UploadFilesPageUI.SELECT_ALL_CHECKBOX);
        selectCheckboxOrRadio(driver, UploadFilesPageUI.SELECT_ALL_CHECKBOX);
    }

}
