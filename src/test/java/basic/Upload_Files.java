package basic;

import commons.BaseTest;
import commons.GlobalConstants;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageObjects.jQuery.PageGenerator;
import pageObjects.jQuery.UploadFilesPO;

public class Upload_Files extends BaseTest {
    private WebDriver driver;
    private UploadFilesPO uploadFiles;

    @Parameters("browser")
    @BeforeClass
    public void beforeClass(String browserName) {
        driver = initDriver(browserName);
        configBrowserAndOpenUrl(driver, GlobalConstants.JQUERY_FILE_UPLOAD);
        uploadFiles = PageGenerator.getUploadFilesPage(driver);
    }

    @Test
    public void TC_01() {
        uploadFiles.addFiles("avatar.jpg", "jQuery.txt", "large.jpg", "santa.ico", "snow.png", "topic13.png");
        Assert.assertTrue(uploadFiles.isFileAdded(
                "avatar.jpg", "jQuery.txt", "large.jpg", "santa.ico", "snow.png", "topic13.png"));
        Assert.assertTrue(uploadFiles.isFileAllowedToUpload("avatar.jpg", "snow.png", "topic13.png"));
        Assert.assertFalse(uploadFiles.isFileAllowedToUpload("large.jpg", "jQuery.txt", "santa.ico"));

        uploadFiles.actionForAddedFiles("cancel", "large.jpg", "jQuery.txt", "santa.ico");
        Assert.assertFalse(uploadFiles.isFileAdded("large.jpg", "jQuery.txt", "santa.ico"));

        uploadFiles.actionForAddedFiles("start", "avatar.jpg", "snow.png", "topic13.png");
        Assert.assertTrue(uploadFiles.isFileUploaded("avatar.jpg", "snow.png", "topic13.png"));

        uploadFiles.deleteUploadedFiles("avatar.jpg", "snow.png", "topic13.png");
        uploadFiles.sleepThread(1);
        Assert.assertFalse(uploadFiles.isFileUploaded("avatar.jpg", "snow.png", "topic13.png"));
    }

    @Test
    public void TC_02() {
        uploadFiles.addFiles("avatar.jpg", "jQuery.txt", "large.jpg", "santa.ico", "snow.png", "topic13.png");
        Assert.assertTrue(uploadFiles.isFileAdded(
                "avatar.jpg", "jQuery.txt", "large.jpg", "santa.ico", "snow.png", "topic13.png"));
        Assert.assertTrue(uploadFiles.isFileAllowedToUpload("avatar.jpg", "snow.png", "topic13.png"));
        Assert.assertFalse(uploadFiles.isFileAllowedToUpload("large.jpg", "jQuery.txt", "santa.ico"));

        uploadFiles.actionForAllFiles("start");
        Assert.assertTrue(uploadFiles.isFileUploaded("avatar.jpg", "snow.png", "topic13.png"));

        uploadFiles.actionForAllFiles("cancel");
        Assert.assertFalse(uploadFiles.isFileAdded("large.jpg", "jQuery.txt", "santa.ico"));

        uploadFiles.selectAllFiles();
        uploadFiles.actionForAllFiles("delete");
        uploadFiles.sleepThread(1);
        Assert.assertFalse(uploadFiles.isFileUploaded("avatar.jpg", "snow.png", "topic13.png"));
    }
}
