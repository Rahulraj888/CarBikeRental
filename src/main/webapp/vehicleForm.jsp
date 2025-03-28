<%@ page contentType="text/html;charset=UTF-8" language="java" import="com.groupb.rental.bean.Vehicle" %>
<%
    // Set page title
    request.setAttribute("pageTitle", "Vehicle Form");
%>
<jsp:include page="header.jsp" />
<main class="container my-4 flex-grow-1">
<%
    Vehicle vehicle = (Vehicle) request.getAttribute("vehicle");
    boolean isEdit = (vehicle != null);
%>
<h2><%= isEdit ? "Edit Vehicle" : "Add New Vehicle" %></h2>
<form action="VehicleServlet" method="get" class="card p-3">
    <input type="hidden" name="action" value="<%= isEdit ? "update" : "insert" %>" />
<% if (isEdit) { %>
    <input type="hidden" name="id" value="<%= vehicle.getId() %>" />
<% } %>
    <div class="mb-3">
      <label class="form-label">Type:</label>
      <select name="type" class="form-select">
        <option value="Car" <%= (isEdit && "Car".equals(vehicle.getType())) ? "selected" : "" %>>Car</option>
        <option value="Bike" <%= (isEdit && "Bike".equals(vehicle.getType())) ? "selected" : "" %>>Bike</option>
      </select>
    </div>
    <div class="mb-3">
      <label class="form-label">Brand:</label>
      <input type="text" name="brand" class="form-control"
             value="<%= isEdit ? vehicle.getBrand() : "" %>" required />
    </div>
    <div class="mb-3">
      <label class="form-label">Model:</label>
      <input type="text" name="model" class="form-control"
             value="<%= isEdit ? vehicle.getModel() : "" %>" required />
    </div>
    <div class="mb-3">
      <label class="form-label">Price per day:</label>
      <input type="text" name="pricePerDay" class="form-control"
             value="<%= isEdit ? vehicle.getPricePerDay() : "" %>" required />
    </div>
    <div class="mb-3 form-check">
      <input type="checkbox" name="available" class="form-check-input" id="availableCheck"
        <%= (isEdit && vehicle.isAvailable()) ? "checked" : "" %> />
      <label class="form-check-label" for="availableCheck">Available</label>
    </div>
    <button type="submit" class="btn btn-primary">
      <%= isEdit ? "Update" : "Add" %>
    </button>
</form>
<a href="VehicleServlet" class="btn btn-link mt-3">Back to Vehicle List</a>

<jsp:include page="footer.jsp" />
