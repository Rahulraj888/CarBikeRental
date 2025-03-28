package com.groupb.rental.dao;

import static org.junit.Assert.*;
import org.junit.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import com.groupb.rental.model.Booking;
import com.groupb.rental.dao.BookingDAO;

public class BookingDAOTest {

    @Test
    public void testAddBooking() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date bookingDate = sdf.parse("2025-04-01");
        Date returnDate = sdf.parse("2025-04-05");
        
        Booking booking = new Booking(0, 1, 1, bookingDate, returnDate, 200.0, "pending");
        boolean success = BookingDAO.addBooking(booking);
        assertTrue("Booking should be added successfully", success);
    }

    @Test
    public void testGetBookingsByUser() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date bookingDate = sdf.parse("2025-04-01");
        Date returnDate = sdf.parse("2025-04-05");
        
        Booking booking = new Booking(0, 1, 1, bookingDate, returnDate, 200.0, "pending");
        boolean added = BookingDAO.addBooking(booking);
        assertTrue("Booking should be added", added);
        
        List<Booking> bookings = BookingDAO.getBookingsByUser(1);
        assertNotNull("Booking list should not be null", bookings);
        assertTrue("Booking list should have at least one booking", bookings.size() > 0);
    }
}
