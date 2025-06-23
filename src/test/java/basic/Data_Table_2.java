package basic;

import commons.BaseTest;
import commons.GlobalConstants;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageObjects.jQuery.DataTable2PO;
import pageObjects.jQuery.PageGenerator;

public class Data_Table_2 extends BaseTest {
    private WebDriver driver;
    private DataTable2PO dataTable;

    @Parameters({"platform", "browserName"})
    @BeforeClass
    public void beforeClass(String platform, String browserName) {
        driver = initDriver(platform, browserName);
        configBrowserAndOpenUrl(driver, GlobalConstants.JQUERY_DATA_TABLE_2);
        dataTable = PageGenerator.getDataTable2Page(driver);
    }

    @Test
    public void TC_01() {
        dataTable.loadDataToTable();

        dataTable.enterValueToTextbox("3", "Contact Person", "PERSON 03");
        dataTable.sleepThread(1);
        dataTable.enterValueToTextbox("1", "Company", "COMPANY 01");
        dataTable.sleepThread(1);

        dataTable.enterAllValueToRow("5", "COMPANY 05", "PERSON 05",
                "Japan", false, "005", "05/25/2020");
        dataTable.sleepThread(1);
        dataTable.enterAllValueToRow("2", "COMPANY 02", "PERSON 02",
                "Germany", true, "002", "02/22/2020");
        dataTable.sleepThread(1);

        dataTable.rowForAction("5", "Move Up");
        dataTable.sleepThread(1);
        dataTable.rowForAction("4", "Move Down");
        dataTable.sleepThread(1);
        dataTable.rowForAction("2", "Insert");
        dataTable.sleepThread(1);
        dataTable.rowForAction("2", "Remove");
        dataTable.sleepThread(1);
    }
}
