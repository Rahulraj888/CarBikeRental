package com.groupb.rental.dao;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import com.groupb.rental.model.User;

/**
 * Tests for UserDAO functionality including user registration, login, and retrieval by id.
 */
public class UserDAOTest {

    // DAO interface for user operations.
    private UserDAOInterface userDAO;

    /**
     * The setUp() method initializes the UserDAO implementation before each test.
     */
    @Before
    public void setUp() {
        userDAO = new UserDAOImpl();
    }

    /**
     * Tests registration and login functionality.
     * It first registers a user with a unique username then attempts to log in using the provided credentials.
     */
    @Test
    public void testRegisterAndLogin() {
        // Create a unique username using the current time
        String uniqueUsername = "testUser_" + System.currentTimeMillis();
        User user = new User();
        user.setId(0);
        user.setUsername(uniqueUsername);
        user.setPassword("testPass");
        user.setEmail("test@example.com");
        user.setRole("customer");

        // Assert that registration is successful
        boolean registered = userDAO.registerUser(user);
        assertTrue("User should be registered successfully", registered);

        // Attempt to log in using the unique username and password
        User loggedInUser = userDAO.login(uniqueUsername, "testPass");
        assertNotNull("Login should return a valid user", loggedInUser);
        assertEquals("Username should match", uniqueUsername, loggedInUser.getUsername());
    }

    /**
     * Tests retrieval of a user by their id.
     * This test registers a user and then retrieves the same user based on the generated id.
     */
    @Test
    public void testGetUserById() {
        String uniqueUsername = "testUser2_" + System.currentTimeMillis();
        User user = new User();
        user.setId(0);
        user.setUsername(uniqueUsername);
        user.setPassword("testPass");
        user.setEmail("test2@example.com");
        user.setRole("customer");

        // Register the user first
        boolean registered = userDAO.registerUser(user);
        assertTrue("User should be registered", registered);

        // Log in to obtain the user's id (if not returned on registration)
        User loginUser = userDAO.login(uniqueUsername, "testPass");
        assertNotNull("Login should succeed", loginUser);

        int id = loginUser.getId();
        // Retrieve the user using the obtained id
        User retrievedUser = userDAO.getUserById(id);
        assertNotNull("User retrieved by ID should not be null", retrievedUser);
        assertEquals("Username should match", uniqueUsername, retrievedUser.getUsername());
    }
}
