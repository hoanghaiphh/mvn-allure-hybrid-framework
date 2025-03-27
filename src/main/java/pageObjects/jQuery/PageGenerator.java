package pageObjects.jQuery;

import org.openqa.selenium.WebDriver;

public class PageGenerator {

    public static DataTable1PO getDataTable1Page(WebDriver driver) {
        return new DataTable1PO(driver);
    }

    public static DataTable2PO getDataTable2Page(WebDriver driver) {
        return new DataTable2PO(driver);
    }

    public static UploadFilesPO getUploadFilesPage(WebDriver driver) {
        return new UploadFilesPO(driver);
    }

}
