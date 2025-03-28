package com.groupb.rental.dao;

import static org.junit.Assert.*;
import org.junit.*;
import com.groupb.rental.model.User;
import com.groupb.rental.dao.UserDAO;

public class UserDAOTest {

    @Test
    public void testRegisterAndLogin() {
    	 // Create a unique username
        String uniqueUsername = "testUser_" + System.currentTimeMillis();
        User user = new User(0, uniqueUsername, "testPass", "test@example.com", "customer");
        boolean registered = UserDAO.registerUser(user);
        assertTrue("User should be registered successfully", registered);

        // Test login
        User loggedInUser = UserDAO.login("testUser", "testPass");
        assertNotNull("Login should return a valid user", loggedInUser);
        assertEquals("Username should match", "testUser", loggedInUser.getUsername());
    }

    @Test
    public void testGetUserById() {
        // Create a unique username
        String uniqueUsername = "testUser2_" + System.currentTimeMillis();
        User user = new User(0, uniqueUsername, "testPass", "test2@example.com", "customer");
        boolean registered = UserDAO.registerUser(user);
        assertTrue("User should be registered", registered);

        User loginUser = UserDAO.login(uniqueUsername, "testPass");
        assertNotNull("Login should succeed", loginUser);

        int id = loginUser.getId();
        User retrievedUser = UserDAO.getUserById(id);
        assertNotNull("User retrieved by ID should not be null", retrievedUser);
        assertEquals("Username should match", uniqueUsername, retrievedUser.getUsername());
    }

}
