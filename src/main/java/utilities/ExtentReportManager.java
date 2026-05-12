package utilities;

import base.BaseClass;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.apache.commons.io.FileUtils;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.List;

public class ExtentReportManager implements ITestListener {
    private static ExtentSparkReporter sparkReporter;
    private static ExtentReports extentReports;
    private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();
    String repName;

    public static ExtentTest getExtentTest() {
        return extentTest.get();
    }

    public static void setExtentTest(ExtentTest test) {
        extentTest.set(test);
    }

    public void onStart(ITestContext context) {
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        repName = "Test-Report" + timeStamp + ".html";
        sparkReporter = new ExtentSparkReporter(".\\reports\\" + repName);
        sparkReporter.config().setDocumentTitle("OrangeHRM Project");
        sparkReporter.config().setReportName("OrangeHRM Project");
        sparkReporter.config().setTheme(Theme.DARK);

        extentReports = new ExtentReports();
        extentReports.attachReporter(sparkReporter);
        extentReports.setSystemInfo("Application", "OrangeHRM");
        extentReports.setSystemInfo("Username", System.getProperty("user.name"));
        extentReports.setSystemInfo("Environment", "QA");

        //Group name taken from xml file
        List<String> includeGroups = context.getCurrentXmlTest().getIncludedGroups();
        if (!includeGroups.isEmpty()) {
            extentReports.setSystemInfo("Groups", includeGroups.toString());
        }
    }

    public void onTestStart(ITestResult result){
        setExtentTest(extentReports.createTest(result.getMethod().getMethodName()));
        getExtentTest().assignCategory(result.getMethod().getGroups());
    }

    public void onTestSuccess(ITestResult result) {
        getExtentTest().log(Status.PASS, result.getName() + " got successfully completed");
    }

    public void onTestFailure(ITestResult result) {
        getExtentTest().log(Status.FAIL, result.getName() + " got Failed");
        getExtentTest().log(Status.INFO, result.getThrowable().getMessage());

        try {
            String imgPath = new BaseClass().captureScreen(result.getName());
            File file = new File(imgPath);
            byte[] fileContent = FileUtils.readFileToByteArray(file);
            String encodeString = Base64.getEncoder().encodeToString(fileContent);

            getExtentTest().addScreenCaptureFromBase64String(encodeString);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void onTestSkipped(ITestResult result) {
        getExtentTest().log(Status.SKIP, result.getName() + " got skipped");
        getExtentTest().log(Status.INFO, result.getThrowable().getMessage());
    }

    public void onFinish(ITestContext context) {
        extentReports.flush();
        String pathFile = System.getProperty("user.dir") + "\\reports\\" + repName;
        File filePath = new File(pathFile);
        try {
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(filePath.toURI());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void logFailureAPI(String logMessage){
        getExtentTest().fail(logMessage);
        try {
            String imgPath= new BaseClass().captureScreen("CustomFailure");
            File file=new File(imgPath);

            byte[] fileContent=FileUtils.readFileToByteArray(file);

            String encodingString=Base64.getEncoder().encodeToString(fileContent);
            getExtentTest().addScreenCaptureFromBase64String(encodingString);

        } catch (Exception e) {
            e.printStackTrace();
        }
        Assert.fail(logMessage);
    }

    public static void logValidationAPI(String logMessage){
        getExtentTest().pass(logMessage);
    }
}
