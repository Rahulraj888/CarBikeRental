package com.groupb.rental.servlet;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.groupb.rental.servlet.BookingServlet;
import com.groupb.rental.model.User;
import com.groupb.rental.model.Vehicle;
import com.groupb.rental.dao.VehicleDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.*;

import java.io.IOException;

public class BookingServletTest {

    private BookingServlet servlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private RequestDispatcher dispatcher;

    @Before
    public void setUp() {
        servlet = new BookingServlet();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        dispatcher = mock(RequestDispatcher.class);

        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
    }

    @Test
    public void testUserNotLoggedIn() throws Exception {
        // If session.getAttribute("user") is null, it should redirect to login.jsp
        when(session.getAttribute("user")).thenReturn(null);
        when(request.getParameter("action")).thenReturn("list");

        servlet.doGet(request, response);
        verify(response).sendRedirect("login.jsp");
    }


    @Test
    public void testActionList() throws Exception {
        // If user is logged in, and action=list
        // Should forward to bookingList.jsp with bookingList
        User user = new User(1, "testUser", "testPass", "test@example.com", "customer");
        when(session.getAttribute("user")).thenReturn(user);
        when(request.getParameter("action")).thenReturn("list");

        servlet.doGet(request, response);

        verify(request).setAttribute(eq("bookingList"), any());
        verify(dispatcher).forward(request, response);
    }

    @Test
    public void testActionDefaultRedirect() throws Exception {
        // If user is logged in, but action is something else
        User user = new User(1, "testUser", "testPass", "test@example.com", "customer");
        when(session.getAttribute("user")).thenReturn(user);
        when(request.getParameter("action")).thenReturn("unknown");

        servlet.doGet(request, response);

        verify(response).sendRedirect("BookingServlet?action=list");
    }

    @Test
    public void testDoPostNotLoggedIn() throws Exception {
        // If user is not logged in, doPost should redirect to login
        when(session.getAttribute("user")).thenReturn(null);
        servlet.doPost(request, response);
        verify(response).sendRedirect("login.jsp");
    }

   
}