package com.groupb.rental.servlet;

import static org.mockito.Mockito.*;
import org.junit.Before;
import org.junit.Test;
import com.groupb.rental.model.User;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.*;

/**
 * Tests for BookingServlet to verify request handling and forwarding/redirecting behavior.
 * Uses Mockito to simulate servlet request and response.
 */
public class BookingServletTest {

    private BookingServlet servlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private RequestDispatcher dispatcher;

    /**
     * setUp() method initializes mocks and stubs common behaviors for all tests.
     */
    @Before
    public void setUp() {
        servlet = new BookingServlet();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        dispatcher = mock(RequestDispatcher.class);

        // Stub basic methods: getSession() returns our mocked session,
        // getRequestDispatcher returns our mocked dispatcher.
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);

        // Stub getRequestURL() to return a dummy valid URL
        when(request.getRequestURL()).thenReturn(new StringBuffer("http://localhost:8080/BookingServlet"));
    }

    /**
     * Tests that when no user is logged in, the servlet redirects to login.jsp.
     */
    @Test
    public void testUserNotLoggedIn() throws Exception {
        // Setup: session does not contain a user
        when(session.getAttribute("user")).thenReturn(null);
        when(request.getParameter("action")).thenReturn("list");

        // Execute doGet() and verify redirect
        servlet.doGet(request, response);
        verify(response).sendRedirect("login.jsp");
    }

    /**
     * Tests that when a valid user is logged in and action=list,
     * the servlet forwards to the dashboard with a booking list set in the request.
     */
    @Test
    public void testActionList() throws Exception {
        // Create a test user and stub the session with it.
        User user = new User();
        user.setId(1);
        user.setUsername("testUser");
        user.setPassword("testPass");
        user.setEmail("test@example.com");
        user.setRole("customer");
        
        when(session.getAttribute("user")).thenReturn(user);
        when(request.getParameter("action")).thenReturn("list");

        // Execute doGet()
        servlet.doGet(request, response);

        // Verify that a bookingList attribute is set and request is forwarded
        verify(request).setAttribute(eq("bookingList"), any());
        verify(dispatcher).forward(request, response);
    }

    /**
     * Tests that when an unknown action is provided, the servlet redirects to the list action.
     */
    @Test
    public void testActionDefaultRedirect() throws Exception {
        User user = new User();
        user.setId(1);
        user.setUsername("testUser");
        user.setPassword("testPass");
        user.setEmail("test@example.com");
        user.setRole("customer");
        
        when(session.getAttribute("user")).thenReturn(user);
        when(request.getParameter("action")).thenReturn("unknown");

        servlet.doGet(request, response);

        // Verify redirection to default list action
        verify(response).sendRedirect("BookingServlet?action=list");
    }

    /**
     * Tests that when doPost() is called without a logged-in user, the servlet redirects to login.jsp.
     */
    @Test
    public void testDoPostNotLoggedIn() throws Exception {
        when(session.getAttribute("user")).thenReturn(null);
        servlet.doPost(request, response);
        verify(response).sendRedirect("login.jsp");
    }
}
