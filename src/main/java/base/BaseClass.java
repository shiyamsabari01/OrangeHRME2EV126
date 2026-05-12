package base;

import driverManager.DriverManager;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.annotations.*;
import utilities.Constants;
import utilities.LoggerManager;
import utilities.ReadConfigProp;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BaseClass {
    private final static Logger logger = LoggerManager.getLogger(BaseClass.class);

    @BeforeClass
    public void setup() {
        try {
            ReadConfigProp readConfigProp = new ReadConfigProp();
            readConfigProp.loadProperties();
            logger.info("Properties Loaded");

            DriverManager.loadBrowser();
            logger.info("Browser Launched Successfully");
            DriverManager.getDriver().navigate().to(Constants.APP_URL);
            logger.info("Navigated to Application URL");
        } catch (Exception e) {
            System.out.println("Unable to Launch the URL: " + e.getMessage());
        }
    }

    @AfterClass
    public void tearDown() {
        try {
            DriverManager.quitDriver();
            logger.info("Browser Closed Successfully");
        } catch (Exception e) {
            System.out.println("Unable to Close the Tear Down: " + e.getMessage());
        }
    }

    public String captureScreen(String tname) {
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        String filePath = System.getProperty("user.dir") + "\\screenShots\\" + tname + "_" + timeStamp + ".png";
        File sorceFile = ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.FILE);
        File destinationFile = new File(filePath);
        try {
            Files.copy(sorceFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            e.getMessage();
        }
        return filePath;
    }

    public String randomString(){
        String name= RandomStringUtils.secure().nextAlphabetic(6);
        return name;
    }

    public String randomAlphaNumeric(){
        String pwd=RandomStringUtils.secure().nextAlphanumeric(8);
        return pwd;
    }
}

