package com.groupb.rental.servlet;

import com.groupb.rental.model.Vehicle;
import com.groupb.rental.model.User;
import com.groupb.rental.dao.VehicleDAOInterface;
import com.groupb.rental.dao.VehicleDAOImpl;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

public class VehicleServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if(action == null) {
            action = "list";
        }

        // If the action is "list", allow anyone (logged in or not) to see the list
        if (action.equals("list")) {
            listVehicles(request, response);
            return;
        }

        // Otherwise, require an admin user
        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;
        if(user == null || !"admin".equals(user.getRole())) {
            // Not logged in or not an admin
            response.sendRedirect("login.jsp?error=Unauthorized access");
            return;
        }

        // Admin-only actions
        switch(action) {
            case "new":
                showNewForm(request, response);
                break;
            case "insert":
                insertVehicle(request, response);
                break;
            case "delete":
                deleteVehicle(request, response);
                break;
            case "edit":
                showEditForm(request, response);
                break;
            case "update":
                updateVehicle(request, response);
                break;
            default:
                // if unknown action provided, list vehicles
                listVehicles(request, response);
                break;
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    private void listVehicles(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        VehicleDAOInterface vehicleDAO = new VehicleDAOImpl();
        List<Vehicle> list = vehicleDAO.getAllVehicles();
        request.setAttribute("vehicleList", list);
        RequestDispatcher dispatcher = request.getRequestDispatcher("vehicleList.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("vehicleForm.jsp");
        dispatcher.forward(request, response);
    }

    private void insertVehicle(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String type = request.getParameter("type");
        String brand = request.getParameter("brand");
        String model = request.getParameter("model");
        double pricePerDay = Double.parseDouble(request.getParameter("pricePerDay"));
        boolean available = (request.getParameter("available") != null);

        Vehicle vehicle = new Vehicle();
        vehicle.setId(0);
        vehicle.setType(type);
        vehicle.setBrand(brand);
        vehicle.setModel(model);
        vehicle.setPricePerDay(pricePerDay);
        vehicle.setAvailable(available);

        VehicleDAOInterface vehicleDAO = new VehicleDAOImpl();
        vehicleDAO.addVehicle(vehicle);
        response.sendRedirect("VehicleServlet");
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        VehicleDAOInterface vehicleDAO = new VehicleDAOImpl();
        Vehicle vehicle = vehicleDAO.getVehicleById(id);
        request.setAttribute("vehicle", vehicle);
        RequestDispatcher dispatcher = request.getRequestDispatcher("vehicleForm.jsp");
        dispatcher.forward(request, response);
    }

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
        response.sendRedirect("VehicleServlet");
    }

    private void deleteVehicle(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        VehicleDAOInterface vehicleDAO = new VehicleDAOImpl();
        vehicleDAO.deleteVehicle(id);
        response.sendRedirect("VehicleServlet");
    }
}
