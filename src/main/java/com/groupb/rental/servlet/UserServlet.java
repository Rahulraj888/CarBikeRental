package com.groupb.rental.servlet;

import com.groupb.rental.model.User;
import com.groupb.rental.dao.UserDAOInterface;
import com.groupb.rental.dao.UserDAOImpl;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Handles user-related operations such as registration, login, and logout.
 */
public class UserServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(UserServlet.class.getName());

    /**
     * Processes GET requests, which may include forwarding to registration or login pages, or handling logout.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Determine the action parameter to decide which page to display
        String action = request.getParameter("action");
        if(action == null) action = "loginPage";
        switch(action) {
            case "registerPage":
                logger.info("Forwarding to registration page.");
                RequestDispatcher dispatcher = request.getRequestDispatcher("register.jsp");
                dispatcher.forward(request, response);
                break;
            case "logout":
                logger.info("Logging out user.");
                HttpSession session = request.getSession();
                session.invalidate();
                response.sendRedirect("login.jsp");
                break;
            default: 
                logger.info("Forwarding to login page.");
                RequestDispatcher dispatcherLogin = request.getRequestDispatcher("login.jsp");
                dispatcherLogin.forward(request, response);
                break;
        }
    }

    /**
     * Processes POST requests for user registration and login.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        UserDAOInterface userDAO = new UserDAOImpl();
        if("register".equals(action)) {
            // Process user registration
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String email = request.getParameter("email");
            String role = request.getParameter("role"); // Role from the registration form

            logger.info("Attempting to register user: " + username);
            User user = new User();
            user.setId(0);
            user.setUsername(username);
            user.setPassword(password);
            user.setEmail(email);
            user.setRole(role);

            boolean registered = userDAO.registerUser(user);
            if(registered) {
                logger.info("User registered successfully: " + username);
                response.sendRedirect("login.jsp");
            } else {
                logger.warning("User registration failed for: " + username);
                response.sendRedirect("register.jsp?error=Registration failed");
            }
        } else if("login".equals(action)) {
            // Process user login
            String username = request.getParameter("username");
            String password = request.getParameter("password");

            logger.info("Attempting login for user: " + username);
            User user = userDAO.login(username, password);
            if(user != null) {
                logger.info("Login successful for user: " + username);
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                // Redirect to stored URL if any; otherwise go to dashboard.
                String redirectURL = (String) session.getAttribute("redirectAfterLogin");
                if(redirectURL != null) {
                    session.removeAttribute("redirectAfterLogin");
                    response.sendRedirect(redirectURL);
                } else {
                    response.sendRedirect("dashboard.jsp");
                }
            } else {
                logger.warning("Login failed for user: " + username);
                response.sendRedirect("login.jsp?error=Invalid credentials");
            }
        }
    }
}
