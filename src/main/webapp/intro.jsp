<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    request.setAttribute("pageTitle", "Welcome to Car & Bike Rental Service");
%>
<jsp:include page="header.jsp" />

<main class="container my-4 flex-grow-1">
<!-- Centered heading with more spacing -->
<h1 class="text-center my-5">Welcome to Car & Bike Rental Service</h1>

<!-- A card with auto horizontal margin, some vertical margin, and a max width -->
<div class="card p-4 mx-auto my-5" style="max-width: 600px;">
  <h2 class="text-center">Your One-Stop Rental Solution</h2>
  <p>We provide premium car and bike rental services at affordable prices. Explore our range of vehicles and experience the best service in town.</p>
  <p>Whether you need a car for a business trip or a bike for a quick ride, we have the perfect vehicle for you!</p>
</div>

<jsp:include page="footer.jsp" />
