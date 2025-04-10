package com.groupb.rental.dao;

import com.groupb.rental.constants.UserConstants;
import com.groupb.rental.model.User;
import com.groupb.rental.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAOImpl implements UserDAOInterface {

    @Override
    public boolean registerUser(User user) {
        String sql = "INSERT INTO " + UserConstants.TABLE_NAME + " ("
                + UserConstants.COL_USERNAME + ", "
                + UserConstants.COL_PASSWORD + ", "
                + UserConstants.COL_EMAIL + ", "
                + UserConstants.COL_ROLE
                + ") VALUES (?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getRole());
            return ps.executeUpdate() > 0;
        } catch(Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public User login(String username, String password) {
        String sql = "SELECT * FROM " + UserConstants.TABLE_NAME + " WHERE " 
                + UserConstants.COL_USERNAME + " = ? AND " 
                + UserConstants.COL_PASSWORD + " = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                User user = new User();
                user.setId(rs.getInt(UserConstants.COL_ID));
                user.setUsername(rs.getString(UserConstants.COL_USERNAME));
                user.setPassword(rs.getString(UserConstants.COL_PASSWORD));
                user.setEmail(rs.getString(UserConstants.COL_EMAIL));
                user.setRole(rs.getString(UserConstants.COL_ROLE));
                return user;
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    @Override
    public User getUserById(int id) {
        String sql = "SELECT * FROM " + UserConstants.TABLE_NAME + " WHERE " + UserConstants.COL_ID + " = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                User user = new User();
                user.setId(id);
                user.setUsername(rs.getString(UserConstants.COL_USERNAME));
                user.setPassword(rs.getString(UserConstants.COL_PASSWORD));
                user.setEmail(rs.getString(UserConstants.COL_EMAIL));
                user.setRole(rs.getString(UserConstants.COL_ROLE));
                return user;
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
