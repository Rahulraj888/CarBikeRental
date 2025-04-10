package com.groupb.rental.servlet;

import com.groupb.rental.model.Booking;
import com.groupb.rental.dao.BookingDAOInterface;
import com.groupb.rental.dao.BookingDAOImpl;
import com.groupb.rental.dao.VehicleDAOInterface;
import com.groupb.rental.dao.VehicleDAOImpl;
import com.groupb.rental.model.Vehicle;
import com.groupb.rental.model.User;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Servlet for handling booking-related requests.
 * Supports both GET (retrieving booking forms and lists) and POST (creating bookings).
 */
public class BookingServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(BookingServlet.class.getName());

    /**
     * Handles GET requests for bookings.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Ensure user is logged in
        HttpSession session = request.getSession();
        // Build current URL to redirect after login if needed
        String currentURL = request.getRequestURL().toString();
        String queryString = request.getQueryString();
        if(queryString != null) {
            currentURL += "?" + queryString;
        }
        session.setAttribute("redirectAfterLogin", currentURL);
        logger.info("Current URL stored for redirection: " + currentURL);

        // Check if user session exists
        if(session.getAttribute("user") == null) {
            logger.warning("User not logged in. Redirecting to login page.");
            response.sendRedirect("login.jsp");
            return;
        }

        // Determine action from request parameters, default to "list"
        String action = request.getParameter("action");
        if(action == null) action = "list";
        switch(action) {
            case "new":
                // Show booking form for a new booking for a specific vehicle
                int vehicleId = Integer.parseInt(request.getParameter("vehicleId"));
                VehicleDAOInterface vehicleDAO = new VehicleDAOImpl();
                Vehicle vehicle = vehicleDAO.getVehicleById(vehicleId);
                if (vehicle != null) {
                    logger.info("Displaying booking form for vehicle id: " + vehicleId);
                    request.setAttribute("vehicle", vehicle);
                    RequestDispatcher dispatcher = request.getRequestDispatcher("bookingForm.jsp");
                    dispatcher.forward(request, response);
                } else {
                    logger.warning("Vehicle with id " + vehicleId + " not found. Redirecting to dashboard.");
                    response.sendRedirect("dashboard.jsp?error=Vehicle not found");
                }
                break;
            case "list":
                // List bookings for the current user
                int userId = ((User) session.getAttribute("user")).getId();
                BookingDAOInterface bookingDAO = new BookingDAOImpl();
                List<Booking> bookingList = bookingDAO.getBookingsByUser(userId);
                logger.info("Listing bookings for userId: " + userId + ", total found: " + bookingList.size());
                request.setAttribute("bookingList", bookingList);
                RequestDispatcher listDispatcher = request.getRequestDispatcher("dashboard.jsp");
                listDispatcher.forward(request, response);
                break;
            default:
                // Unknown action, redirect to list view
                logger.warning("Unknown action '" + action + "'. Redirecting to list view.");
                response.sendRedirect("BookingServlet?action=list");
                break;
        }
    }

    /**
     * Handles POST requests for creating a new booking.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // If user is not logged in, redirect immediately
        HttpSession session = request.getSession();
        if (session.getAttribute("user") == null) {
            logger.warning("POST attempt without logged-in user. Redirecting to login page.");
            response.sendRedirect("login.jsp");
            return;
        }
        
        try {
            // Retrieve parameters from form data
            int vehicleId = Integer.parseInt(request.getParameter("vehicleId"));
            String bookingDateStr = request.getParameter("bookingDate");
            String returnDateStr = request.getParameter("returnDate");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date bookingDate = sdf.parse(bookingDateStr);
            Date returnDate = sdf.parse(returnDateStr);
            
            // Calculate total cost based on the price per day and the number of days
            VehicleDAOInterface vehicleDAO = new VehicleDAOImpl();
            Vehicle vehicle = vehicleDAO.getVehicleById(vehicleId);
            if(vehicle == null) {
                logger.warning("Vehicle not found for id: " + vehicleId);
                response.sendRedirect("bookingForm.jsp?error=Vehicle not found");
                return;
            }
            long diffInMillies = Math.abs(returnDate.getTime() - bookingDate.getTime());
            long diffDays = (diffInMillies / (1000 * 60 * 60 * 24)) + 1;
            double totalCost = diffDays * vehicle.getPricePerDay();
            
            // Build Booking object with collected data
            int userId = ((User) session.getAttribute("user")).getId();
            Booking booking = new Booking();
            booking.setId(0);
            booking.setUserId(userId);
            booking.setVehicleId(vehicleId);
            booking.setBookingDate(bookingDate);
            booking.setReturnDate(returnDate);
            booking.setTotalCost(totalCost);
            booking.setStatus("confirmed");
            
            // Attempt to add the booking using DAO
            BookingDAOInterface bookingDAO = new BookingDAOImpl();
            boolean success = bookingDAO.addBooking(booking);
            if(success) {
                logger.info("Booking created successfully for userId: " + userId);
                response.sendRedirect("BookingServlet?action=list");
            } else {
                logger.warning("Failed to create booking for userId: " + userId);
                response.sendRedirect("bookingForm.jsp?error=Booking failed");
            }
        } catch(Exception e) {
            logger.log(Level.SEVERE, "Exception occurred during booking creation", e);
            response.sendRedirect("bookingForm.jsp?error=Invalid data provided");
        }
    }
}
