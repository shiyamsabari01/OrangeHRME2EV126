package driverManager;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import utilities.Constants;
import utilities.LoggerManager;

import java.util.HashMap;
import java.util.Map;

public class DriverManager {

    private final static Logger logger = LoggerManager.getLogger(DriverManager.class);

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();


    private DriverManager() {

    }

    public static WebDriver getDriver() {
        if (driver.get() == null) {
            loadBrowser();
        }
        return driver.get();
    }

    public static void loadBrowser() {
        try {
            WebDriver localDriver;
            switch (Constants.BROWSER.toLowerCase()) {
                case "firefox":
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    firefoxOptions.addArguments("--start-maximized");
                    localDriver = new FirefoxDriver(firefoxOptions);
                    break;
                case "edge":
                    EdgeOptions edgeOptions = new EdgeOptions();
                    edgeOptions.addArguments("--start-maximized");
                    localDriver = new EdgeDriver(edgeOptions);
                    break;
                default:
                    ChromeOptions chromeOptions = new ChromeOptions();
                    Map<String,Object> prefs=new HashMap<>();
                    prefs.put("credentials_enable_service", false);
                    prefs.put("profile.password_manager_enabled", false);
                    prefs.put("profile.password_manager_leak_detection", false);
                    chromeOptions.setExperimentalOption("prefs", prefs);
                    chromeOptions.addArguments("--start-maximized");

                    chromeOptions.addArguments("--disable-save-password-bubble");
                    chromeOptions.addArguments("--disable-features=PasswordLeakDetection");
                    chromeOptions.addArguments("--incognito");
                    chromeOptions.addArguments("--start-maximized");

                    localDriver = new ChromeDriver(chromeOptions);
                    break;
            }

            driver.set(localDriver);

        } catch (Exception e) {
            System.out.println("Please Launch the Valid Browser" + Constants.BROWSER);
        }
    }

    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}
