<%@ page contentType="text/html;charset=UTF-8" language="java"
    import="java.util.List, com.groupb.rental.model.Booking, com.groupb.rental.dao.BookingDAOInterface, com.groupb.rental.dao.BookingDAOImpl, com.groupb.rental.model.User" %>
<%
    // Get the current logged-in user from the session
    User user = (User) session.getAttribute("user");
    
    // If no user in session, redirect to login (safety check)
    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    
    // Determine which option to display in the main area
    String option = request.getParameter("option");
    if (option == null || option.trim().isEmpty()) {
        option = "profile"; // Default to profile
    }
%>
<jsp:include page="header.jsp" />

<div class="container my-4">
    <h2 class="mb-3">Dashboard</h2>
    <div class="row">
        <!-- Sidebar -->
        <aside class="col-md-3 mb-4">
            <jsp:include page="dashboardMenu.jsp" />
        </aside>

        <!-- Main Content -->
        <section class="col-md-9">
            <% if ("profile".equals(option)) { %>
            <div class="card mb-4">
                <div class="card-header bg-info text-white">Profile Information</div>
                <div class="card-body">
                    <p><strong>Username:</strong> <%= user.getUsername() %></p>
                    <p><strong>Email:</strong> <%= user.getEmail() %></p>
                    <p><strong>Role:</strong> <%= user.getRole() %></p>
                </div>
            </div>
            <% } else if ("bookings".equals(option) && !"admin".equalsIgnoreCase(user.getRole())) {
                    int userId = user.getId();
                    // Instantiate DAO and call the non-static method now.
                    com.groupb.rental.dao.BookingDAOInterface bookingDAO = new com.groupb.rental.dao.BookingDAOImpl();
                    List<Booking> bookingList = bookingDAO.getBookingsByUser(userId);
            %>
            <div class="card mb-4">
                <div class="card-header bg-success text-white">Your Bookings</div>
                <div class="card-body">
                    <div class="table-responsive">
                        <table class="table table-bordered table-striped align-middle">
                            <thead class="table-dark">
                                <tr>
                                    <th>ID</th>
                                    <th>Vehicle ID</th>
                                    <th>Booking Date</th>
                                    <th>Return Date</th>
                                    <th>Total Cost</th>
                                    <th>Status</th>
                                </tr>
                            </thead>
                            <tbody>
                                <% if (bookingList != null && !bookingList.isEmpty()) {
                                       for (Booking b : bookingList) { %>
                                <tr>
                                    <td><%= b.getId() %></td>
                                    <td><%= b.getVehicleId() %></td>
                                    <td><%= b.getBookingDate() %></td>
                                    <td><%= b.getReturnDate() %></td>
                                    <td>$<%= b.getTotalCost() %></td>
                                    <td><%= b.getStatus() %></td>
                                </tr>
                                <%   }
                                   } else { %>
                                <tr>
                                    <td colspan="6" class="text-center">No bookings found.</td>
                                </tr>
                                <% } %>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
            <% } else if ("addVehicle".equals(option) && "admin".equalsIgnoreCase(user.getRole())) { %>
            <div class="card mb-4">
                <div class="card-header bg-warning text-dark">Add New Vehicle</div>
                <div class="card-body">
                    <form action="VehicleServlet" method="get">
                        <input type="hidden" name="action" value="insert">
                        <div class="mb-3">
                            <label class="form-label">Type:</label>
                            <select name="type" class="form-select">
                                <option value="Car">Car</option>
                                <option value="Bike">Bike</option>
                            </select>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Brand:</label>
                            <input type="text" name="brand" class="form-control" required />
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Model:</label>
                            <input type="text" name="model" class="form-control" required />
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Price per day:</label>
                            <input type="number" step="0.01" name="pricePerDay" class="form-control" required />
                        </div>
                        <div class="mb-3 form-check">
                            <input type="checkbox" name="available" class="form-check-input" id="availableCheck" />
                            <label class="form-check-label" for="availableCheck">Available</label>
                        </div>
                        <button type="submit" class="btn btn-primary">Add Vehicle</button>
                    </form>
                </div>
            </div>
            <% } else { %>
            <div class="alert alert-danger">
                Invalid option or insufficient permissions.
            </div>
            <% } %>
        </section>
    </div>
</div>

<jsp:include page="footer.jsp" />
