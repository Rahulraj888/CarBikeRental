<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    // Get the current logged-in user from the session
    com.groupb.rental.model.User user = (com.groupb.rental.model.User) session.getAttribute("user");
    String option = request.getParameter("option");
    if (option == null || option.trim().isEmpty()) {
        option = "profile";
    }
%>
<div class="card">
    <div class="card-header bg-primary text-white">
        Menu
    </div>
    <div class="list-group list-group-flush">
        <a href="dashboard.jsp?option=profile" class="list-group-item list-group-item-action <%= "profile".equals(option) ? "active" : "" %>">
            Profile Info
        </a>
        <% if ("admin".equalsIgnoreCase(user.getRole())) { %>
        <a href="dashboard.jsp?option=addVehicle" class="list-group-item list-group-item-action <%= "addVehicle".equals(option) ? "active" : "" %>">
            Add Vehicle
        </a>
        <% } else { %>
        <a href="dashboard.jsp?option=bookings" class="list-group-item list-group-item-action <%= "bookings".equals(option) ? "active" : "" %>">
            Bookings
        </a>
        <% } %>
        <a href="UserServlet?action=logout" class="list-group-item list-group-item-action text-danger">
            Logout
        </a>
    </div>
</div>
