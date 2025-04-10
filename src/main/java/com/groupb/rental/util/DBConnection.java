package com.groupb.rental.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utility class for managing database connections.
 * Loads the database configuration from a properties file based on the environment.
 */
public class DBConnection {

    // Logger for this class
    private static final Logger logger = Logger.getLogger(DBConnection.class.getName());

    // Database URL, username, and password loaded from properties file
    private static String URL;
    private static String USER;
    private static String PASSWORD;

    // Static initializer block to load database configuration upon class loading.
    static {
        // Retrieve the environment system property; default to "prod" if not set.
        String env = System.getProperty("env", "prod");
        // Construct the properties file name based on the environment.
        String propertiesFile = "db-" + env + ".properties";
        try (InputStream input = DBConnection.class.getClassLoader().getResourceAsStream(propertiesFile)) {
            // If the file is not found in the classpath, throw an error.
            if (input == null) {
                throw new RuntimeException("Property file '" + propertiesFile + "' not found in the classpath");
            }
            Properties properties = new Properties();
            properties.load(input);
            URL = properties.getProperty("db.url");
            USER = properties.getProperty("db.username");
            PASSWORD = properties.getProperty("db.password");
            logger.info("Loaded database configuration for environment: " + env);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error loading database configuration from " + propertiesFile, e);
        }
    }
    
    /**
     * Establishes and returns a database connection.
     *
     * @return A Connection object to the database.
     * @throws SQLException If a database access error occurs.
     * @throws ClassNotFoundException If the JDBC driver class is not found.
     */
    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        // Load the JDBC driver class
        Class.forName("com.mysql.cj.jdbc.Driver"); 
        logger.info("Establishing database connection to URL: " + URL);
        // Return the connection using the loaded configuration
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
