package com.groupb.rental.servlet;


import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class ContactServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve contact form parameters
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String message = request.getParameter("message");

        // Here you might store the message in a database or send an email.
        // For demonstration, we simply print it to the console.
        System.out.println("Contact Message Received:");
        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
        System.out.println("Message: " + message);

        // Pass a success message back to the contact page
        request.setAttribute("successMessage", "Thank you for contacting us. We will get back to you soon!");
        RequestDispatcher dispatcher = request.getRequestDispatcher("contact.jsp");
        dispatcher.forward(request, response);
    }
}