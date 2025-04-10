package com.groupb.rental.dao;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import com.groupb.rental.model.Booking;
import com.groupb.rental.model.Vehicle;

public class BookingDAOTest {

    private BookingDAOInterface bookingDAO;
    private VehicleDAOInterface vehicleDAO; 

    @Before
    public void setUp() {
        bookingDAO = new BookingDAOImpl();
        vehicleDAO = new VehicleDAOImpl();
    }

    
    private int createTestVehicle() {
        Vehicle vehicle = new Vehicle();
        vehicle.setId(0);
        vehicle.setType("Car");
        vehicle.setBrand("Toyota");
        vehicle.setModel("Camry");
        vehicle.setPricePerDay(50.0);
        vehicle.setAvailable(true);
        vehicleDAO.addVehicle(vehicle);
        List<Vehicle> vehicles = vehicleDAO.getAllVehicles();
        return vehicles.get(vehicles.size() - 1).getId();
    }

    @Test
    public void testAddBooking() throws Exception {
        int validVehicleId = createTestVehicle();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date bookingDate = sdf.parse("2025-04-01");
        Date returnDate = sdf.parse("2025-04-05");

        Booking booking = new Booking();
        booking.setId(0);
        booking.setUserId(1);
        booking.setVehicleId(validVehicleId);  // Use valid vehicle id
        booking.setBookingDate(bookingDate);
        booking.setReturnDate(returnDate);
        booking.setTotalCost(200.0);
        booking.setStatus("pending");

        boolean success = bookingDAO.addBooking(booking);
        assertTrue("Booking should be added successfully", success);
    }

    @Test
    public void testGetBookingsByUser() throws Exception {
        int validVehicleId = createTestVehicle();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date bookingDate = sdf.parse("2025-04-01");
        Date returnDate = sdf.parse("2025-04-05");

        Booking booking = new Booking();
        booking.setId(0);
        booking.setUserId(1);
        booking.setVehicleId(validVehicleId);  // Use valid vehicle id
        booking.setBookingDate(bookingDate);
        booking.setReturnDate(returnDate);
        booking.setTotalCost(200.0);
        booking.setStatus("pending");

        boolean added = bookingDAO.addBooking(booking);
        assertTrue("Booking should be added", added);

        List<Booking> bookings = bookingDAO.getBookingsByUser(1);
        assertNotNull("Booking list should not be null", bookings);
        assertTrue("Booking list should have at least one booking", bookings.size() > 0);
    }
}
