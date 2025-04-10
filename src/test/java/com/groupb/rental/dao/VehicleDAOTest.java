package com.groupb.rental.dao;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.List;
import com.groupb.rental.model.Vehicle;

public class VehicleDAOTest {

    private VehicleDAOInterface vehicleDAO;

    @Before
    public void setUp() {
        vehicleDAO = new VehicleDAOImpl();
    }

    @Test
    public void testAddAndListVehicle() {
        Vehicle vehicle = new Vehicle();
        vehicle.setId(0);
        vehicle.setType("Car");
        vehicle.setBrand("Toyota");
        vehicle.setModel("Camry");
        vehicle.setPricePerDay(50.0);
        vehicle.setAvailable(true);
        
        vehicleDAO.addVehicle(vehicle);
        
        List<Vehicle> vehicles = vehicleDAO.getAllVehicles();
        assertNotNull("Vehicle list should not be null", vehicles);
        assertTrue("Vehicle list should contain at least one vehicle", vehicles.size() > 0);
    }

    @Test
    public void testUpdateAndDeleteVehicle() {
        // Insert a test vehicle
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
        
        // Assume last inserted vehicle is the one we want to update/delete
        Vehicle lastVehicle = vehicles.get(vehicles.size() - 1);
        lastVehicle.setBrand("Yamaha");
        vehicleDAO.updateVehicle(lastVehicle);
        Vehicle updatedVehicle = vehicleDAO.getVehicleById(lastVehicle.getId());
        assertEquals("Brand should be updated", "Yamaha", updatedVehicle.getBrand());
        
        int sizeBeforeDelete = vehicles.size();
        vehicleDAO.deleteVehicle(lastVehicle.getId());
        List<Vehicle> vehiclesAfterDelete = vehicleDAO.getAllVehicles();
        assertEquals("Vehicle list size should decrease by one", sizeBeforeDelete - 1, vehiclesAfterDelete.size());
    }
}
