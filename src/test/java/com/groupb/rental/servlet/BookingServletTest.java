package com.groupb.rental.servlet;

import static org.mockito.Mockito.*;
import org.junit.Before;
import org.junit.Test;
import com.groupb.rental.model.User;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.*;

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

        // Stub getSession() and getRequestDispatcher()
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);

        // Stub getRequestURL() so that it returns a valid URL
        when(request.getRequestURL()).thenReturn(new StringBuffer("http://localhost:8080/BookingServlet"));
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
        // When a user is logged in and action=list, it should forward with a bookingList attribute
        User user = new User();
        user.setId(1);
        user.setUsername("testUser");
        user.setPassword("testPass");
        user.setEmail("test@example.com");
        user.setRole("customer");
        
        when(session.getAttribute("user")).thenReturn(user);
        when(request.getParameter("action")).thenReturn("list");

        servlet.doGet(request, response);

        verify(request).setAttribute(eq("bookingList"), any());
        verify(dispatcher).forward(request, response);
    }

    @Test
    public void testActionDefaultRedirect() throws Exception {
        // When a user is logged in and an unknown action is provided,
        // it should redirect to BookingServlet?action=list
        User user = new User();
        user.setId(1);
        user.setUsername("testUser");
        user.setPassword("testPass");
        user.setEmail("test@example.com");
        user.setRole("customer");
        
        when(session.getAttribute("user")).thenReturn(user);
        when(request.getParameter("action")).thenReturn("unknown");

        servlet.doGet(request, response);

        verify(response).sendRedirect("BookingServlet?action=list");
    }

    @Test
    public void testDoPostNotLoggedIn() throws Exception {
        // When user is not logged in, doPost should redirect to login.jsp
        when(session.getAttribute("user")).thenReturn(null);
        servlet.doPost(request, response);
        verify(response).sendRedirect("login.jsp");
    }
}
