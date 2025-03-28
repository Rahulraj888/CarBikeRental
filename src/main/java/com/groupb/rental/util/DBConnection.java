package com.groupb.rental.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/rentaldb";
    private static final String USER = "root";
    private static final String PASSWORD = "Rahulraj@88"; // Change as needed

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver"); // MySQL 8.x driver
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}