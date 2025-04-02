package basic;

import commons.BaseTest;
import commons.GlobalConstants;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageObjects.saucedemo.InventoryPO;
import pageObjects.saucedemo.LoginPO;
import pageObjects.saucedemo.PageGenerator;
import reportConfigs.SoftVerification;

public class Sort_Data extends BaseTest {
    private LoginPO loginPage;
    private InventoryPO inventoryPage;

    private WebDriver driver;
    private SoftVerification soft = SoftVerification.getSoftVerification();

    @Parameters("browser")
    @Test
    public void TC_01_Sort_Data(String browserName) {
        driver = initDriver(browserName);
        openUrl(driver, GlobalConstants.SAUCE_DEMO);

        loginPage = PageGenerator.getLoginPage(driver);

        inventoryPage = loginPage.loginToSystem("standard_user", "secret_sauce");

        inventoryPage.selectSortingCriteria("Name (A to Z)");
        soft.verifyTrue(inventoryPage.isProductsSortedByCriteria("Name (A to Z)"));

        inventoryPage.selectSortingCriteria("Name (Z to A)");
        soft.verifyTrue(inventoryPage.isProductsSortedByCriteria("Name (Z to A)"));

        inventoryPage.selectSortingCriteria("Price (low to high)");
        soft.verifyTrue(inventoryPage.isProductsSortedByCriteria("Price (low to high)"));

        inventoryPage.selectSortingCriteria("Price (high to low)");
        soft.verifyTrue(inventoryPage.isProductsSortedByCriteria("Price (high to low)"));

        inventoryPage.navigateToUrl(driver,
                "https://www.amazon.com/s?k=selenium+java&i=stripbooks-intl-ship&crid=1H7VZTCMM9ZFB&sprefix=selenium+ja%2Cstripbooks-intl-ship%2C333&ref=nb_sb_noss_2");

        inventoryPage.selectSortingCriteria_2("Publication Date");
        soft.verifyTrue(inventoryPage.isBookSortedByPublicationDate());
    }

}
