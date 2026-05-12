package utilities;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelReadUtility {

    public static List<String[]> getSheetData(String filePath, String sheetName) {
        List<String[]> data = new ArrayList<>();
        DataFormatter formatter = new DataFormatter();

        try (FileInputStream fileInputStream = new FileInputStream(filePath);
             Workbook workbook = WorkbookFactory.create(fileInputStream)) {
            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new IllegalArgumentException("Sheet " + sheetName + " Not Existed");
            }
            for (Row row : sheet) {
                if (row.getRowNum() == 0) {
                    continue;
                }

                int lastCellNum= row.getLastCellNum();

                List<String> rowData = new ArrayList<>();
                for (int i=0;i<lastCellNum;i++){
                    Cell cell=row.getCell(i,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    rowData.add(formatter.formatCellValue(cell).trim());
                }
                //Skip completely empty rows
                if (rowData.stream().allMatch(String::isEmpty)) {
                    continue;
                }
                data.add(rowData.toArray(new String[0]));
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read Excel file: "+filePath,e);
        }
        return data;
    }
}