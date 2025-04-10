package com.groupb.rental.dao;

import com.groupb.rental.constants.BookingConstants;
import com.groupb.rental.model.Booking;
import com.groupb.rental.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BookingDAOImpl implements BookingDAOInterface {

    @Override
    public boolean addBooking(Booking booking) {
        String sql = "INSERT INTO " + BookingConstants.TABLE_NAME + " ("
                + BookingConstants.COL_USER_ID + ", "
                + BookingConstants.COL_VEHICLE_ID + ", "
                + BookingConstants.COL_BOOKING_DATE + ", "
                + BookingConstants.COL_RETURN_DATE + ", "
                + BookingConstants.COL_TOTAL_COST + ", "
                + BookingConstants.COL_STATUS
                + ") VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, booking.getUserId());
            ps.setInt(2, booking.getVehicleId());
            ps.setDate(3, new java.sql.Date(booking.getBookingDate().getTime()));
            ps.setDate(4, new java.sql.Date(booking.getReturnDate().getTime()));
            ps.setDouble(5, booking.getTotalCost());
            ps.setString(6, booking.getStatus());
            return ps.executeUpdate() > 0;
        } catch(Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Booking> getBookingsByUser(int userId) {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM " + BookingConstants.TABLE_NAME + " WHERE " + BookingConstants.COL_USER_ID + " = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
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
        } catch(Exception e) {
            e.printStackTrace();
        }
        return bookings;
    }

    @Override
    public boolean updateBookingStatus(int bookingId, String status) {
        String sql = "UPDATE " + BookingConstants.TABLE_NAME + " SET " 
                + BookingConstants.COL_STATUS + " = ? WHERE " 
                + BookingConstants.COL_ID + " = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, bookingId);
            return ps.executeUpdate() > 0;
        } catch(Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
