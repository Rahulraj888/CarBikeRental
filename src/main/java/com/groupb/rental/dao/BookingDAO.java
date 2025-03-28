package com.groupb.rental.dao;

import com.groupb.rental.bean.Booking;
import com.groupb.rental.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {

    public static boolean addBooking(Booking booking) {
        String sql = "INSERT INTO bookings (user_id, vehicle_id, booking_date, return_date, total_cost, status) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, booking.getUserId());
            ps.setInt(2, booking.getVehicleId());
            ps.setDate(3, new java.sql.Date(booking.getBookingDate().getTime()));
            ps.setDate(4, new java.sql.Date(booking.getReturnDate().getTime()));
            ps.setDouble(5, booking.getTotalCost());
            ps.setString(6, booking.getStatus());
            int result = ps.executeUpdate();
            return result > 0;
        } catch(Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static List<Booking> getBookingsByUser(int userId) {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM bookings WHERE user_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                Booking booking = new Booking();
                booking.setId(rs.getInt("id"));
                booking.setUserId(rs.getInt("user_id"));
                booking.setVehicleId(rs.getInt("vehicle_id"));
                booking.setBookingDate(rs.getDate("booking_date"));
                booking.setReturnDate(rs.getDate("return_date"));
                booking.setTotalCost(rs.getDouble("total_cost"));
                booking.setStatus(rs.getString("status"));
                bookings.add(booking);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return bookings;
    }

    public static boolean updateBookingStatus(int bookingId, String status) {
        String sql = "UPDATE bookings SET status = ? WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, bookingId);
            int result = ps.executeUpdate();
            return result > 0;
        } catch(Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
