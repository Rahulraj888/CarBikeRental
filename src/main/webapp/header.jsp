<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title><%= (request.getAttribute("pageTitle") != null)
        ? request.getAttribute("pageTitle")
        : "Car & Bike Rental Service" %></title>
    
    <!-- Bootstrap CSS (CDN) -->
    <link
      rel="stylesheet"
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
      integrity="sha384-9ndCyUaIbzAi2FUVXJi0CjmCapSmO7SnpJef0486qhLnuZ2cdeRhO02iuK6FUUVM"
      crossorigin="anonymous"
    />
    <style>
	    .nav-item a, 
		.navbar-brand {
		    color: white;
		    margin-inline: 10px;
		    font-weight: bold;
		}

		.nav-item a:hover,
		.navbar-brand:hover {
		    color: white;
		    font-size: 1.1em;
		    font-weight: bold;
		}
    </style>
    
</head>
<body class="d-flex flex-column min-vh-100">
    <!-- Navbar -->
    <nav class="navbar navbar-expand-lg" style="background-color:#1F51FF;">
      <div class="container-fluid">
        <a class="navbar-brand" href="intro.jsp">Car & Bike Rental</a>
        <button 
          class="navbar-toggler" 
          type="button" 
          data-bs-toggle="collapse" 
          data-bs-target="#navbarNav" 
          aria-controls="navbarNav" 
          aria-expanded="false" 
          aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
          <ul class="navbar-nav ms-auto">
            <li class="nav-item"><a class="nav-link" href="intro.jsp">Home</a></li>
            <li class="nav-item"><a class="nav-link" href="about.jsp">About Us</a></li>
            <li class="nav-item"><a class="nav-link" href="contact.jsp">Contact Us</a></li>
<%
    com.groupb.rental.model.User user = (com.groupb.rental.model.User) session.getAttribute("user");
    if(user != null) {
%>
            <li class="nav-item"><a class="nav-link" href="dashboard.jsp">Profile</a></li>
<%
    } else {
%>
            <li class="nav-item"><a class="nav-link" href="login.jsp">Login</a></li>
            <li class="nav-item"><a class="nav-link" href="register.jsp">Register</a></li>
<%
    }
%>
            <li class="nav-item"><a class="nav-link" href="BookingServlet?action=list">Dashboard</a></li>
            <li class="nav-item"><a class="nav-link" href="VehicleServlet?action=list">Vehicles</a></li>
          </ul>
        </div>
      </div>
    </nav>

    <!-- Main container starts -->
