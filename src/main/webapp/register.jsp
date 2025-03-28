<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    request.setAttribute("pageTitle", "Register");
%>
<jsp:include page="header.jsp" />

<main class="container my-4 flex-grow-1">
  <div class="row justify-content-center">
    <!-- Restrict form width on md+ screens -->
    <div class="col-md-6 col-lg-4">
      <h2 class="text-center mb-4">Register</h2>
      <form action="UserServlet" method="post" class="card p-4">
          <input type="hidden" name="action" value="register">
          <div class="mb-3">
            <label class="form-label">Username:</label>
            <input type="text" name="username" class="form-control" required>
          </div>
          <div class="mb-3">
            <label class="form-label">Password:</label>
            <input type="password" name="password" class="form-control" required>
          </div>
          <div class="mb-3">
            <label class="form-label">Email:</label>
            <input type="email" name="email" class="form-control" required>
          </div>
          <button type="submit" class="btn btn-primary w-100">Register</button>
      </form>
      <p class="mt-3 text-center">
        Already have an account? <a href="UserServlet?action=loginPage">Login here</a>
      </p>
<%
    String regError = request.getParameter("error");
    if (regError != null) {
%>
      <p class="text-danger text-center"><%= regError %></p>
<%
    }
%>
    </div> <!-- /.col -->
  </div> <!-- /.row -->
</main>

<jsp:include page="footer.jsp" />
