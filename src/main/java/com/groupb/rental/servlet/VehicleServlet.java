package com.groupb.rental.servlet;

import com.groupb.rental.model.Vehicle;
import com.groupb.rental.model.User;
import com.groupb.rental.dao.VehicleDAOInterface;
import com.groupb.rental.dao.VehicleDAOImpl;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Servlet for handling vehicle management operations.
 * Supports listing vehicles for all users and administrative actions (insert, edit, delete) for admin users.
 */
public class VehicleServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(VehicleServlet.class.getName());

    /**
     * Processes GET requests for various vehicle actions.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if(action == null) {
            action = "list";
        }

        // If action is "list", we allow anyone to view the list of vehicles
        if (action.equals("list")) {
            logger.info("Listing vehicles for public view.");
            listVehicles(request, response);
            return;
        }

        // For other actions, check that an admin user is logged in.
        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;
        if(user == null || !"admin".equalsIgnoreCase(user.getRole())) {
            logger.warning("Unauthorized access attempt to vehicle management. Redirecting to login.");
            response.sendRedirect("login.jsp?error=Unauthorized access");
            return;
        }

        // Process admin-only actions
        switch(action) {
            case "new":
                logger.info("Admin requested new vehicle form.");
                showNewForm(request, response);
                break;
            case "insert":
                logger.info("Admin inserting new vehicle record.");
                insertVehicle(request, response);
                break;
            case "delete":
                logger.info("Admin deleting vehicle record.");
                deleteVehicle(request, response);
                break;
            case "edit":
                logger.info("Admin editing vehicle record.");
                showEditForm(request, response);
                break;
            case "update":
                logger.info("Admin updating vehicle record.");
                updateVehicle(request, response);
                break;
            default:
                logger.warning("Unknown action '" + action + "'. Listing vehicles by default.");
                listVehicles(request, response);
                break;
        }
    }

    /**
     * Forwards POST requests to doGet to handle all actions uniformly.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    /**
     * Retrieves and forwards a list of all vehicles to the vehicle list JSP.
     */
    private void listVehicles(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        VehicleDAOInterface vehicleDAO = new VehicleDAOImpl();
        List<Vehicle> list = vehicleDAO.getAllVehicles();
        logger.info("Fetched " + list.size() + " vehicles from the database.");
        request.setAttribute("vehicleList", list);
        RequestDispatcher dispatcher = request.getRequestDispatcher("vehicleList.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * Forwards to the vehicle form JSP for a new vehicle entry.
     */
    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.info("Displaying new vehicle form.");
        RequestDispatcher dispatcher = request.getRequestDispatcher("vehicleForm.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * Inserts a new vehicle record based on form data.
     */
    private void insertVehicle(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String type = request.getParameter("type");
        String brand = request.getParameter("brand");
        String model = request.getParameter("model");
        double pricePerDay = Double.parseDouble(request.getParameter("pricePerDay"));
        boolean available = (request.getParameter("available") != null);

        // Create a new Vehicle object with form data
        Vehicle vehicle = new Vehicle();
        vehicle.setId(0);
        vehicle.setType(type);
        vehicle.setBrand(brand);
        vehicle.setModel(model);
        vehicle.setPricePerDay(pricePerDay);
        vehicle.setAvailable(available);

        VehicleDAOInterface vehicleDAO = new VehicleDAOImpl();
        vehicleDAO.addVehicle(vehicle);
        logger.info("New vehicle inserted: " + brand + " " + model);
        response.sendRedirect("VehicleServlet");
    }

    /**
     * Retrieves a vehicle record by id and forwards to the form for editing.
     */
    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        VehicleDAOInterface vehicleDAO = new VehicleDAOImpl();
        Vehicle vehicle = vehicleDAO.getVehicleById(id);
        if (vehicle != null) {
            logger.info("Editing vehicle with id: " + id);
            request.setAttribute("vehicle", vehicle);
            RequestDispatcher dispatcher = request.getRequestDispatcher("vehicleForm.jsp");
            dispatcher.forward(request, response);
        } else {
            logger.warning("Vehicle with id " + id + " not found.");
            response.sendRedirect("VehicleServlet?action=list");
        }
    }

    /**
     * Updates an existing vehicle record with new details.
     */
    private void updateVehicle(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String type = request.getParameter("type");
        String brand = request.getParameter("brand");
        String model = request.getParameter("model");
        double pricePerDay = Double.parseDouble(request.getParameter("pricePerDay"));
        boolean available = (request.getParameter("available") != null);

        Vehicle vehicle = new Vehicle();
        vehicle.setId(id);
        vehicle.setType(type);
        vehicle.setBrand(brand);
        vehicle.setModel(model);
        vehicle.setPricePerDay(pricePerDay);
        vehicle.setAvailable(available);

        VehicleDAOInterface vehicleDAO = new VehicleDAOImpl();
        vehicleDAO.updateVehicle(vehicle);
        logger.info("Vehicle updated with id: " + id);
        response.sendRedirect("VehicleServlet");
    }

    /**
     * Deletes a vehicle record by id and then redirects to the list view.
     */
    private void deleteVehicle(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        VehicleDAOInterface vehicleDAO = new VehicleDAOImpl();
        vehicleDAO.deleteVehicle(id);
        logger.info("Vehicle deleted with id: " + id);
        response.sendRedirect("VehicleServlet");
    }
}
