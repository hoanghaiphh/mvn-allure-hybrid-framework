package pageObjects.orangehrm;

import org.openqa.selenium.WebDriver;
import pageObjects.orangehrm.PIM.AddEmployeePO;
import pageObjects.orangehrm.PIM.EmployeeList.*;

public class PageGenerator {

    public static LoginPO getLoginPage(WebDriver driver) {
        return new LoginPO(driver);
    }

    public static DashboardPO getDashboardPage(WebDriver driver) {
        return new DashboardPO(driver);
    }

    public static PimPO getPIMPage(WebDriver driver) {
        return new PimPO(driver);
    }

    public static AddEmployeePO getAddEmployeePage(WebDriver driver) {
        return new AddEmployeePO(driver);
    }

    public static EmployeeListPO getEmployeeListPage(WebDriver driver) {
        return new EmployeeListPO(driver);
    }

    public static PersonalDetailsPO getPersonalDetailsPage(WebDriver driver) {
        return new PersonalDetailsPO(driver);
    }

    public static ProfilePicturePO getProfilePicturePage(WebDriver driver) {
        return new ProfilePicturePO(driver);
    }

}
