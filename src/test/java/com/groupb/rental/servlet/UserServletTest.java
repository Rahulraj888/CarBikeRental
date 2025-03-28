package com.groupb.rental.servlet;

import static org.mockito.Mockito.*;
import org.junit.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.groupb.rental.model.User;

public class UserServletTest {

    private UserServlet servlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private RequestDispatcher dispatcher;

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

    @Test
    public void testRegisterPageAction() throws Exception {
        when(request.getParameter("action")).thenReturn("registerPage");
        servlet.doGet(request, response);
        // Should forward to register.jsp
        verify(dispatcher).forward(request, response);
    }

    @Test
    public void testLogoutAction() throws Exception {
        when(request.getParameter("action")).thenReturn("logout");
        when(session.getAttribute("user")).thenReturn(new User());
        servlet.doGet(request, response);
        // Session should be invalidated and redirect to login.jsp
        verify(session).invalidate();
        verify(response).sendRedirect("login.jsp");
    }

    @Test
    public void testDefaultLoginPageAction() throws Exception {
        when(request.getParameter("action")).thenReturn(null);
        servlet.doGet(request, response);
        // Should forward to login.jsp by default
        verify(dispatcher).forward(request, response);
    }
}
