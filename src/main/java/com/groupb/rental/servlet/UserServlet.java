package com.groupb.rental.servlet;

import com.groupb.rental.bean.User;
import com.groupb.rental.dao.UserDAO;
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
            default: // loginPage
                RequestDispatcher dispatcherLogin = request.getRequestDispatcher("login.jsp");
                dispatcherLogin.forward(request, response);
                break;
        }
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if("register".equals(action)) {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String email = request.getParameter("email");
            // Default role: customer
            User user = new User(0, username, password, email, "customer");
            boolean registered = UserDAO.registerUser(user);
            if(registered) {
                response.sendRedirect("login.jsp");
            } else {
                response.sendRedirect("register.jsp?error=Registration failed");
            }
        } else if("login".equals(action)) {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            User user = UserDAO.login(username, password);
            if(user != null) {
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                response.sendRedirect("dashboard.jsp");
            } else {
                response.sendRedirect("login.jsp?error=Invalid credentials");
            }
        }
    }
}