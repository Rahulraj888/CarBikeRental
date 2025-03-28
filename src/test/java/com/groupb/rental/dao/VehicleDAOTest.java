package com.groupb.rental.dao;

import static org.junit.Assert.*;
import org.junit.*;
import java.util.List;
import com.groupb.rental.model.Vehicle;
import com.groupb.rental.dao.VehicleDAO;

public class VehicleDAOTest {

    @Test
    public void testAddAndListVehicle() {
        Vehicle vehicle = new Vehicle(0, "Car", "Toyota", "Camry", 50.0, true);
        VehicleDAO.addVehicle(vehicle);
        
        List<Vehicle> vehicles = VehicleDAO.getAllVehicles();
        assertNotNull("Vehicle list should not be null", vehicles);
        assertTrue("Vehicle list should contain at least one vehicle", vehicles.size() > 0);
    }

    @Test
    public void testUpdateAndDeleteVehicle() {
        // Insert a test vehicle
        Vehicle vehicle = new Vehicle(0, "Bike", "Honda", "CBR", 30.0, true);
        VehicleDAO.addVehicle(vehicle);
        List<Vehicle> vehicles = VehicleDAO.getAllVehicles();
        assertTrue("Vehicle list should have at least one element", vehicles.size() > 0);
        
        // Assume last inserted vehicle is the one we want to update/delete
        Vehicle lastVehicle = vehicles.get(vehicles.size()-1);
        lastVehicle.setBrand("Yamaha");
        VehicleDAO.updateVehicle(lastVehicle);
        Vehicle updatedVehicle = VehicleDAO.getVehicleById(lastVehicle.getId());
        assertEquals("Brand should be updated", "Yamaha", updatedVehicle.getBrand());
        
        int sizeBeforeDelete = vehicles.size();
        VehicleDAO.deleteVehicle(lastVehicle.getId());
        List<Vehicle> vehiclesAfterDelete = VehicleDAO.getAllVehicles();
        assertEquals("Vehicle list size should decrease by one", sizeBeforeDelete - 1, vehiclesAfterDelete.size());
    }
}
