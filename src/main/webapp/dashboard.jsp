<%@ page contentType="text/html;charset=UTF-8" language="java" import="java.util.List, com.groupb.rental.bean.Booking, com.groupb.rental.dao.BookingDAO" %>
<%
    // Assuming the user is logged in, get the booking list from the DAO.
    // (Not the best practice to access DAO directly in JSP, but acceptable for demonstration.)
    int userId = ((com.groupb.rental.bean.User) session.getAttribute("user")).getId();
    List<Booking> bookingList = BookingDAO.getBookingsByUser(userId);
    request.setAttribute("bookingList", bookingList);
%>
<jsp:include page="header.jsp" />
<main class="container my-4 flex-grow-1">
<h2>Dashboard</h2>
<p>Welcome to your dashboard! Here are your bookings:</p>
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
