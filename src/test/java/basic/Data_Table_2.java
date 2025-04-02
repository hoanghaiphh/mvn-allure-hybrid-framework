package basic;

import commons.BaseTest;
import commons.GlobalConstants;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageObjects.jQuery.DataTable2PO;
import pageObjects.jQuery.PageGenerator;
import utilities.CommonUtils;

public class Data_Table_2 extends BaseTest {
    private WebDriver driver;
    private DataTable2PO dataTable;

    @Parameters("browser")
    @BeforeClass
    public void beforeClass(String browserName) {
        driver = initDriver(browserName);
        openUrl(driver, GlobalConstants.JQUERY_DATA_TABLE_2);
        dataTable = PageGenerator.getDataTable2Page(driver);
    }

    @Test
    public void TC_01() {
        dataTable.loadDataToTable();

        dataTable.enterValueToTextbox("3", "Contact Person", "PERSON 03");
        CommonUtils.sleepInSeconds(1);
        dataTable.enterValueToTextbox("1", "Company", "COMPANY 01");
        CommonUtils.sleepInSeconds(1);

        dataTable.enterAllValueToRow("5", "COMPANY 05", "PERSON 05",
                "Japan", false, "005", "05/25/2020");
        CommonUtils.sleepInSeconds(1);
        dataTable.enterAllValueToRow("2", "COMPANY 02", "PERSON 02",
                "Germany", true, "002", "02/22/2020");
        CommonUtils.sleepInSeconds(1);

        dataTable.rowForAction("5", "Move Up");
        CommonUtils.sleepInSeconds(1);
        dataTable.rowForAction("4", "Move Down");
        CommonUtils.sleepInSeconds(1);
        dataTable.rowForAction("2", "Insert");
        CommonUtils.sleepInSeconds(1);
        dataTable.rowForAction("2", "Remove");
        CommonUtils.sleepInSeconds(1);
    }
}
