package com.groupb.rental;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

// Import all your test classes here
import com.groupb.rental.dao.UserDAOTest;
import com.groupb.rental.dao.VehicleDAOTest;
import com.groupb.rental.dao.BookingDAOTest;
import com.groupb.rental.servlet.UserServletTest;
import com.groupb.rental.servlet.BookingServletTest;
import com.groupb.rental.servlet.VehicleServletTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    UserDAOTest.class,
    VehicleDAOTest.class,
    BookingDAOTest.class,
    UserServletTest.class,
    BookingServletTest.class,
    VehicleServletTest.class
})
public class AllTests {
	
	static {
        // Set the system property for environment configuration for test database.
        System.setProperty("env", "test");
    }
}
