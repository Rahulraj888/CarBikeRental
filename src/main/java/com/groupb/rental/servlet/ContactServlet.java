package com.groupb.rental.servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles contact form submissions.
 * Retrieves form parameters, processes the contact request (e.g., send email or store in database),
 * and then forwards a success message to the contact page.
 */
public class ContactServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(ContactServlet.class.getName());

    /**
     * Processes the POST request from the contact form.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve contact form parameters from the request
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String message = request.getParameter("message");

        // For demonstration, print the message to the console and log it.
        logger.info("Contact Message Received:");
        logger.info("Name: " + name);
        logger.info("Email: " + email);
        logger.info("Message: " + message);

        // In production, you might store the message in the database or send an email.

        // Set a success message as a request attribute and forward to contact.jsp.
        request.setAttribute("successMessage", "Thank you for contacting us. We will get back to you soon!");
        RequestDispatcher dispatcher = request.getRequestDispatcher("contact.jsp");
        dispatcher.forward(request, response);
    }
}
