package com.groupb.rental.dao;

import com.groupb.rental.constants.BookingConstants;
import com.groupb.rental.model.Booking;
import com.groupb.rental.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementation of the BookingDAOInterface to handle CRUD operations for Booking objects.
 */
public class BookingDAOImpl implements BookingDAOInterface {

    // Initialize logger for this class
    private static final Logger logger = Logger.getLogger(BookingDAOImpl.class.getName());

    /**
     * Adds a new booking record into the database.
     *
     * @param booking The booking object containing booking details.
     * @return true if the booking was added successfully; false otherwise.
     */
    @Override
    public boolean addBooking(Booking booking) {
        // Build SQL statement dynamically using constants for table and column names
        String sql = "INSERT INTO " + BookingConstants.TABLE_NAME + " ("
                + BookingConstants.COL_USER_ID + ", "
                + BookingConstants.COL_VEHICLE_ID + ", "
                + BookingConstants.COL_BOOKING_DATE + ", "
                + BookingConstants.COL_RETURN_DATE + ", "
                + BookingConstants.COL_TOTAL_COST + ", "
                + BookingConstants.COL_STATUS
                + ") VALUES (?, ?, ?, ?, ?, ?)";
        try (
            // Obtain a connection using DBConnection utility class
            Connection con = DBConnection.getConnection();
            // Prepare statement for safe insertion
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            logger.info("Preparing to add a booking for userId: " + booking.getUserId());

            // Set parameters for the prepared statement from the booking object
            ps.setInt(1, booking.getUserId());
            ps.setInt(2, booking.getVehicleId());
            ps.setDate(3, new java.sql.Date(booking.getBookingDate().getTime()));
            ps.setDate(4, new java.sql.Date(booking.getReturnDate().getTime()));
            ps.setDouble(5, booking.getTotalCost());
            ps.setString(6, booking.getStatus());

            int rowsAffected = ps.executeUpdate();
            logger.info("Booking insertion successful for userId: " + booking.getUserId() +
                        ", rows affected: " + rowsAffected);
            return rowsAffected > 0;
        } catch(Exception e) {
            logger.log(Level.SEVERE, "Error adding booking for userId: " + booking.getUserId(), e);
        }
        return false;
    }

    /**
     * Retrieves a list of bookings for a given user.
     *
     * @param userId The unique id of the user.
     * @return List of Booking objects; empty list if none found.
     */
    @Override
    public List<Booking> getBookingsByUser(int userId) {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM " + BookingConstants.TABLE_NAME + " WHERE " 
                + BookingConstants.COL_USER_ID + " = ?";
        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            logger.info("Fetching bookings for userId: " + userId);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            // Iterate over the result set and create Booking objects
            while(rs.next()) {
                Booking booking = new Booking();
                booking.setId(rs.getInt(BookingConstants.COL_ID));
                booking.setUserId(rs.getInt(BookingConstants.COL_USER_ID));
                booking.setVehicleId(rs.getInt(BookingConstants.COL_VEHICLE_ID));
                booking.setBookingDate(rs.getDate(BookingConstants.COL_BOOKING_DATE));
                booking.setReturnDate(rs.getDate(BookingConstants.COL_RETURN_DATE));
                booking.setTotalCost(rs.getDouble(BookingConstants.COL_TOTAL_COST));
                booking.setStatus(rs.getString(BookingConstants.COL_STATUS));
                bookings.add(booking);
            }
            logger.info("Retrieved " + bookings.size() + " bookings for userId: " + userId);
        } catch(Exception e) {
            logger.log(Level.SEVERE, "Error retrieving bookings for userId: " + userId, e);
        }
        return bookings;
    }

    /**
     * Updates the status of a booking identified by bookingId.
     *
     * @param bookingId The unique booking identifier.
     * @param status    The new status to update.
     * @return true if update is successful; false otherwise.
     */
    @Override
    public boolean updateBookingStatus(int bookingId, String status) {
        String sql = "UPDATE " + BookingConstants.TABLE_NAME + " SET " 
                + BookingConstants.COL_STATUS + " = ? WHERE " 
                + BookingConstants.COL_ID + " = ?";
        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            logger.info("Updating status for bookingId: " + bookingId + " to status: " + status);
            ps.setString(1, status);
            ps.setInt(2, bookingId);
            int rowsAffected = ps.executeUpdate();
            logger.info("Booking status updated, rows affected: " + rowsAffected);
            return rowsAffected > 0;
        } catch(Exception e) {
            logger.log(Level.SEVERE, "Error updating booking status for bookingId: " + bookingId, e);
        }
        return false;
    }
}
