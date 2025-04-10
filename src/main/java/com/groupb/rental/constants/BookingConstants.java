package com.groupb.rental.constants;

public final class BookingConstants {
    public static final String TABLE_NAME = "bookings";
    public static final String COL_ID = "id";
    public static final String COL_USER_ID = "user_id";
    public static final String COL_VEHICLE_ID = "vehicle_id";
    public static final String COL_BOOKING_DATE = "booking_date";
    public static final String COL_RETURN_DATE = "return_date";
    public static final String COL_TOTAL_COST = "total_cost";
    public static final String COL_STATUS = "status";
    
    private BookingConstants() {
        // Prevent instantiation
    }
}
