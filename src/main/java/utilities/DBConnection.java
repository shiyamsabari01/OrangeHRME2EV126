package utilities;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class DBConnection {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/orangehrm";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "";

    public static Connection getDBConnection() {
        try {
            System.out.println("Starting DB Connection");
            Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            System.out.println("DB Connection successfully started");
            return connection;
        } catch (SQLException e) {
            System.out.println("Error while connecting DB");
            e.printStackTrace();
            return null;
        }
    }

    public static Map<String, String> getEmpDetails(String employeeID) {
        String query = "SELECT * FROM hs_hr_employee WHERE employee_id=" + employeeID;

        Map<String, String> empDetails = new HashMap<>();
        try (Connection connection = getDBConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            System.out.println("Executing query:" + query);
            if (resultSet.next()) {

                String firstName = resultSet.getString("emp_firstname");
                String lastName = resultSet.getString("emp_lastname");
                String middleName = resultSet.getString("emp_middle_name");

                if (middleName == null) {
                    middleName = "";
                }
                empDetails.put("firstName", firstName.trim());
                empDetails.put("lastName", lastName.trim());
                empDetails.put("middleName", middleName.trim());

                System.out.println("Executed Successfully");
            } else {
                System.out.println("Employee not found");
            }
            resultSet.close();
            statement.close();
            connection.close();

        } catch (Exception e) {
            System.out.println("Error while executing the Query");
            e.printStackTrace();
        }
        return empDetails;
    }
}
