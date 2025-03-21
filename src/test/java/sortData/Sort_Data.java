package sortData;

import commons.BaseTest;
import commons.GlobalConstants;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageObjects.saucedemo.InventoryPO;
import pageObjects.saucedemo.LoginPO;
import pageObjects.saucedemo.PageGenerator;

public class Sort_Data extends BaseTest {
    private LoginPO loginPage;
    private InventoryPO inventoryPage;

    @Parameters("browser")
    @Test
    public void TC_01_Sort_By_Name_And_Price(String browserName) {
        driver = initDriverAndOpenUrl(browserName, GlobalConstants.SAUCE_DEMO);
        loginPage = PageGenerator.getLoginPage(driver);

        inventoryPage = loginPage.loginToSystem("standard_user", "secret_sauce");

        inventoryPage.selectSortingCriteria("Name (A to Z)");
        verifyTrue(inventoryPage.isProductsSortedByCriteria("Name (A to Z)"));

        inventoryPage.selectSortingCriteria("Name (Z to A)");
        verifyTrue(inventoryPage.isProductsSortedByCriteria("Name (Z to A)"));

        inventoryPage.selectSortingCriteria("Price (low to high)");
        verifyTrue(inventoryPage.isProductsSortedByCriteria("Price (low to high)"));

        inventoryPage.selectSortingCriteria("Price (high to low)");
        verifyTrue(inventoryPage.isProductsSortedByCriteria("Price (high to low)"));
    }

    @Parameters("browser")
    @Test
    public void TC_02_Sort_By_Date(String browserName) {
        driver.get("https://www.amazon.com/s?k=selenium+java&i=stripbooks-intl-ship&crid=1H7VZTCMM9ZFB&sprefix=selenium+ja%2Cstripbooks-intl-ship%2C333&ref=nb_sb_noss_2");

        inventoryPage.selectSortingCriteria_2("Publication Date");
        verifyTrue(inventoryPage.isBookSortedByPublicationDate());
    }
}
