package utilities;

import org.testng.annotations.DataProvider;

import java.util.List;

public class DataProviders {
    private static final String FILE_PATH=System.getProperty("user.dir")+"\\src\\main\\resources\\testData\\loginCred.xlsx";

    @DataProvider(name = "DataDriven")
    public static Object[][] values(){
        return getSheetData("Credentials");
    }

    @DataProvider(name="empVerification")
    public static Object[][] empVerification(){
        return getSheetData("EmpDetails");
    }

    private static Object[][] getSheetData(String sheetName){
        List<String[]> sheetData=ExcelReadUtility.getSheetData(FILE_PATH,sheetName);

        Object[][] data=new Object[sheetData.size()][sheetData.get(0).length];
        for (int i=0;i< sheetData.size();i++){
            data[i]=sheetData.get(i);
        }
        return data;
    }
}
