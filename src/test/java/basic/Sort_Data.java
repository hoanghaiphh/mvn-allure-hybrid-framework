package basic;

import commons.BaseTest;
import commons.GlobalConstants;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageObjects.saucedemo.InventoryPO;
import pageObjects.saucedemo.LoginPO;
import reportConfigs.SoftVerification;

public class Sort_Data extends BaseTest {
    private LoginPO loginPage;
    private InventoryPO inventoryPage;

    private static final SoftVerification VERIFY = SoftVerification.getSoftVerification();

    @Parameters({"platform", "browserName"})
    @BeforeClass
    public void beforeClass(String platform, String browserName) {
        initDriver(platform, browserName);
        configBrowserAndOpenUrl(GlobalConstants.SAUCE_DEMO);
        loginPage = getPage(LoginPO.class);
    }

    @Test
    public void TC_01() {
        loginPage.loginToSystem("standard_user", "secret_sauce");
        inventoryPage = getPage(InventoryPO.class);

        inventoryPage.selectSortingCriteria("Name (A to Z)");
        VERIFY.verifyTrue(inventoryPage.isProductsSortedByCriteria("Name (A to Z)"));

        inventoryPage.selectSortingCriteria("Name (Z to A)");
        VERIFY.verifyTrue(inventoryPage.isProductsSortedByCriteria("Name (Z to A)"));

        inventoryPage.selectSortingCriteria("Price (low to high)");
        VERIFY.verifyTrue(inventoryPage.isProductsSortedByCriteria("Price (low to high)"));

        inventoryPage.selectSortingCriteria("Price (high to low)");
        VERIFY.verifyTrue(inventoryPage.isProductsSortedByCriteria("Price (high to low)"));
    }

    @Test
    public void TC_02() {
        inventoryPage.navigateToUrl("https://www.amazon.com/s?k=selenium+java&i=stripbooks-intl-ship&crid=1H7VZTCMM9ZFB&sprefix=selenium+ja%2Cstripbooks-intl-ship%2C333&ref=nb_sb_noss_2");

        inventoryPage.sortBooksByPublicationDate();
        VERIFY.verifyTrue(inventoryPage.isBookSortedByPublicationDate());
    }

}
