package testCases;

import base.BaseClass;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import utilities.Constants;
import utilities.ExtentReportManager;
import utilities.LoggerManager;

public class HomePageTest extends BaseClass {
    private static final Logger logger = LoggerManager.getLogger(HomePageTest.class);
    private LoginPage loginPage;
    private HomePage homePage;

    @BeforeMethod
    public void setUpLogin(){
        loginPage=new LoginPage();
        homePage=new HomePage();
    }

    @Test
    public void verifyHomePageDisplayed(){
        logger.info(Thread.currentThread().getId());
        logger.info("Provide Valid Username and Password");
        ExtentReportManager.getExtentTest().info("Provided Valid Username and Password");
        loginPage.login(Constants.USERNAME,Constants.PASSWORD);
        ExtentReportManager.getExtentTest().info("Successfully Logged into Application");
        logger.info("Successfully Logged into Application");
        Assert.assertTrue(homePage.isDashboardDisplayed(),"The Dashboard can able to see in Home Page");
        ExtentReportManager.getExtentTest().info("Dashboard is Displayed");
        logger.info("Dashboard is Displayed");
    }
}
