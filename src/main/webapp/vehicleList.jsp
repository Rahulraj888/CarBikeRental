<%@ page contentType="text/html;charset=UTF-8" language="java" import="java.util.List, com.groupb.rental.model.Vehicle, com.groupb.rental.model.User, java.io.File" %>
<%
    request.setAttribute("pageTitle", "Vehicle List");

    List<Vehicle> vehicleList = (List<Vehicle>) request.getAttribute("vehicleList");
    User loggedInUser = (User) session.getAttribute("user");
    boolean isAdmin = (loggedInUser != null && "admin".equalsIgnoreCase(loggedInUser.getRole()));
%>
<jsp:include page="header.jsp" />

<main class="container my-4 flex-grow-1">
    <h2 class="mb-4">Vehicle List</h2>
    
    <!-- Bootstrap row with columns that wrap on smaller screens -->
    <div class="row row-cols-1 row-cols-md-3 g-4">
        <%
            if (vehicleList != null) {
                for (Vehicle v : vehicleList) {
                    String normalizedModel = v.getModel().toLowerCase().replaceAll("\\s+", "_");
                    String imagePath = "images/" + normalizedModel + ".jpeg";
                    // Check if the file actually exists on the server
                    String realPath = application.getRealPath("/") + imagePath;
                    File imgFile = new File(realPath);
                    if (!imgFile.exists()) {
                        // Fallback to placeholder if not found
                        imagePath = "images/default.jpeg";
                    }
        %>
        <div class="col">
          <div class="card h-100">
            <img src="<%= imagePath %>" class="card-img-top" alt="Vehicle Image" 
                 style="max-height: 180px; object-fit: cover;">
            <div class="card-body">
              <h5 class="card-title">
                <%= v.getBrand() %> <%= v.getModel() %>
                <span class="badge bg-secondary"><%= v.getType() %></span>
              </h5>
              <p class="card-text mb-1">
                <strong>Price per day:</strong> $<%= v.getPricePerDay() %>
              </p>
              <p class="card-text">
                <strong>Available:</strong> <%= v.isAvailable() ? "Yes" : "No" %>
              </p>
            </div>
            
            <div class="card-footer d-flex justify-content-between">
              <%
                if (isAdmin) {
              %>
                  <a href="VehicleServlet?action=edit&id=<%= v.getId() %>" class="btn btn-warning btn-sm">Edit</a>
                  <a href="VehicleServlet?action=delete&id=<%= v.getId() %>"
                     class="btn btn-danger btn-sm"
                     onclick="return confirm('Are you sure?')">
                     Delete
                  </a>
              <%
                } else {
              %>
                  <a href="BookingServlet?action=new&vehicleId=<%= v.getId() %>" class="btn btn-primary btn-sm">Book</a>
              <%
                }
              %>
            </div>
          </div>
        </div>
        <%
                }
            }
        %>
    </div> <!-- row -->
    <a href="dashboard.jsp" class="btn btn-link mt-4">Back to Dashboard</a>
</main>

<jsp:include page="footer.jsp" />
