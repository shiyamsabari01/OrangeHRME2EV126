package actionDriver;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utilities.Constants;

import java.time.Duration;

public class ActionDriver {

    private WebDriver driver;
    private WebDriverWait wait;

    public ActionDriver(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(Constants.EXP_WAIT));
    }

    //Click an Element
    public void click(By element) {
        try {
            waitElementClickable(element);
            driver.findElement(element).click();
        } catch (Exception e) {
            System.out.println("Unable to click an element: " + e.getMessage());
        }
    }

    //Enter text in input field
    public void enterText(By element, String value) {
        try {
            waitElementVisible(element);
            WebElement textBox = driver.findElement(element);
            textBox.clear();
            textBox.sendKeys(value);
        } catch (Exception e) {
            System.out.println("Unable to enter the text: " + e.getMessage());
        }
    }

    //Wait for Element clickable
    private void waitElementClickable(By element) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(element));
        } catch (Exception e) {
            System.out.println("Unable to click element: " + e.getMessage());
        }
    }

    //Wait for Element visible
    private void waitElementVisible(By element) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(element));
        } catch (Exception e) {
            System.out.println("Element is not visible: " + e.getMessage());
        }
    }

    //Get text from input field
    public String getText(By element) {
        try {
            waitElementVisible(element);
            return driver.findElement(element).getText();
        } catch (Exception e) {
            System.out.println("Unable to get the text: " + e.getMessage());
            return "";
        }
    }

    //Scroll to element
    public void scrollToElement(By element) {
        try {
            waitElementVisible(element);
            JavascriptExecutor executor = (JavascriptExecutor) driver;
            WebElement elements = driver.findElement(element);
            executor.executeScript("arguments[0].scrollIntoView(true);", elements);
        } catch (Exception e) {
            System.out.println("Unable to scroll to element: " + e.getMessage());
        }
    }

    //Element is displayed
    public boolean isDisplayed(By element) {
        try {
            waitElementVisible(element);
            boolean isDisplayed = driver.findElement(element).isDisplayed();
            if (isDisplayed) {
                return isDisplayed;
            } else {
                return isDisplayed;
            }
        } catch (Exception e) {
            System.out.println("Element is not displayed: " + e.getMessage());
            return false;
        }
    }

}
