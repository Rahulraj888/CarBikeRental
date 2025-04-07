<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    request.setAttribute("pageTitle", "About Us - Car & Bike Rental Service");
%>
<jsp:include page="header.jsp" />

<main class="container-fluid p-0 flex-grow-1">

  <!-- Hero Banner Section -->
  <div class="position-relative text-center text-white" 
       style="background: #1F51FF url('images/rental_hero.png') center center/cover no-repeat; min-height: 50vh;">
    <div class="d-flex flex-column justify-content-center align-items-center h-100">
      <h1 class="display-4 fw-bold mb-3">About Car & Bike Rental Service</h1>
      <p class="lead">Delivering reliable and efficient rental solutions since 2025.</p>
      <a href="contact.jsp" class="btn btn-outline-light btn-lg mt-3">Get in Touch</a>
    </div>
  </div>
  <!-- End Hero Banner Section -->

  <!-- About Content Section -->
  <div class="container my-5">
    <div class="row g-4">
      <!-- Our Story Card -->
      <div class="col-md-4">
        <div class="card h-100 shadow">
          <div class="card-body">
            <h3 class="card-title">Our Story</h3>
            <p class="card-text">
              Founded in 2025, we have become one of the most trusted car and bike rental services in the region.
              Our commitment is to provide reliable, affordable, and efficient transportation solutions.
            </p>
          </div>
        </div>
      </div>
      <!-- Our Mission Card -->
      <div class="col-md-4">
        <div class="card h-100 shadow">
          <div class="card-body">
            <h3 class="card-title">Our Mission</h3>
            <p class="card-text">
              We strive to offer exceptional rental services focused on customer satisfaction, safety, and innovation.
              Our goal is to exceed expectations every step of the way.
            </p>
          </div>
        </div>
      </div>
      <!-- Our Team Card -->
      <div class="col-md-4">
        <div class="card h-100 shadow">
          <div class="card-body">
            <h3 class="card-title">Our Team</h3>
            <p class="card-text">
              Our dedicated team of industry experts is passionate about delivering the best rental experience.
              From support to maintenance, we work together to ensure your journey is smooth and enjoyable.
            </p>
          </div>
        </div>
      </div>
    </div>
  </div>
  <!-- End About Content Section -->

  <!-- Partners Section -->
  <div class="container-fluid py-5 bg-light border-top border-bottom">
    <div class="container">
      <h2 class="text-center mb-5 fw-bold">Our Partners</h2>
      <div class="row row-cols-2 row-cols-md-4 g-4">
        <div class="col text-center">
          <img src="images/honda_logo.png" alt="Honda" class="img-fluid d-block mx-auto" style="max-height:80px;">
        </div>
        <div class="col text-center">
          <img src="images/yamaha_logo.png" alt="Yamaha" class="img-fluid d-block mx-auto" style="max-height:80px;">
        </div>
        <div class="col text-center">
          <img src="images/toyota_logo.png" alt="Toyota" class="img-fluid d-block mx-auto" style="max-height:80px;">
        </div>
        <div class="col text-center">
          <img src="images/ford_logo.png" alt="Ford" class="img-fluid d-block mx-auto" style="max-height:80px;">
        </div>
      </div>
    </div>
  </div>
  <!-- End Partners Section -->

</main>

<jsp:include page="footer.jsp" />
