package driverManager;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import utilities.Constants;
import utilities.LoggerManager;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class DriverManager {

    private final static Logger logger = LoggerManager.getLogger(DriverManager.class);

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();


    private DriverManager() {

    }

    public static WebDriver getDriver() {
        if (driver.get()==null){
            throw  new RuntimeException("WebDriver is not initialized.");
        }
        return driver.get();
    }

    public static void loadBrowser(String browser) {
        try {
            WebDriver localDriver;
            boolean gridStatus = Boolean.parseBoolean(Constants.SELENIUM_GRID);

            switch (browser.toLowerCase()) {
                case "firefox":
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    firefoxOptions.addArguments("--start-maximized");
                    if (gridStatus) {
                        localDriver = new RemoteWebDriver(new URL(Constants.GRID_URL), firefoxOptions);
                    } else {
                        localDriver = new FirefoxDriver(firefoxOptions);
                    }
                    break;
                case "edge":
                    EdgeOptions edgeOptions = new EdgeOptions();
                    edgeOptions.addArguments("--start-maximized");
                    if (gridStatus) {
                        localDriver = new RemoteWebDriver(new URL(Constants.GRID_URL), edgeOptions);

                    } else {
                        localDriver = new EdgeDriver(edgeOptions);
                    }
                    break;
                default:
                    ChromeOptions chromeOptions = new ChromeOptions();
                    Map<String, Object> prefs = new HashMap<>();
                    prefs.put("credentials_enable_service", false);
                    prefs.put("profile.password_manager_enabled", false);
                    prefs.put("profile.password_manager_leak_detection", false);
                    chromeOptions.setExperimentalOption("prefs", prefs);
                    chromeOptions.addArguments("--start-maximized");

                    chromeOptions.addArguments("--disable-save-password-bubble");
                    chromeOptions.addArguments("--disable-features=PasswordLeakDetection");
                    chromeOptions.addArguments("--incognito");
                    chromeOptions.addArguments("--start-maximized");
                    if (gridStatus) {
                        localDriver = new RemoteWebDriver(new URL(Constants.GRID_URL), chromeOptions);
                    } else {
                        localDriver = new ChromeDriver(chromeOptions);
                    }
                    break;
            }

            driver.set(localDriver);

        } catch (Exception e) {
            logger.error("Please Launch the Valid Browser" + browser,e);
throw new RuntimeException(e);
        }
    }

    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}
