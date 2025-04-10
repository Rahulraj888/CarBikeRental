package com.groupb.rental.servlet;

import com.groupb.rental.model.User;
import com.groupb.rental.dao.UserDAOInterface;
import com.groupb.rental.dao.UserDAOImpl;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class UserServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if(action == null) action = "loginPage";
        switch(action) {
            case "registerPage":
                RequestDispatcher dispatcher = request.getRequestDispatcher("register.jsp");
                dispatcher.forward(request, response);
                break;
            case "logout":
                HttpSession session = request.getSession();
                session.invalidate();
                response.sendRedirect("login.jsp");
                break;
            default: 
                RequestDispatcher dispatcherLogin = request.getRequestDispatcher("login.jsp");
                dispatcherLogin.forward(request, response);
                break;
        }
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        UserDAOInterface userDAO = new UserDAOImpl();
        if("register".equals(action)) {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String email = request.getParameter("email");
            String role = request.getParameter("role"); // Get role from the registration form
            User user = new User();
            user.setId(0);
            user.setUsername(username);
            user.setPassword(password);
            user.setEmail(email);
            user.setRole(role);
            boolean registered = userDAO.registerUser(user);
            if(registered) {
                response.sendRedirect("login.jsp");
            } else {
                response.sendRedirect("register.jsp?error=Registration failed");
            }
        } else if("login".equals(action)) {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            User user = userDAO.login(username, password);
            if(user != null) {
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                // Check for a stored redirect URL
                String redirectURL = (String) session.getAttribute("redirectAfterLogin");
                if(redirectURL != null) {
                    session.removeAttribute("redirectAfterLogin");
                    response.sendRedirect(redirectURL);
                } else {
                    response.sendRedirect("dashboard.jsp");
                }
            } else {
                response.sendRedirect("login.jsp?error=Invalid credentials");
            }
        }
    }
}
