<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="header.jsp" />

<main class="container my-4 flex-grow-1">
  <div class="row justify-content-center">
    <!-- Restrict the form width on md+ screens -->
    <div class="col-md-6 col-lg-5">
      <h1 class="text-center mb-4">Contact Us</h1>
<%
    // Retrieve any success message set by the servlet
    String successMessage = (String) request.getAttribute("successMessage");
    if (successMessage != null && !successMessage.trim().isEmpty()) {
%>
      <div class="alert alert-success"><%= successMessage %></div>
<%
    }
%>
      <p>If you have any queries, please fill out the form below:</p>
      <form action="ContactServlet" method="post" class="card p-4">
          <div class="mb-3">
            <label for="name" class="form-label">Your Name:</label>
            <input type="text" id="name" name="name" class="form-control" required>
          </div>
          <div class="mb-3">
            <label for="email" class="form-label">Your Email:</label>
            <input type="email" id="email" name="email" class="form-control" required>
          </div>
          <div class="mb-3">
            <label for="message" class="form-label">Message:</label>
            <textarea id="message" name="message" rows="5" class="form-control" required></textarea>
          </div>
          <button type="submit" class="btn btn-primary w-100">Send Message</button>
      </form>
    </div> <!-- /.col -->
  </div> <!-- /.row -->
</main>

<jsp:include page="footer.jsp" />
