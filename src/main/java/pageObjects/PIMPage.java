package pageObjects;

import actionDriver.ActionDriver;
import driverManager.DriverManager;
import org.openqa.selenium.By;

public class PIMPage {
    private ActionDriver actionDriver;

    public PIMPage(){
        this.actionDriver=new ActionDriver(DriverManager.getDriver());
    }

    private By empName=By.xpath("(//label[text()='Employee Name']/parent::div/following-sibling::div/div/div/input)");
    private By searchBtn=By.xpath("//button[@type='submit']");
    private By firstAndLastName=By.xpath("(//div[@class='oxd-table-card']//div[@role='cell'])[3]");
    private By lastName=By.xpath("(//div[@class='oxd-table-card']//div[@role='cell'])[4]");

    public void empDetails(String EmpName){
        actionDriver.enterText(empName,EmpName);
        actionDriver.click(searchBtn);
        actionDriver.scrollToElement(firstAndLastName);
    }

    public String verifyFirstAndMiddleName(){
        actionDriver.scrollToElement(firstAndLastName);
        return actionDriver.getText(firstAndLastName);
    }

    public String verifyLastName(){
        actionDriver.scrollToElement(lastName);
        return actionDriver.getText(lastName);
    }
}
