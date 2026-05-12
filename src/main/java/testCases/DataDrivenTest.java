package testCases;

import base.BaseClass;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import utilities.DataProviders;
import utilities.ExtentReportManager;
import utilities.LoggerManager;

public class DataDrivenTest extends BaseClass {

    private static final Logger logger= LoggerManager.getLogger(DataDrivenTest.class);

    private LoginPage loginPage;
    private HomePage homePage;

    @BeforeMethod
    public void setUpLogin(){
        loginPage=new LoginPage();
        homePage=new HomePage();
    }

    @Test(dataProvider = "DataDriven",dataProviderClass = DataProviders.class)
    public void loginPageTestDataDriven(String uname,String pwd){
        logger.info(Thread.currentThread().getId());
        ExtentReportManager.getExtentTest().info("Provided Valid Username and Password");
        logger.info("Provide Valid Username and Password");
        loginPage.login(uname,pwd);
        ExtentReportManager.getExtentTest().info("Successfully Logged into Application");
        logger.info("Successfully Logged into Application");
        Assert.assertTrue(homePage.isDashboardDisplayed(),"The Dashboard can able to see in Home Page");
        ExtentReportManager.getExtentTest().info("Dashboard is Displayed");
        logger.info("Dashboard is Displayed");
        homePage.logOut();
        ExtentReportManager.getExtentTest().info("Successfully Logged Out");
        logger.info("Successfully Logged Out");

    }
}
