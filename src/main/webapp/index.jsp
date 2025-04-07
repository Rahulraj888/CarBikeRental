<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    request.setAttribute("pageTitle", "Welcome to Car & Bike Rental Service");
%>
<jsp:include page="header.jsp" />

<!-- Main container starts -->
<main class="container-fluid px-0 flex-grow-1">

  <!-- Hero Section (Optional) -->
  <!-- Replace 'images/rental_hero.jpg' with a real image in your project -->
  <div class="position-relative text-center text-white"
       style="background: url('images/rental_hero.png') center center/cover no-repeat; min-height: 50vh;">
    <div class="position-absolute top-0 start-0 w-100 h-100"
         style="background-color: rgba(0, 0, 0, 0.5);">
    </div>
    <div class="d-flex flex-column justify-content-center align-items-center position-relative h-100">
      <h1 class="display-4 fw-bold mb-3">Welcome to Car & Bike Rental Service</h1>
      <p class="lead mb-4">Your One-Stop Rental Solution</p>
      <a class="btn btn-light btn-lg fw-semibold" href="VehicleServlet?action=list">
        View Our Vehicles
      </a>
    </div>
  </div>
  <!-- End Hero Section -->

  <!-- Info Card Section -->
  <div class="container my-5">
    <div class="card p-4 mx-auto shadow" style="max-width: 800px;">
      <h2 class="text-center">Your One-Stop Rental Solution</h2>
      <p class="mt-3">
        We provide premium car and bike rental services at affordable prices. Explore our range of vehicles and experience the best service in town.
      </p>
      <p>
        Whether you need a car for a business trip or a bike for a quick ride, we have the perfect vehicle for you!
      </p>
    </div>
  </div>

  <!-- How to Rent Section -->
  <div class="container text-center my-5">
    <h2 class="fw-bold mb-5">How to Rent?</h2>
    <div class="row row-cols-1 row-cols-md-4 g-4">
      <!-- Step 1 -->
      <div class="col">
        <div class="card h-100 border-0">
          <div class="card-body">
            <img width="140" height="140"
                 src="https://www.vrsitrentals.com/shop/wp-content/uploads/2024/02/1-Select-product.png"
                 alt="Select Product" class="mb-3">
            <p class="fw-normal">Select a Product</p>
          </div>
        </div>
      </div>
      <!-- Step 2 -->
      <div class="col">
        <div class="card h-100 border-0">
          <div class="card-body">
            <img width="140" height="140"
                 src="https://www.vrsitrentals.com/shop/wp-content/uploads/2024/02/2-Easy-KYC.png"
                 alt="Easy KYC" class="mb-3">
            <p class="fw-normal">Easy KYC</p>
          </div>
        </div>
      </div>
      <!-- Step 3 -->
      <div class="col">
        <div class="card h-100 border-0">
          <div class="card-body">
            <img width="140" height="140"
                 src="https://www.vrsitrentals.com/shop/wp-content/uploads/2024/02/3-Pay-once-satisfied.png"
                 alt="Pay Once Satisfied" class="mb-3">
            <p class="fw-normal">Pay Once Satisfied</p>
          </div>
        </div>
      </div>
      <!-- Step 4 -->
      <div class="col">
        <div class="card h-100 border-0">
          <div class="card-body">
            <img width="140" height="140"
                 src="https://www.vrsitrentals.com/shop/wp-content/uploads/2024/02/4-Product-Delivered.png"
                 alt="Product Delivered" class="mb-3">
            <p class="fw-normal">Product Delivered</p>
          </div>
        </div>
      </div>
    </div>
  </div>

  <!-- Why to Rent Out Section -->
  <div class="container-fluid py-5 bg-body-tertiary border-top border-bottom">
    <div class="container text-center">
      <h2 class="fw-bold mb-5">Why to Rent Out?</h2>
      <div class="row row-cols-1 row-cols-md-4 g-4">
        <!-- Reason 1 -->
        <div class="col">
          <div class="card h-100 border-0">
            <div class="card-body">
              <img width="70" height="70"
                   src="https://www.vrsitrentals.com/shop/wp-content/uploads/2024/02/Best-Competitive-Price.png"
                   alt="Best Competitive Price" class="mb-3">
              <p class="fw-normal">Best Competitive Price</p>
            </div>
          </div>
        </div>
        <!-- Reason 2 -->
        <div class="col">
          <div class="card h-100 border-0">
            <div class="card-body">
              <img width="70" height="70"
                   src="https://www.vrsitrentals.com/shop/wp-content/uploads/2024/02/Flexible-Update.png"
                   alt="Flexible Update" class="mb-3">
              <p class="fw-normal">Flexible Update</p>
            </div>
          </div>
        </div>
        <!-- Reason 3 -->
        <div class="col">
          <div class="card h-100 border-0">
            <div class="card-body">
              <img width="70" height="70"
                   src="https://www.vrsitrentals.com/shop/wp-content/uploads/2024/02/Quality-Services.png"
                   alt="Quality Services" class="mb-3">
              <p class="fw-normal">Quality Services</p>
            </div>
          </div>
        </div>
        <!-- Reason 4 -->
        <div class="col">
          <div class="card h-100 border-0">
            <div class="card-body">
              <img width="70" height="70"
                   src="https://www.vrsitrentals.com/shop/wp-content/uploads/2024/02/On-Time-Availability.png"
                   alt="On-Time Availability" class="mb-3">
              <p class="fw-normal">On-Time Availability</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>

</main>
<!-- End main content -->

<jsp:include page="footer.jsp" />
