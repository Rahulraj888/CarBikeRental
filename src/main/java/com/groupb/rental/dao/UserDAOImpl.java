package com.groupb.rental.dao;

import com.groupb.rental.constants.UserConstants;
import com.groupb.rental.model.User;
import com.groupb.rental.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementation of the UserDAOInterface to manage User registration, login and retrieval.
 */
public class UserDAOImpl implements UserDAOInterface {

    private static final Logger logger = Logger.getLogger(UserDAOImpl.class.getName());

    /**
     * Registers a new user by inserting the details into the database.
     *
     * @param user A User object containing the registration details.
     * @return true if registration is successful; false otherwise.
     */
    @Override
    public boolean registerUser(User user) {
        String sql = "INSERT INTO " + UserConstants.TABLE_NAME + " ("
                + UserConstants.COL_USERNAME + ", "
                + UserConstants.COL_PASSWORD + ", "
                + UserConstants.COL_EMAIL + ", "
                + UserConstants.COL_ROLE
                + ") VALUES (?, ?, ?, ?)";
        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            logger.info("Registering new user: " + user.getUsername());
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getRole());
            int rowsAffected = ps.executeUpdate();
            logger.info("User registration successful, rows affected: " + rowsAffected);
            return rowsAffected > 0;
        } catch(Exception e) {
            logger.log(Level.SEVERE, "Error registering user: " + user.getUsername(), e);
        }
        return false;
    }

    /**
     * Authenticates a user using the provided username and password.
     *
     * @param username The username credential.
     * @param password The password credential.
     * @return User object if credentials are valid; null otherwise.
     */
    @Override
    public User login(String username, String password) {
        String sql = "SELECT * FROM " + UserConstants.TABLE_NAME + " WHERE " 
                + UserConstants.COL_USERNAME + " = ? AND " 
                + UserConstants.COL_PASSWORD + " = ?";
        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            logger.info("Attempting login for user: " + username);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                // Build and return a User object populated with data
                User user = new User();
                user.setId(rs.getInt(UserConstants.COL_ID));
                user.setUsername(rs.getString(UserConstants.COL_USERNAME));
                user.setPassword(rs.getString(UserConstants.COL_PASSWORD));
                user.setEmail(rs.getString(UserConstants.COL_EMAIL));
                user.setRole(rs.getString(UserConstants.COL_ROLE));
                logger.info("Login successful for user: " + username);
                return user;
            }
            logger.warning("Login failed: Invalid credentials for user: " + username);
        } catch(Exception e) {
            logger.log(Level.SEVERE, "Error during login for user: " + username, e);
        }
        return null;
    }
    
    /**
     * Retrieves a user by their unique id.
     *
     * @param id The user id.
     * @return User object if found; null otherwise.
     */
    @Override
    public User getUserById(int id) {
        String sql = "SELECT * FROM " + UserConstants.TABLE_NAME + " WHERE " + UserConstants.COL_ID + " = ?";
        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            logger.info("Retrieving user details for userId: " + id);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                User user = new User();
                user.setId(id);
                user.setUsername(rs.getString(UserConstants.COL_USERNAME));
                user.setPassword(rs.getString(UserConstants.COL_PASSWORD));
                user.setEmail(rs.getString(UserConstants.COL_EMAIL));
                user.setRole(rs.getString(UserConstants.COL_ROLE));
                logger.info("User found for userId: " + id);
                return user;
            }
            logger.warning("No user found for userId: " + id);
        } catch(Exception e) {
            logger.log(Level.SEVERE, "Error retrieving user for userId: " + id, e);
        }
        return null;
    }
}
