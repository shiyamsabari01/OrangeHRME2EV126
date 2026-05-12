package utilities;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class ApiUtilities {

    public static Response getRequestData(String endPoint){
        return RestAssured.get(endPoint);
    }

    public static Response postRequest(String endPoint, String payLoad){
       return RestAssured.given().header("Content-Type","application/json")
                .body(payLoad)
                .post(endPoint);
    }

    public static boolean validateStatusCode(Response response,int statusCode){
        return response.getStatusCode()==statusCode;
    }

    public static String getJsonResponse(Response response,String value){
        return response.jsonPath().getString(value);
    }
}
