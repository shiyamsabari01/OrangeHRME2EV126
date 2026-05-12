package pageObjects;

import actionDriver.ActionDriver;
import driverManager.DriverManager;
import org.openqa.selenium.By;

public class HomePage {

    private ActionDriver actionDriver;

    public HomePage() {
        this.actionDriver = new ActionDriver(DriverManager.getDriver());
    }

    private By isDashboardOption = By.xpath("//span[text()='Dashboard']");
    private By clickProfileMenu = By.xpath("//span[@class='oxd-userdropdown-tab']//p[@class='oxd-userdropdown-name']");
    private By logoutBtn = By.xpath("//a[text()='Logout']");
    private By PIMNavigate=By.xpath("//span[text()='PIM']");

    public boolean isDashboardDisplayed() {
        return actionDriver.isDisplayed(isDashboardOption);
    }

    public void logOut() {
        actionDriver.click(clickProfileMenu);
        actionDriver.click(logoutBtn);
    }

    public void NavigatePIMPage(){
        actionDriver.click(PIMNavigate);
    }
}
