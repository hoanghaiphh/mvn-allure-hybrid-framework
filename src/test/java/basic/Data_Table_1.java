package basic;

import commons.BaseTest;
import commons.GlobalConstants;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageObjects.jQuery.DataTable1PO;
import pageObjects.jQuery.PageGenerator;

public class Data_Table_1 extends BaseTest {
    private WebDriver driver;
    private DataTable1PO dataTable;

    @Parameters("browser")
    @BeforeClass
    public void beforeClass(String browserName) {
        driver = initDriver(browserName);
        openUrl(driver, GlobalConstants.JQUERY_DATA_TABLE_1);
        dataTable = PageGenerator.getDataTable1Page(driver);
    }

    @Test
    public void TC_01_Paging() {
        dataTable.openPageNumber("5");
        Assert.assertTrue(dataTable.isPageNumberSelected("5"));

        dataTable.openPageNumber("10");
        Assert.assertTrue(dataTable.isPageNumberSelected("10"));

        dataTable.openPageNumber("7");
        Assert.assertTrue(dataTable.isPageNumberSelected("7"));
    }

    @Test
    public void TC_02_Search() {
        dataTable.clearAllSearchTextboxes();
        dataTable.searchValueByHeader("Country", "Eastern Asia");
        Assert.assertTrue(dataTable.isDataRowDisplayed("8235727", "Eastern Asia", "9283824", "17519560"));

        dataTable.clearAllSearchTextboxes();
        dataTable.searchValueByHeader("Females", "147677");
        Assert.assertTrue(dataTable.isDataRowDisplayed("147677", "Ecuador", "152733", "300410"));

        dataTable.clearAllSearchTextboxes();
        dataTable.searchValueByHeader("Males", "97641");
        Assert.assertTrue(dataTable.isDataRowDisplayed("95905", "Dominican Republic", "97641", "193547"));
    }

    @Test
    public void TC_03_Action() {
        dataTable.clearAllSearchTextboxes();
        dataTable.searchValueByHeader("Country", "Dominican Republic");
        Assert.assertTrue(dataTable.isDataRowDisplayed("95905", "Dominican Republic", "97641", "193547"));
        dataTable.removeOrEditDataRow("remove", "Country", "Dominican Republic");
        Assert.assertFalse(dataTable.isDataRowDisplayed("95905", "Dominican Republic", "97641", "193547"));

        dataTable.clearAllSearchTextboxes();
        dataTable.searchValueByHeader("Country", "Eastern Europe");
        Assert.assertTrue(dataTable.isDataRowDisplayed("1279622", "Eastern Europe", "1344642", "2624253"));
        dataTable.removeOrEditDataRow("edit", "Country", "Eastern Europe");
        dataTable.submitChangesAfterEditDataRow();

        dataTable.clearAllSearchTextboxes();
        dataTable.openPageNumber("1");
        Assert.assertTrue(dataTable.isPageNumberSelected("1"));
        dataTable.searchValueByHeader("Females", "12253515");
        Assert.assertTrue(dataTable.isDataRowDisplayed("12253515", "AFRICA", "12599691", "24853148"));
        dataTable.removeOrEditDataRow("remove", "Females", "12253515");
        Assert.assertFalse(dataTable.isDataRowDisplayed("12253515", "AFRICA", "12599691", "24853148"));
    }

    @Test
    public void TC_04_Get_All_Value_Of_Row_Or_Column() {
        dataTable.refreshCurrentPage(driver);

        dataTable.getAllValueOfColumnName("Country");
        dataTable.getAllValueOfRowNumber("3");
//        Assert.assertEquals(dataTable.getAllValueOfColumn("Country").get(2), dataTable.getAllValueOfRow("3").get(1));

        dataTable.getAllValueOfColumnName("Males");
        dataTable.getAllValueOfRowNumber("5");
//        Assert.assertEquals(dataTable.getAllValueOfColumn("Males").get(4), dataTable.getAllValueOfRow("5").get(2));
    }
}
