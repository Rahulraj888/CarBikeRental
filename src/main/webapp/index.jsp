<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Index</title>
    <!-- Bootstrap CSS (CDN) -->
    <link 
      rel="stylesheet" 
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
      integrity="sha384-ENjdO4Dr2bkBIFxQpeoBjaKUMH6kD69fJzvR8tN0EU4cNnbObyHb+0aWAI6yBiAO"
      crossorigin="anonymous"
    />
</head>
<body>
<%
    if (session != null && session.getAttribute("user") != null) {
        response.sendRedirect("dashboard.jsp");
    } else {
        response.sendRedirect("intro.jsp");
    }
%>
</body>
</html>