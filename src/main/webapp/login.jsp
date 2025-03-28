<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    request.setAttribute("pageTitle", "Login");
%>
<jsp:include page="header.jsp" />

<main class="container my-4">
  <div class="row justify-content-center">
    <!-- This column will have a max width on medium screens (md) of 6 columns, 
         and center itself on large screens. Adjust as needed (e.g., col-md-4). -->
    <div class="col-md-6 col-lg-4">
      <h2 class="text-center mb-4">Login</h2>
      <form action="UserServlet" method="post" class="card p-4">
          <input type="hidden" name="action" value="login">
          <div class="mb-3">
            <label class="form-label">Username:</label>
            <input type="text" name="username" class="form-control" required>
          </div>
          <div class="mb-3">
            <label class="form-label">Password:</label>
            <input type="password" name="password" class="form-control" required>
          </div>
          <button type="submit" class="btn btn-primary w-100">Login</button>
      </form>
      <p class="mt-3 text-center">
        Don't have an account? 
        <a href="UserServlet?action=registerPage">Register here</a>
      </p>
<%
    String error = request.getParameter("error");
    if (error != null) {
%>
      <p class="text-danger text-center"><%= error %></p>
<%
    }
%>
    </div> <!-- col -->
  </div> <!-- row -->
</main>

<jsp:include page="footer.jsp" />
