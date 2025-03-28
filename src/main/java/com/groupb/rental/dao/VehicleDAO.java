package com.groupb.rental.dao;

import com.groupb.rental.bean.Vehicle;
import com.groupb.rental.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VehicleDAO {

    public static void addVehicle(Vehicle vehicle) {
        String sql = "INSERT INTO vehicles (type, brand, model, price_per_day, available) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, vehicle.getType());
            ps.setString(2, vehicle.getBrand());
            ps.setString(3, vehicle.getModel());
            ps.setDouble(4, vehicle.getPricePerDay());
            ps.setBoolean(5, vehicle.isAvailable());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Vehicle> getAllVehicles() {
        List<Vehicle> vehicles = new ArrayList<>();
        String sql = "SELECT * FROM vehicles";
        try (Connection con = DBConnection.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Vehicle v = new Vehicle();
                v.setId(rs.getInt("id"));
                v.setType(rs.getString("type"));
                v.setBrand(rs.getString("brand"));
                v.setModel(rs.getString("model"));
                v.setPricePerDay(rs.getDouble("price_per_day"));
                v.setAvailable(rs.getBoolean("available"));
                vehicles.add(v);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vehicles;
    }

    public static Vehicle getVehicleById(int id) {
        Vehicle v = null;
        String sql = "SELECT * FROM vehicles WHERE id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                v = new Vehicle();
                v.setId(rs.getInt("id"));
                v.setType(rs.getString("type"));
                v.setBrand(rs.getString("brand"));
                v.setModel(rs.getString("model"));
                v.setPricePerDay(rs.getDouble("price_per_day"));
                v.setAvailable(rs.getBoolean("available"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return v;
    }

    public static void updateVehicle(Vehicle vehicle) {
        String sql = "UPDATE vehicles SET type=?, brand=?, model=?, price_per_day=?, available=? WHERE id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, vehicle.getType());
            ps.setString(2, vehicle.getBrand());
            ps.setString(3, vehicle.getModel());
            ps.setDouble(4, vehicle.getPricePerDay());
            ps.setBoolean(5, vehicle.isAvailable());
            ps.setInt(6, vehicle.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteVehicle(int id) {
        String sql = "DELETE FROM vehicles WHERE id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}