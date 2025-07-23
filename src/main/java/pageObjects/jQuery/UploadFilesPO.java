package pageObjects.jQuery;

import commons.BasePage;
import utilities.FileUtils;
import org.openqa.selenium.WebDriver;

import java.util.Arrays;
import java.util.stream.Collectors;

import static pageUIs.jQuery.UploadFilesPUI.*;

public class UploadFilesPO extends BasePage {

    public UploadFilesPO(WebDriver driver) {
        super(driver);
    }

    public void addFiles(String... fileNames) {
        String filePath = Arrays.stream(fileNames)
                .map(fileName -> FileUtils.getFileAbsolutePath("uploadFiles", fileName))
                .collect(Collectors.joining("\n"));

        sendKeysToElement(getElement(ADD_FILES_BUTTON), filePath.trim());
    }

    public boolean isFileAdded(String... fileNames) {
        return Arrays.stream(fileNames)
                .allMatch(fileName -> isElementDisplayed(DYNAMIC_ADDED_FILE, fileName));
    }

    public boolean isFileAllowedToUpload(String... fileNames) {
        return Arrays.stream(fileNames)
                .allMatch(fileName -> {
                    boolean isErrMsgDisplayed = isElementDisplayed(DYNAMIC_ERROR_MESSAGE, fileName);
                    boolean isStartBtnEnabled = isElementEnabled(
                            getVisibleElement(DYNAMIC_ADDED_FILE_ACTION_BUTTON, fileName, "start"));
                    return !isErrMsgDisplayed && isStartBtnEnabled;
                });
    }

    public void actionForAddedFiles(String action, String... fileNames) {
        Arrays.stream(fileNames).forEach(fileName ->
                clickOnElement(getClickableElement(DYNAMIC_ADDED_FILE_ACTION_BUTTON, fileName, action)));
    }

    public boolean isFileUploaded(String... fileNames) {
        return Arrays.stream(fileNames)
                .allMatch(fileName -> isElementDisplayed(DYNAMIC_UPLOADED_FILE, fileName));
    }

    public void deleteUploadedFiles(String... fileNames) {
        Arrays.stream(fileNames).forEach(fileName ->
                clickOnElement(getClickableElement(DYNAMIC_UPLOADED_FILE_DELETE_BUTTON, fileName)));
    }

    public void actionForAllFiles(String action) {
        clickOnElement(getClickableElement(DYNAMIC_ALL_FILES_ACTION_BUTTON, action));
    }

    public void selectAllFiles() {
        selectCheckboxOrRadio(getClickableElement(SELECT_ALL_CHECKBOX));
    }

}
