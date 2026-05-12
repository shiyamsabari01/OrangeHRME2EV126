package testCases;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utilities.ApiUtilities;
import utilities.ExtentReportManager;

public class APITest {

    @Test
    public void verifyGetRequest(){
        SoftAssert softAssert=new SoftAssert();

        String endpoint="https://jsonplaceholder.typicode.com/users/1";
        ExtentReportManager.getExtentTest().info("API endpoint: "+endpoint);

        ExtentReportManager.getExtentTest().info("Send GET request to the API");
        Response response=ApiUtilities.getRequestData(endpoint);

        ExtentReportManager.getExtentTest().info("Validating status code");
        boolean isStatusCodeValidated=ApiUtilities.validateStatusCode(response,200);

        softAssert.assertTrue(isStatusCodeValidated,"Status code not matched");

        if (isStatusCodeValidated){
            ExtentReportManager.logValidationAPI("Status code validation done");
        }else {
            ExtentReportManager.logFailureAPI("Status code Failed");
        }

        ExtentReportManager.getExtentTest().info("Validate the Response");
        String username=ApiUtilities.getJsonResponse(response,"username");
        boolean isUsernameValid="Bret".equals(username);
        softAssert.assertTrue(isUsernameValid,"Username is not valid");
        if (isUsernameValid){
            ExtentReportManager.logValidationAPI("Username is valid");
        }else {
            ExtentReportManager.logFailureAPI("Username is Not Valid");
        }

        String email=ApiUtilities.getJsonResponse(response,"email");
        boolean isEmailValid="Sincere@april.biz".equals(email);
        softAssert.assertTrue(isEmailValid,"Email is not valid");
        if (isEmailValid){
            ExtentReportManager.logValidationAPI("Email is valid");
        }else {
            ExtentReportManager.logFailureAPI("Email is Not Valid");
        }
    }
}
