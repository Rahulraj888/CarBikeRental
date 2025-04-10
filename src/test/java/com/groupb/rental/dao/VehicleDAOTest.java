package com.groupb.rental.dao;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.List;
import com.groupb.rental.model.Vehicle;

/**
 * Tests for VehicleDAO functionality including adding, updating, deleting,
 * and listing vehicles.
 */
public class VehicleDAOTest {

    // DAO interface for vehicle operations.
    private VehicleDAOInterface vehicleDAO;

    /**
     * The setUp() method initializes the VehicleDAO implementation before each test.
     */
    @Before
    public void setUp() {
        vehicleDAO = new VehicleDAOImpl();
    }

    /**
     * Tests adding a new vehicle and then listing all vehicles.
     * Asserts that the list is non-null and contains at least one vehicle.
     */
    @Test
    public void testAddAndListVehicle() {
        Vehicle vehicle = new Vehicle();
        vehicle.setId(0);
        vehicle.setType("Car");
        vehicle.setBrand("Toyota");
        vehicle.setModel("Camry");
        vehicle.setPricePerDay(50.0);
        vehicle.setAvailable(true);
        
        // Insert the new vehicle
        vehicleDAO.addVehicle(vehicle);
        
        // Retrieve the list of vehicles
        List<Vehicle> vehicles = vehicleDAO.getAllVehicles();
        assertNotNull("Vehicle list should not be null", vehicles);
        assertTrue("Vehicle list should contain at least one vehicle", vehicles.size() > 0);
    }

    /**
     * Tests updating and deleting a vehicle.
     * A vehicle is inserted, then updated, and finally deleted.
     * Checks that update operations change the data and that deletion reduces the list size.
     */
    @Test
    public void testUpdateAndDeleteVehicle() {
        // Insert a test vehicle first
        Vehicle vehicle = new Vehicle();
        vehicle.setId(0);
        vehicle.setType("Bike");
        vehicle.setBrand("Honda");
        vehicle.setModel("CBR");
        vehicle.setPricePerDay(30.0);
        vehicle.setAvailable(true);
        
        vehicleDAO.addVehicle(vehicle);
        List<Vehicle> vehicles = vehicleDAO.getAllVehicles();
        assertTrue("Vehicle list should have at least one element", vehicles.size() > 0);
        
        // Assume the last inserted vehicle is the one we want to update/delete
        Vehicle lastVehicle = vehicles.get(vehicles.size() - 1);
        // Update the brand
        lastVehicle.setBrand("Yamaha");
        vehicleDAO.updateVehicle(lastVehicle);
        // Retrieve the updated vehicle and confirm the update
        Vehicle updatedVehicle = vehicleDAO.getVehicleById(lastVehicle.getId());
        assertEquals("Brand should be updated", "Yamaha", updatedVehicle.getBrand());
        
        // Delete the updated vehicle and verify that the list size decreases
        int sizeBeforeDelete = vehicles.size();
        vehicleDAO.deleteVehicle(lastVehicle.getId());
        List<Vehicle> vehiclesAfterDelete = vehicleDAO.getAllVehicles();
        assertEquals("Vehicle list size should decrease by one", sizeBeforeDelete - 1, vehiclesAfterDelete.size());
    }
}
