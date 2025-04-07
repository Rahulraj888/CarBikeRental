<%@ page contentType="text/html;charset=UTF-8" language="java"
	import="java.util.List, com.groupb.rental.model.Booking, com.groupb.rental.dao.BookingDAO, com.groupb.rental.model.User"%>
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
			<div class="card">
				<div class="card-header bg-primary text-white">Menu</div>
				<div class="list-group list-group-flush">
					<!-- Profile Info link -->
					<a href="dashboard.jsp?option=profile"
						class="list-group-item list-group-item-action <%="profile".equals(option) ? "active" : ""%>">
						Profile Info </a>

					<!-- If user is admin, show "Add Vehicle"; otherwise show "Bookings" -->
					<%
					if ("admin".equalsIgnoreCase(user.getRole())) {
					%>
					<a href="dashboard.jsp?option=addVehicle"
						class="list-group-item list-group-item-action <%="addVehicle".equals(option) ? "active" : ""%>">
						Add Vehicle </a>
					<%
					} else {
					%>
					<a href="dashboard.jsp?option=bookings"
						class="list-group-item list-group-item-action <%="bookings".equals(option) ? "active" : ""%>">
						Bookings </a>
					<%
					}
					%>
					<!-- Logout link (visible to all logged-in users) -->
					<a href="UserServlet?action=logout"
						class="list-group-item list-group-item-action text-danger">
						Logout </a>
				</div>
			</div>
		</aside>

		<!-- Main Content -->
		<section class="col-md-9">
			<!-- Profile Section -->
			<%
			if ("profile".equals(option)) {
			%>
			<div class="card mb-4">
				<div class="card-header bg-info text-white">Profile
					Information</div>
				<div class="card-body">
					<p>
						<strong>Username:</strong>
						<%=user.getUsername()%></p>
					<p>
						<strong>Email:</strong>
						<%=user.getEmail()%></p>
					<p>
						<strong>Role:</strong>
						<%=user.getRole()%></p>
				</div>
			</div>

			<!-- Bookings Section (for non-admin) -->
			<%
			} else if ("bookings".equals(option) && !"admin".equalsIgnoreCase(user.getRole())) {
			int userId = user.getId();
			List<Booking> bookingList = BookingDAO.getBookingsByUser(userId);
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
								<%
								if (bookingList != null && !bookingList.isEmpty()) {
									for (Booking b : bookingList) {
								%>
								<tr>
									<td><%=b.getId()%></td>
									<td><%=b.getVehicleId()%></td>
									<td><%=b.getBookingDate()%></td>
									<td><%=b.getReturnDate()%></td>
									<td>$<%=b.getTotalCost()%></td>
									<td><%=b.getStatus()%></td>
								</tr>
								<%
								}
								} else {
								%>
								<tr>
									<td colspan="6" class="text-center">No bookings found.</td>
								</tr>
								<%
								}
								%>
							</tbody>
						</table>
					</div>
				</div>
			</div>

			<!-- Add Vehicle Section (for admin) -->
			<%
			} else if ("addVehicle".equals(option) && "admin".equalsIgnoreCase(user.getRole())) {
			%>
			<div class="card mb-4">
				<div class="card-header bg-warning text-dark">Add New Vehicle
				</div>
				<div class="card-body">
					<form action="VehicleServlet" method="get">
						<input type="hidden" name="action" value="insert">
						<div class="mb-3">
							<label class="form-label">Type:</label> <select name="type"
								class="form-select">
								<option value="Car">Car</option>
								<option value="Bike">Bike</option>
							</select>
						</div>
						<div class="mb-3">
							<label class="form-label">Brand:</label> <input type="text"
								name="brand" class="form-control" required />
						</div>
						<div class="mb-3">
							<label class="form-label">Model:</label> <input type="text"
								name="model" class="form-control" required />
						</div>
						<div class="mb-3">
							<label class="form-label">Price per day:</label> <input
								type="number" step="0.01" name="pricePerDay"
								class="form-control" required />
						</div>
						<div class="mb-3 form-check">
							<input type="checkbox" name="available" class="form-check-input"
								id="availableCheck" /> <label class="form-check-label"
								for="availableCheck">Available</label>
						</div>
						<button type="submit" class="btn btn-primary">Add Vehicle</button>
					</form>
				</div>
			</div>

			<%
			} else {
			// If someone tries to access an admin section as non-admin or vice-versa,
			// or if the 'option' is invalid, just show a generic message.
			%>
			<div class="alert alert-danger">Invalid option or insufficient
				permissions.</div>
			<%
			}
			%>
		</section>
	</div>
</div>

<jsp:include page="footer.jsp" />
