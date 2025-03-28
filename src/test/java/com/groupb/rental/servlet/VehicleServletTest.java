package com.groupb.rental.servlet;


import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.groupb.rental.servlet.VehicleServlet;
import com.groupb.rental.model.User;
import com.groupb.rental.model.Vehicle;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.*;

import java.io.IOException;

public class VehicleServletTest {

    private VehicleServlet servlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private RequestDispatcher dispatcher;

    @Before
    public void setUp() {
        servlet = new VehicleServlet();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        dispatcher = mock(RequestDispatcher.class);

        when(request.getSession(anyBoolean())).thenReturn(session);
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
    }

    @Test
    public void testListVehicles() throws Exception {
        // If action=list, anyone can see the list
        when(request.getParameter("action")).thenReturn("list");
        // No user needed in session
        servlet.doGet(request, response);

        // Should forward to vehicleList.jsp
        verify(dispatcher).forward(request, response);
    }

    @Test
    public void testAdminActionsWithoutLogin() throws Exception {
        // action=new, but user is not logged in
        when(request.getParameter("action")).thenReturn("new");
        when(session.getAttribute("user")).thenReturn(null);

        servlet.doGet(request, response);
        // Should redirect to login.jsp?error=Unauthorized access
        verify(response).sendRedirect("login.jsp?error=Unauthorized access");
    }

    @Test
    public void testAdminActionsAsCustomer() throws Exception {
        // action=new, but user is a "customer", not "admin"
        when(request.getParameter("action")).thenReturn("new");
        User customer = new User(1, "testCust", "pass", "test@example.com", "customer");
        when(session.getAttribute("user")).thenReturn(customer);

        servlet.doGet(request, response);
        // Should redirect to login.jsp?error=Unauthorized access
        verify(response).sendRedirect("login.jsp?error=Unauthorized access");
    }

    @Test
    public void testNewVehicleAsAdmin() throws Exception {
        // action=new, user is admin
        when(request.getParameter("action")).thenReturn("new");
        User admin = new User(1, "adminUser", "pass", "admin@example.com", "admin");
        when(session.getAttribute("user")).thenReturn(admin);

        servlet.doGet(request, response);
        // Should forward to vehicleForm.jsp
        verify(dispatcher).forward(request, response);
    }

    @Test
    public void testInsertVehicleAsAdmin() throws Exception {
        // action=insert, user is admin
        when(request.getParameter("action")).thenReturn("insert");
        when(request.getParameter("type")).thenReturn("Car");
        when(request.getParameter("brand")).thenReturn("Toyota");
        when(request.getParameter("model")).thenReturn("Camry");
        when(request.getParameter("pricePerDay")).thenReturn("50");
        when(request.getParameter("available")).thenReturn("on");

        User admin = new User(1, "adminUser", "pass", "admin@example.com", "admin");
        when(session.getAttribute("user")).thenReturn(admin);

        servlet.doGet(request, response);
        // Should eventually redirect to VehicleServlet (action=list)
        verify(response).sendRedirect("VehicleServlet");
    }

    @Test
    public void testEditVehicleAsAdmin() throws Exception {
        // action=edit, user is admin
        when(request.getParameter("action")).thenReturn("edit");
        when(request.getParameter("id")).thenReturn("101");

        User admin = new User(1, "adminUser", "pass", "admin@example.com", "admin");
        when(session.getAttribute("user")).thenReturn(admin);

        servlet.doGet(request, response);
        // Should forward to vehicleForm.jsp
        verify(dispatcher).forward(request, response);
    }

    @Test
    public void testDeleteVehicleAsAdmin() throws Exception {
        // action=delete, user is admin
        when(request.getParameter("action")).thenReturn("delete");
        when(request.getParameter("id")).thenReturn("101");

        User admin = new User(1, "adminUser", "pass", "admin@example.com", "admin");
        when(session.getAttribute("user")).thenReturn(admin);

        servlet.doGet(request, response);
        // Should redirect to VehicleServlet
        verify(response).sendRedirect("VehicleServlet");
    }
}