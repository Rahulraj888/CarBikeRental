<%@ page contentType="text/html;charset=UTF-8" language="java" import="java.util.List, com.groupb.rental.bean.Booking" %>
<%
    request.setAttribute("pageTitle", "My Bookings");
%>
<jsp:include page="header.jsp" />

<main class="container my-4 flex-grow-1">
<h2>My Bookings</h2>
<a href="dashboard.jsp" class="btn btn-secondary mb-3">Back to Dashboard</a>
<table class="table table-bordered table-striped">
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
  List<Booking> bookingList = (List<Booking>) request.getAttribute("bookingList");
  if (bookingList != null) {
    for (Booking b : bookingList) {
%>
    <tr>
      <td><%= b.getId() %></td>
      <td><%= b.getVehicleId() %></td>
      <td><%= b.getBookingDate() %></td>
      <td><%= b.getReturnDate() %></td>
      <td><%= b.getTotalCost() %></td>
      <td><%= b.getStatus() %></td>
    </tr>
<%
    }
  }
%>
  </tbody>
</table>

<jsp:include page="footer.jsp" />
