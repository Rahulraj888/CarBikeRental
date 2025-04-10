package com.groupb.rental.dao;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import com.groupb.rental.model.User;

public class UserDAOTest {

    private UserDAOInterface userDAO;

    @Before
    public void setUp() {
        userDAO = new UserDAOImpl();
    }

    @Test
    public void testRegisterAndLogin() {
        // Create a unique username
        String uniqueUsername = "testUser_" + System.currentTimeMillis();
        User user = new User();
        user.setId(0);
        user.setUsername(uniqueUsername);
        user.setPassword("testPass");
        user.setEmail("test@example.com");
        user.setRole("customer");

        boolean registered = userDAO.registerUser(user);
        assertTrue("User should be registered successfully", registered);

        // Test login using the unique username and password
        User loggedInUser = userDAO.login(uniqueUsername, "testPass");
        assertNotNull("Login should return a valid user", loggedInUser);
        assertEquals("Username should match", uniqueUsername, loggedInUser.getUsername());
    }

    @Test
    public void testGetUserById() {
        String uniqueUsername = "testUser2_" + System.currentTimeMillis();
        User user = new User();
        user.setId(0);
        user.setUsername(uniqueUsername);
        user.setPassword("testPass");
        user.setEmail("test2@example.com");
        user.setRole("customer");

        boolean registered = userDAO.registerUser(user);
        assertTrue("User should be registered", registered);

        User loginUser = userDAO.login(uniqueUsername, "testPass");
        assertNotNull("Login should succeed", loginUser);

        int id = loginUser.getId();
        User retrievedUser = userDAO.getUserById(id);
        assertNotNull("User retrieved by ID should not be null", retrievedUser);
        assertEquals("Username should match", uniqueUsername, retrievedUser.getUsername());
    }
}
