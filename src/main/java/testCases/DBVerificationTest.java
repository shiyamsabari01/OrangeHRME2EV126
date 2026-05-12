package testCases;

import base.BaseClass;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.PIMPage;
import utilities.*;

import java.util.Map;

public class DBVerificationTest extends BaseClass {
    private static final Logger logger = LoggerManager.getLogger(HomePageTest.class);
    private LoginPage loginPage;
    private HomePage homePage;
    private PIMPage pimPage;

    @BeforeMethod
    public void setUpLogin(){
        loginPage=new LoginPage();
        homePage=new HomePage();
    }

    @Test(dataProvider = "empVerification",dataProviderClass = DataProviders.class)
    public void verifyEmpDetailsDB(String empID,String empName){
        ExtentReportManager.getExtentTest().info("Provided Valid Username and Password");
        loginPage.login(Constants.USERNAME,Constants.PASSWORD);
        homePage.NavigatePIMPage();
        ExtentReportManager.getExtentTest().info("Navigated to PIM Page");
        pimPage=new PIMPage();
        pimPage.empDetails(empName);
        ExtentReportManager.getExtentTest().info("Get emp details from DB");
        String emp_Id=empID;

        Map<String,String> empDetails= DBConnection.getEmpDetails(emp_Id);
        String firstName=empDetails.get("firstName");
        String lastName=empDetails.get("lastName");
        String middleName=empDetails.get("middleName");

        String firstNameAndLastName=(firstName+" "+middleName).trim();
        ExtentReportManager.getExtentTest().info("Verify the First and Middle Name");
        String firstNameAndMiddle= pimPage.verifyFirstAndMiddleName();
        Assert.assertEquals(firstNameAndMiddle,firstNameAndLastName);

        ExtentReportManager.getExtentTest().info("Verify the Last Name");
        String Lname= pimPage.verifyLastName();
        Assert.assertEquals(Lname,lastName);

        ExtentReportManager.getExtentTest().info("DB Verification is completed");
    }
}
