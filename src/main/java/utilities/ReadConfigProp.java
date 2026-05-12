package utilities;

import java.util.Properties;

public class ReadConfigProp {

    protected Properties properties;

    public void loadProperties() {
        properties = new Properties();
        try {
            properties.load(getClass().getResourceAsStream("/config.properties"));
            if (properties == null) {
                System.out.println("Config Property files not found");

            }
        } catch (Exception e) {
            System.out.println("Unable to load prop" + e.getMessage());
        }

        Constants.APP_URL = properties.getProperty("URL");
        Constants.BROWSER = properties.getProperty("browser");
        Constants.USERNAME = properties.getProperty("username");
        Constants.PASSWORD = properties.getProperty("password");
        Constants.EXP_WAIT = Integer.parseInt(properties.getProperty("explicitWait"));
    }

}
