package com.groupb.rental.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
    private static String URL;
    private static String USER;
    private static String PASSWORD;

    static {
        // Retrieve environment (defaults to "prod" if not set)
        String env = System.getProperty("env", "prod");
        String propertiesFile = "db-" + env + ".properties";
        try (InputStream input = DBConnection.class.getClassLoader().getResourceAsStream(propertiesFile)) {
        	if (input == null) {
                throw new RuntimeException("Property file '" + propertiesFile + "' not found in the classpath");
            }
            Properties properties = new Properties();
            properties.load(input);
            URL = properties.getProperty("db.url");
            USER = properties.getProperty("db.username");
            PASSWORD = properties.getProperty("db.password");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver"); 
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
