<%@ page contentType="text/html;charset=UTF-8" language="java" 
    import="java.util.List, com.groupb.rental.model.Vehicle, com.groupb.rental.model.User, java.io.File" %>
<%
    // Set page title for display in header.jsp
    request.setAttribute("pageTitle", "Vehicle List");

    // Retrieve vehicles and logged-in user (if any)
    List<Vehicle> vehicleList = (List<Vehicle>) request.getAttribute("vehicleList");
    User loggedInUser = (User) session.getAttribute("user");
    boolean isAdmin = (loggedInUser != null && "admin".equalsIgnoreCase(loggedInUser.getRole()));

    // Retrieve filter parameter values to pre-populate the form fields
    String filterType = request.getParameter("type");
    String filterAvailable = request.getParameter("available");
    String filterMinPrice = request.getParameter("minPrice");
    String filterMaxPrice = request.getParameter("maxPrice");
%>
<jsp:include page="header.jsp" />

<main class="container my-4">
    <h2 class="mb-4 text-center">Vehicle List</h2>

    <!-- Filter Form -->
    <form action="VehicleServlet" method="get" class="card p-4 mb-4 shadow-sm">
        <input type="hidden" name="action" value="list" />
        <div class="row g-3">
            <!-- Filter by Type -->
            <div class="col-md-3">
                <label for="type" class="form-label">Type</label>
                <select name="type" id="type" class="form-select">
                    <option value="">All Types</option>
                    <option value="Car" <%= "Car".equalsIgnoreCase(filterType) ? "selected" : "" %>>Car</option>
                    <option value="Bike" <%= "Bike".equalsIgnoreCase(filterType) ? "selected" : "" %>>Bike</option>
                </select>
            </div>
            <!-- Filter by Availability -->
            <div class="col-md-3">
                <label for="available" class="form-label">Availability</label>
                <select name="available" id="available" class="form-select">
                    <option value="">All</option>
                    <option value="true" <%= "true".equals(filterAvailable) ? "selected" : "" %>>Available</option>
                    <option value="false" <%= "false".equals(filterAvailable) ? "selected" : "" %>>Not Available</option>
                </select>
            </div>
            <!-- Filter by Minimum Price -->
            <div class="col-md-3">
                <label for="minPrice" class="form-label">Min Price</label>
                <input type="number" step="0.01" name="minPrice" id="minPrice" class="form-control"
                       placeholder="Min Price" value="<%= filterMinPrice != null ? filterMinPrice : "" %>" />
            </div>
            <!-- Filter by Maximum Price -->
            <div class="col-md-3">
                <label for="maxPrice" class="form-label">Max Price</label>
                <input type="number" step="0.01" name="maxPrice" id="maxPrice" class="form-control"
                       placeholder="Max Price" value="<%= filterMaxPrice != null ? filterMaxPrice : "" %>" />
            </div>
        </div>
        <div class="row mt-3 justify-content-end">
            <div class="col-md-2">
                <button type="submit" class="btn btn-primary w-100">Apply</button>
            </div>
        </div>
    </form>

    <!-- Vehicle Cards Grid -->
    <div class="row row-cols-1 row-cols-md-3 g-4">
        <% if (vehicleList != null && !vehicleList.isEmpty()) { 
               for (Vehicle v : vehicleList) { 
                   // Prepare an image path using the model name, or fallback to default image
                   String normalizedModel = v.getModel().toLowerCase().replaceAll("\\s+", "_");
                   String imagePath = "images/" + normalizedModel + ".jpeg";
                   String realPath = application.getRealPath("/") + imagePath;
                   File imgFile = new File(realPath);
                   if (!imgFile.exists()) {
                       imagePath = "images/default.jpeg";
                   }
        %>
        <div class="col">
            <div class="card h-100 shadow-sm">
                <img 
				  src="<%= imagePath %>"
				  class="card-img-top"
				  alt="Vehicle Image"
				  style="max-height: 180px; object-fit: contain;">
                
                <div class="card-body">
                    <h5 class="card-title">
                        <%= v.getBrand() %> <%= v.getModel() %> 
                        <span class="badge bg-secondary"><%= v.getType() %></span>
                    </h5>
                    <p class="card-text"><strong>Price per day:</strong> $<%= v.getPricePerDay() %></p>
                    <p class="card-text"><strong>Available:</strong> <%= v.isAvailable() ? "Yes" : "No" %></p>
                </div>
                <div class="card-footer d-flex justify-content-between">
                    <% if (isAdmin) { %>
                    <a href="VehicleServlet?action=edit&id=<%= v.getId() %>" class="btn btn-warning btn-sm">Edit</a>
                    <a href="VehicleServlet?action=delete&id=<%= v.getId() %>" class="btn btn-danger btn-sm" onclick="return confirm('Are you sure?')">Delete</a>
                    <% } else { %>
                    <a href="BookingServlet?action=new&vehicleId=<%= v.getId() %>" class="btn btn-primary btn-sm">Book</a>
                    <% } %>
                </div>
            </div>
        </div>
        <%   }
           } else { %>
            <div class="col">
                <p class="text-center">No vehicles found with the current filters.</p>
            </div>
        <% } %>
    </div> <!-- .row -->
    
    <a href="dashboard.jsp" class="btn btn-link mt-4">Back to Dashboard</a>
</main>

<jsp:include page="footer.jsp" />
