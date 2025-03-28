<%@ page contentType="text/html;charset=UTF-8" language="java" import="java.util.List, com.groupb.rental.model.Vehicle, com.groupb.rental.model.User" %>
<%
    request.setAttribute("pageTitle", "Vehicle List");
    List<Vehicle> vehicleList = (List<Vehicle>) request.getAttribute("vehicleList");
    // Retrieve the logged-in user from the session
    User loggedInUser = (User) session.getAttribute("user");
    boolean isAdmin = (loggedInUser != null && "admin".equalsIgnoreCase(loggedInUser.getRole()));
%>
<jsp:include page="header.jsp" />
<main class="container my-4 flex-grow-1">
    <h2>Vehicle List</h2>
    <table class="table table-bordered table-striped">
      <thead class="table-dark">
        <tr>
          <th>ID</th>
          <th>Type</th>
          <th>Brand</th>
          <th>Model</th>
          <th>Price per day</th>
          <th>Available</th>
          <th>Actions</th>
        </tr>
      </thead>
      <tbody>
      <%
        if (vehicleList != null) {
          for (Vehicle v : vehicleList) {
      %>
        <tr>
          <td><%= v.getId() %></td>
          <td><%= v.getType() %></td>
          <td><%= v.getBrand() %></td>
          <td><%= v.getModel() %></td>
          <td><%= v.getPricePerDay() %></td>
          <td><%= v.isAvailable() ? "Yes" : "No" %></td>
          <td>
            <% if(isAdmin) { %>
              <a href="VehicleServlet?action=edit&id=<%= v.getId() %>" class="btn btn-warning btn-sm">Edit</a>
              <a href="VehicleServlet?action=delete&id=<%= v.getId() %>" 
                 class="btn btn-danger btn-sm"
                 onclick="return confirm('Are you sure?')">Delete</a>
            <% } else { %>
              <a href="BookingServlet?action=new&vehicleId=<%= v.getId() %>" class="btn btn-primary btn-sm">Book</a>
            <% } %>
          </td>
        </tr>
      <%
          }
        }
      %>
      </tbody>
    </table>
    <a href="dashboard.jsp" class="btn btn-link">Back to Dashboard</a>
</main>
<jsp:include page="footer.jsp" />
