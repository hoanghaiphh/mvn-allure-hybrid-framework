package sqlQuery;

import commons.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageObjects.orangehrm.DashboardPO;
import pageObjects.orangehrm.LoginPO;
import pageObjects.orangehrm.PIM.AddEmployeePO;
import pageObjects.orangehrm.PIM.EmployeeList.*;
import pageObjects.orangehrm.PageGenerator;
import pageObjects.orangehrm.PimPO;
import testData.EmployeeInfo;
import utilities.DataGenerator;
import utilities.FileUtils;
import utilities.SQLUtils;
import utilities.PropertiesConfig;

@Feature("PIM.Employee")
public class OrangeHRM extends BaseTest {
    private LoginPO loginPage;
    private DashboardPO dashboardPage;
    private PimPO pimPage;
    private AddEmployeePO addEmployeePage;
    private PersonalDetailsPO personalDetailsPage;
    private ProfilePicturePO profilePicturePage;

    private PropertiesConfig env;
    private WebDriver driver;
    private SQLUtils sql;

    private EmployeeInfo empInfo;

    private static final Object ID_GENERATION_LOCK = new Object();

    @Parameters({"platform", "browserName"})
    @BeforeClass
    public void beforeClass(String platform, String browserName) {
        env = PropertiesConfig.getEnvironmentProperties();
        driver = initDriver(platform, browserName);
        configBrowserAndOpenUrl(driver, env.getPropertyValue("app.Url"));
        sql = initSQLConnection(env);

        empInfo = EmployeeInfo.getEmployeeInfo();
        DataGenerator data = DataGenerator.create();
        empInfo.setFirstName(data.getFirstname());
        empInfo.setMiddleName("");
        empInfo.setLastName(data.getLastname());
        empInfo.setPassword(data.getPassword() + "abc");
        empInfo.setDriverLicense(data.getDriverLicense());
        empInfo.setLicenseExpiryDate(data.getDateWithinYearRange(2030, 2040));
        empInfo.setNationality(data.getNationality());
        empInfo.setMaritalStatus(data.getMaritalStatus());
        empInfo.setDateOfBirth(data.getDateWithinYearRange(1970, 2010));
        empInfo.setGender(data.getGender());
        empInfo.setProfilePicture(
                FileUtils.getFileAbsolutePath("uploadFiles", "profilePicture1.jpg"));

        loginPage = PageGenerator.getLoginPage(driver);
        loginPage.sendKeysToUsernameTextbox(env.getPropertyValue("app.Username"));
        loginPage.sendKeysToPasswordTextbox(env.getPropertyValue("app.Password"));
        dashboardPage = loginPage.clickOnLoginButton();
    }

    @Description("Employee_01_Add_Employee")
    @Severity(SeverityLevel.NORMAL)
    @Test
    public void Employee_01_Add_Employee() {
        synchronized (ID_GENERATION_LOCK) {
            pimPage = dashboardPage.clickOnSidePanelLink("PIM");
            pimPage.clickOnTopBarLink("Add Employee");
            addEmployeePage = PageGenerator.getAddEmployeePage(driver);

            String id = addEmployeePage.getValueOfEmployeeIdTextbox();
            empInfo.setEmployeeId(id);

            addEmployeePage.inputEmployeeInformationIntoFields(empInfo);
            addEmployeePage.clickOnAddEmployeeSaveButton();
            Assert.assertEquals(addEmployeePage.getToastMessage(), "Successfully Saved");
            addEmployeePage.waitForLoading(); // loading spinner while saving information
        }

        personalDetailsPage = PageGenerator.getPersonalDetailsPage(driver);
        personalDetailsPage.waitForLoading(); // loading spinner while loading Personal Details page components
        String employeeName = empInfo.getFirstName() + " " + empInfo.getLastName();
        Assert.assertEquals(personalDetailsPage.getEmployeeName(), employeeName);
    }

    @Description("Employee_02_Change_Profile_Picture")
    @Severity(SeverityLevel.MINOR)
    @Test
    public void Employee_02_Change_Profile_Picture() {
        personalDetailsPage.clickOnProfilePictureImage();
        profilePicturePage = PageGenerator.getProfilePicturePage(driver);
        profilePicturePage.waitForLoading();

        Dimension oldDimension = profilePicturePage.getNaturalDimensionOfProfilePicture();

        profilePicturePage.uploadProfilePicture(empInfo.getProfilePicture());
        profilePicturePage.clickOnChangeProfilePictureSaveButton();
        Assert.assertEquals(profilePicturePage.getToastMessage(), "Successfully Updated");
        profilePicturePage.waitForLoading();

        Dimension newDimension = profilePicturePage.getNaturalDimensionOfProfilePicture();
        Assert.assertNotEquals(oldDimension, newDimension);
    }

    @Description("Employee_03_Personal_Details")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void Employee_03_Personal_Details() {
        profilePicturePage.clickOnTabsLink("Personal Details");
        personalDetailsPage = PageGenerator.getPersonalDetailsPage(driver);

        personalDetailsPage.inputEmployeeAdditionalInformationIntoFields(empInfo);
        personalDetailsPage.clickOnPersonalDetailsSaveButton();
        Assert.assertEquals(addEmployeePage.getToastMessage(), "Successfully Updated");
        addEmployeePage.waitForLoading();

        var uiData = personalDetailsPage.getEmployeeInformationFromUI();
        var testData = personalDetailsPage.getEmployeeInformationFromTestData(empInfo);
        var sqlData = personalDetailsPage.getEmployeeInformationFromDatabase(sql,
                personalDetailsPage.getValueOfEmployeeIdTextbox());
        Assert.assertEquals(uiData, testData);
        Assert.assertEquals(sqlData, uiData);

        DataGenerator data = DataGenerator.create();
        String newFirstName = data.getFirstname();
        String newLastName = data.getLastname();
        empInfo.setFirstName(newFirstName);
        empInfo.setLastName(newLastName);

        personalDetailsPage.sendKeysToFirstNameTextbox(empInfo.getFirstName());
        personalDetailsPage.sendKeysToLastNameTextbox(empInfo.getLastName());
        personalDetailsPage.clickOnPersonalDetailsSaveButton();
        Assert.assertEquals(addEmployeePage.getToastMessage(), "Successfully Updated");
        personalDetailsPage.waitForLoading();

        Assert.assertEquals(personalDetailsPage.getValueOfFirstNameTextbox(), empInfo.getFirstName());
        Assert.assertEquals(personalDetailsPage.getValueOfLastNameTextbox(), empInfo.getLastName());

        personalDetailsPage.refreshCurrentPage(driver);
        personalDetailsPage.waitForLoading();

        String newEmployeeName = newFirstName + " " + newLastName;
        Assert.assertEquals(personalDetailsPage.getEmployeeName(), newEmployeeName);
    }

}
