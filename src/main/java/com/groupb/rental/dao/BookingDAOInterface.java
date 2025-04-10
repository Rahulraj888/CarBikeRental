package com.groupb.rental.dao;

import com.groupb.rental.model.Booking;
import java.util.List;

public interface BookingDAOInterface {
    boolean addBooking(Booking booking);
    List<Booking> getBookingsByUser(int userId);
    boolean updateBookingStatus(int bookingId, String status);
}
