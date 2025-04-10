package com.groupb.rental.dao;

import com.groupb.rental.constants.VehicleConstants;
import com.groupb.rental.model.Vehicle;
import com.groupb.rental.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class VehicleDAOImpl implements VehicleDAOInterface {

    @Override
    public void addVehicle(Vehicle vehicle) {
        String sql = "INSERT INTO " + VehicleConstants.TABLE_NAME + " ("
                + VehicleConstants.COL_TYPE + ", "
                + VehicleConstants.COL_BRAND + ", "
                + VehicleConstants.COL_MODEL + ", "
                + VehicleConstants.COL_PRICE_PER_DAY + ", "
                + VehicleConstants.COL_AVAILABLE
                + ") VALUES (?, ?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, vehicle.getType());
            ps.setString(2, vehicle.getBrand());
            ps.setString(3, vehicle.getModel());
            ps.setDouble(4, vehicle.getPricePerDay());
            ps.setBoolean(5, vehicle.isAvailable());
            ps.executeUpdate();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Vehicle> getAllVehicles() {
        List<Vehicle> vehicles = new ArrayList<>();
        String sql = "SELECT * FROM " + VehicleConstants.TABLE_NAME;
        try (Connection con = DBConnection.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Vehicle v = new Vehicle();
                v.setId(rs.getInt(VehicleConstants.COL_ID));
                v.setType(rs.getString(VehicleConstants.COL_TYPE));
                v.setBrand(rs.getString(VehicleConstants.COL_BRAND));
                v.setModel(rs.getString(VehicleConstants.COL_MODEL));
                v.setPricePerDay(rs.getDouble(VehicleConstants.COL_PRICE_PER_DAY));
                v.setAvailable(rs.getBoolean(VehicleConstants.COL_AVAILABLE));
                vehicles.add(v);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return vehicles;
    }

    @Override
    public Vehicle getVehicleById(int id) {
        Vehicle v = null;
        String sql = "SELECT * FROM " + VehicleConstants.TABLE_NAME + " WHERE " 
                + VehicleConstants.COL_ID + " = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                v = new Vehicle();
                v.setId(rs.getInt(VehicleConstants.COL_ID));
                v.setType(rs.getString(VehicleConstants.COL_TYPE));
                v.setBrand(rs.getString(VehicleConstants.COL_BRAND));
                v.setModel(rs.getString(VehicleConstants.COL_MODEL));
                v.setPricePerDay(rs.getDouble(VehicleConstants.COL_PRICE_PER_DAY));
                v.setAvailable(rs.getBoolean(VehicleConstants.COL_AVAILABLE));
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return v;
    }

    @Override
    public void updateVehicle(Vehicle vehicle) {
        String sql = "UPDATE " + VehicleConstants.TABLE_NAME + " SET " 
                + VehicleConstants.COL_TYPE + " = ?, "
                + VehicleConstants.COL_BRAND + " = ?, "
                + VehicleConstants.COL_MODEL + " = ?, "
                + VehicleConstants.COL_PRICE_PER_DAY + " = ?, "
                + VehicleConstants.COL_AVAILABLE + " = ? WHERE " 
                + VehicleConstants.COL_ID + " = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, vehicle.getType());
            ps.setString(2, vehicle.getBrand());
            ps.setString(3, vehicle.getModel());
            ps.setDouble(4, vehicle.getPricePerDay());
            ps.setBoolean(5, vehicle.isAvailable());
            ps.setInt(6, vehicle.getId());
            ps.executeUpdate();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteVehicle(int id) {
        String sql = "DELETE FROM " + VehicleConstants.TABLE_NAME + " WHERE " 
                + VehicleConstants.COL_ID + " = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
