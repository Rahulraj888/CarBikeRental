package com.groupb.rental.servlet;

import com.groupb.rental.model.Booking;
import com.groupb.rental.dao.BookingDAO;
import com.groupb.rental.dao.VehicleDAO;
import com.groupb.rental.model.Vehicle;
import com.groupb.rental.model.User;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class BookingServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Ensure user is logged in
        HttpSession session = request.getSession();
        if(session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String action = request.getParameter("action");
        if(action == null) action = "list";
        switch(action) {
            case "new":
                int vehicleId = Integer.parseInt(request.getParameter("vehicleId"));
                Vehicle vehicle = VehicleDAO.getVehicleById(vehicleId);
                request.setAttribute("vehicle", vehicle);
                RequestDispatcher dispatcher = request.getRequestDispatcher("bookingForm.jsp");
                dispatcher.forward(request, response);
                break;
            case "list":
                int userId = ((User) session.getAttribute("user")).getId();
                List<Booking> bookingList = BookingDAO.getBookingsByUser(userId);
                request.setAttribute("bookingList", bookingList);
                RequestDispatcher listDispatcher = request.getRequestDispatcher("bookingList.jsp");
                listDispatcher.forward(request, response);
                break;
            default:
                response.sendRedirect("BookingServlet?action=list");
                break;
        }
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Handle booking creation
        HttpSession session = request.getSession();
        if(session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        try {
            int vehicleId = Integer.parseInt(request.getParameter("vehicleId"));
            String bookingDateStr = request.getParameter("bookingDate");
            String returnDateStr = request.getParameter("returnDate");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date bookingDate = sdf.parse(bookingDateStr);
            Date returnDate = sdf.parse(returnDateStr);
            
            // Calculate total cost
            Vehicle vehicle = VehicleDAO.getVehicleById(vehicleId);
            long diffInMillies = Math.abs(returnDate.getTime() - bookingDate.getTime());
            long diffDays = (diffInMillies / (1000 * 60 * 60 * 24)) + 1;
            double totalCost = diffDays * vehicle.getPricePerDay();
            
            int userId = ((User) session.getAttribute("user")).getId();
            Booking booking = new Booking(0, userId, vehicleId, bookingDate, returnDate, totalCost, "pending");
            
            boolean success = BookingDAO.addBooking(booking);
            if(success) {
                response.sendRedirect("BookingServlet?action=list");
            } else {
                response.sendRedirect("bookingForm.jsp?error=Booking failed");
            }
        } catch(Exception e) {
            e.printStackTrace();
            response.sendRedirect("bookingForm.jsp?error=Invalid data provided");
        }
    }
}