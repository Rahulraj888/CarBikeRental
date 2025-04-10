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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementation of the VehicleDAOInterface to perform CRUD operations on Vehicle records.
 */
public class VehicleDAOImpl implements VehicleDAOInterface {

    private static final Logger logger = Logger.getLogger(VehicleDAOImpl.class.getName());

    /**
     * Inserts a new vehicle record into the database.
     *
     * @param vehicle The Vehicle object to be inserted.
     */
    @Override
    public void addVehicle(Vehicle vehicle) {
        String sql = "INSERT INTO " + VehicleConstants.TABLE_NAME + " ("
                + VehicleConstants.COL_TYPE + ", "
                + VehicleConstants.COL_BRAND + ", "
                + VehicleConstants.COL_MODEL + ", "
                + VehicleConstants.COL_PRICE_PER_DAY + ", "
                + VehicleConstants.COL_AVAILABLE
                + ") VALUES (?, ?, ?, ?, ?)";
        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            logger.info("Adding new vehicle: " + vehicle.getBrand() + " " + vehicle.getModel());
            ps.setString(1, vehicle.getType());
            ps.setString(2, vehicle.getBrand());
            ps.setString(3, vehicle.getModel());
            ps.setDouble(4, vehicle.getPricePerDay());
            ps.setBoolean(5, vehicle.isAvailable());
            ps.executeUpdate();
            logger.info("Vehicle added successfully.");
        } catch(Exception e) {
            logger.log(Level.SEVERE, "Error adding vehicle: " + vehicle.getBrand() + " " + vehicle.getModel(), e);
        }
    }

    /**
     * Retrieves all vehicles from the database.
     *
     * @return A List of Vehicle objects.
     */
    @Override
    public List<Vehicle> getAllVehicles() {
        List<Vehicle> vehicles = new ArrayList<>();
        String sql = "SELECT * FROM " + VehicleConstants.TABLE_NAME;
        try (
            Connection con = DBConnection.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql)
        ) {
            logger.info("Fetching all vehicles from the database.");
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
            logger.info("Retrieved " + vehicles.size() + " vehicles.");
        } catch(Exception e) {
            logger.log(Level.SEVERE, "Error fetching vehicles.", e);
        }
        return vehicles;
    }

    /**
     * Retrieves a single vehicle record by its id.
     *
     * @param id The vehicle id.
     * @return The Vehicle object if found; null otherwise.
     */
    @Override
    public Vehicle getVehicleById(int id) {
        Vehicle v = null;
        String sql = "SELECT * FROM " + VehicleConstants.TABLE_NAME + " WHERE " 
                + VehicleConstants.COL_ID + " = ?";
        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            logger.info("Fetching vehicle with id: " + id);
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
                logger.info("Vehicle retrieved: " + v.getBrand() + " " + v.getModel());
            } else {
                logger.warning("No vehicle found with id: " + id);
            }
        } catch(Exception e) {
            logger.log(Level.SEVERE, "Error fetching vehicle with id: " + id, e);
        }
        return v;
    }

    /**
     * Updates an existing vehicle record in the database.
     *
     * @param vehicle The Vehicle object with updated values.
     */
    @Override
    public void updateVehicle(Vehicle vehicle) {
        String sql = "UPDATE " + VehicleConstants.TABLE_NAME + " SET " 
                + VehicleConstants.COL_TYPE + " = ?, "
                + VehicleConstants.COL_BRAND + " = ?, "
                + VehicleConstants.COL_MODEL + " = ?, "
                + VehicleConstants.COL_PRICE_PER_DAY + " = ?, "
                + VehicleConstants.COL_AVAILABLE + " = ? WHERE " 
                + VehicleConstants.COL_ID + " = ?";
        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            logger.info("Updating vehicle with id: " + vehicle.getId());
            ps.setString(1, vehicle.getType());
            ps.setString(2, vehicle.getBrand());
            ps.setString(3, vehicle.getModel());
            ps.setDouble(4, vehicle.getPricePerDay());
            ps.setBoolean(5, vehicle.isAvailable());
            ps.setInt(6, vehicle.getId());
            ps.executeUpdate();
            logger.info("Vehicle updated successfully for id: " + vehicle.getId());
        } catch(Exception e) {
            logger.log(Level.SEVERE, "Error updating vehicle with id: " + vehicle.getId(), e);
        }
    }

    /**
     * Deletes a vehicle record from the database by id.
     *
     * @param id The id of the vehicle to be deleted.
     */
    @Override
    public void deleteVehicle(int id) {
        String sql = "DELETE FROM " + VehicleConstants.TABLE_NAME + " WHERE " 
                + VehicleConstants.COL_ID + " = ?";
        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            logger.info("Deleting vehicle with id: " + id);
            ps.setInt(1, id);
            ps.executeUpdate();
            logger.info("Vehicle deleted successfully for id: " + id);
        } catch(Exception e) {
            logger.log(Level.SEVERE, "Error deleting vehicle with id: " + id, e);
        }
    }
}
