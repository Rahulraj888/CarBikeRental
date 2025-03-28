package com.groupb.rental.bean;

import java.util.Date;

public class Booking {
    private int id;
    private int userId;
    private int vehicleId;
    private Date bookingDate;
    private Date returnDate;
    private double totalCost;
    private String status; // e.g., "pending", "confirmed", "cancelled"

    public Booking() {}

    public Booking(int id, int userId, int vehicleId, Date bookingDate, Date returnDate, double totalCost, String status) {
        this.id = id;
        this.userId = userId;
        this.vehicleId = vehicleId;
        this.bookingDate = bookingDate;
        this.returnDate = returnDate;
        this.totalCost = totalCost;
        this.status = status;
    }
    
    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    } 

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    } 

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    } 

    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    } 

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    } 

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    } 

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
