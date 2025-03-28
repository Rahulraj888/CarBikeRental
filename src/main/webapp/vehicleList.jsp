<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.util.List"%>
<%@ page import="com.groupb.rental.bean.Vehicle"%>
<%
    request.setAttribute("pageTitle", "Vehicle List");
    List<Vehicle> vehicleList = (List<Vehicle>) request.getAttribute("vehicleList");
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
        <!-- Existing Edit/Delete if needed -->
        <!-- Book button: sends user to booking form with vehicleId -->
        <a href="BookingServlet?action=new&vehicleId=<%= v.getId() %>"
           class="btn btn-primary btn-sm">
          Book
        </a>
      </td>
    </tr>
  <%
      }
    }
  %>
  </tbody>
</table>

<jsp:include page="footer.jsp" />
