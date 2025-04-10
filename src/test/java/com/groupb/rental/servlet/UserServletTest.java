package com.groupb.rental.servlet;

import static org.mockito.Mockito.*;
import org.junit.Before;
import org.junit.Test;
import com.groupb.rental.model.User;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.*;

/**
 * Tests for UserServlet verifying correct forwarding for registration, login, and logout.
 */
public class UserServletTest {

    private UserServlet servlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private RequestDispatcher dispatcher;

    /**
     * Set up the mocks used by all the tests in this class.
     */
    @Before
    public void setUp() {
        servlet = new UserServlet();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        dispatcher = mock(RequestDispatcher.class);
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
    }

    /**
     * Tests that a GET request with action "registerPage" is forwarded to register.jsp.
     */
    @Test
    public void testRegisterPageAction() throws Exception {
        when(request.getParameter("action")).thenReturn("registerPage");
        servlet.doGet(request, response);
        // Verify that dispatcher forwards to register.jsp
        verify(dispatcher).forward(request, response);
    }

    /**
     * Tests that a logout action invalidates the session and redirects to login.jsp.
     */
    @Test
    public void testLogoutAction() throws Exception {
        when(request.getParameter("action")).thenReturn("logout");
        // Stub session with a user so that logout makes sense
        when(session.getAttribute("user")).thenReturn(new User());
        servlet.doGet(request, response);
        // Verify session invalidation and redirect to login
        verify(session).invalidate();
        verify(response).sendRedirect("login.jsp");
    }

    /**
     * Tests that the default action (when no action is specified) forwards to login.jsp.
     */
    @Test
    public void testDefaultLoginPageAction() throws Exception {
        // action is null, so it should default to loginPage
        when(request.getParameter("action")).thenReturn(null);
        servlet.doGet(request, response);
        // Verify forwarding to login.jsp
        verify(dispatcher).forward(request, response);
    }
}
