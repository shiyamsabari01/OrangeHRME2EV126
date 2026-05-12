package pageObjects;

import actionDriver.ActionDriver;
import driverManager.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {
    private ActionDriver actionDriver;

    public LoginPage() {
        this.actionDriver=new ActionDriver(DriverManager.getDriver());
    }

    private By userNameField = By.name("username");
    private By pwdField = By.name("password");
    private By loginBtn = By.xpath("//button[@type='submit']");
    private By errorMsg = By.xpath("//p[text()='Invalid credentials']");


    public void login(String username,String password){
        actionDriver.enterText(userNameField,username);
        actionDriver.enterText(pwdField,password);
        actionDriver.click(loginBtn);
    }

    public boolean isErrorMsgDisplayed(){
        return actionDriver.isDisplayed(errorMsg);
    }

}
