<%@ page contentType="text/html;charset=UTF-8" language="java" import="com.groupb.rental.model.Vehicle" %>
<%
    request.setAttribute("pageTitle", "Book Vehicle");
    // Retrieve the vehicle object, if available.
    Vehicle vehicle = (Vehicle) request.getAttribute("vehicle");
%>
<jsp:include page="header.jsp" />

<main class="container my-4 flex-grow-1">
<h2 class="mb-3">Book Vehicle</h2>
<%
    if (vehicle != null) {
%>
    <div class="alert alert-info">
      <strong>Vehicle:</strong> <%= vehicle.getBrand() %> <%= vehicle.getModel() %> (<%= vehicle.getType() %>) - $<%= vehicle.getPricePerDay() %> per day
    </div>
<%
    }
%>
<form action="BookingServlet" method="post" class="card p-3">
    <input type="hidden" name="vehicleId" value="<%= (vehicle != null) ? vehicle.getId() : 0 %>">
    <div class="mb-3">
      <label class="form-label">Booking Date:</label>
      <input type="date" name="bookingDate" class="form-control" required>
    </div>
    <div class="mb-3">
      <label class="form-label">Return Date:</label>
      <input type="date" name="returnDate" class="form-control" required>
    </div>
    <button type="submit" class="btn btn-primary">Book Now</button>
</form>
<a href="dashboard.jsp" class="btn btn-link mt-3">Back to Dashboard</a>

<jsp:include page="footer.jsp" />
