<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    request.setAttribute("pageTitle", "Login");
    com.groupb.rental.model.User loggedInUser = (com.groupb.rental.model.User) session.getAttribute("user");
    if (loggedInUser != null) {
        // Redirect to profile if the user is already logged in
        response.sendRedirect("profile.jsp");
        return;
    }
%>
<jsp:include page="header.jsp" />

<main class="container my-4">
    <div class="row justify-content-center">
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
