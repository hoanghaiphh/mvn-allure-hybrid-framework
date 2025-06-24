package pageObjects.orangehrm;

import org.openqa.selenium.WebDriver;

public class DashboardPO extends BasePO {
    private WebDriver driver;

    public DashboardPO(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

}
