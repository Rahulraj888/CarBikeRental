package com.groupb.rental.dao;

import com.groupb.rental.model.Vehicle;
import java.util.List;

public interface VehicleDAOInterface {
    void addVehicle(Vehicle vehicle);
    List<Vehicle> getAllVehicles();
    Vehicle getVehicleById(int id);
    void updateVehicle(Vehicle vehicle);
    void deleteVehicle(int id);
}
