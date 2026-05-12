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

public class LoginPageTest extends BaseClass {
    private static final Logger logger= LoggerManager.getLogger(LoginPageTest.class);
    private LoginPage loginPage;
    private HomePage homePage;

    @BeforeMethod
    public void setUpLogin(){
        loginPage=new LoginPage();
        homePage=new HomePage();
    }

    @Test
    public void loginPageTest(){
        logger.info(Thread.currentThread().getId());
        ExtentReportManager.getExtentTest().info("Provided Valid Username and Password");
        logger.info("Provide Valid Username and Password");
        loginPage.login(Constants.USERNAME,Constants.PASSWORD);
        ExtentReportManager.getExtentTest().info("Successfully Logged into Application");
        logger.info("Successfully Logged into Application");
        Assert.assertTrue(homePage.isDashboardDisplayed(),"The Dashboard can able to see in Home Page");
        ExtentReportManager.getExtentTest().info("Dashboard is Displayed");
        logger.info("Dashboard is Displayed");
        homePage.logOut();
        ExtentReportManager.getExtentTest().info("Successfully Logged Out");
        logger.info("Successfully Logged Out");

    }

    @Test
    public void invalidLogin(){
        logger.info(Thread.currentThread().getId());
        logger.info("Provide Invalid Username and Password");
        ExtentReportManager.getExtentTest().info("Provided Invalid Username and Password");
        loginPage.login("Admin","Sabari");
        boolean actualError=loginPage.isErrorMsgDisplayed();
        Assert.assertTrue(actualError,"Invalid credentials");
        logger.info("Error message is displayed");
        ExtentReportManager.getExtentTest().info("Error message is Displayed");
    }
}
