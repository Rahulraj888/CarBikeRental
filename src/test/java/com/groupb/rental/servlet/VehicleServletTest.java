package com.groupb.rental.servlet;

import static org.mockito.Mockito.*;
import org.junit.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.groupb.rental.model.User;
import com.groupb.rental.model.Vehicle;
import java.io.IOException;

/**
 * Tests for VehicleServlet verifying the various vehicle management actions.
 */
public class VehicleServletTest {

    private VehicleServlet servlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private RequestDispatcher dispatcher;

    /**
     * setUp() method initializes mocks for all tests in this class.
     */
    @Before
    public void setUp() {
        servlet = new VehicleServlet();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        dispatcher = mock(RequestDispatcher.class);

        // Set up session and request stubs
        when(request.getSession(anyBoolean())).thenReturn(session);
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
    }

    /**
     * Tests that when action=list, the servlet forwards to vehicleList.jsp.
     */
    @Test
    public void testListVehicles() throws Exception {
        when(request.getParameter("action")).thenReturn("list");
        // No user is required for listing vehicles publicly
        servlet.doGet(request, response);

        // Verify forward to vehicleList.jsp occurs
        verify(dispatcher).forward(request, response);
    }

    /**
     * Tests that if an admin action is requested while no user is logged in,
     * the servlet redirects to login.jsp with an error message.
     */
    @Test
    public void testAdminActionsWithoutLogin() throws Exception {
        when(request.getParameter("action")).thenReturn("new");
        when(session.getAttribute("user")).thenReturn(null);

        servlet.doGet(request, response);
        verify(response).sendRedirect("login.jsp?error=Unauthorized access");
    }

    /**
     * Tests that if a customer (non-admin) attempts an admin action,
     * the servlet redirects to login.jsp with an error.
     */
    @Test
    public void testAdminActionsAsCustomer() throws Exception {
        when(request.getParameter("action")).thenReturn("new");
        User customer = new User(1, "testCust", "Password@123", "test@example.com", "customer");
        when(session.getAttribute("user")).thenReturn(customer);

        servlet.doGet(request, response);
        verify(response).sendRedirect("login.jsp?error=Unauthorized access");
    }

    /**
     * Tests that when an admin requests to add a new vehicle (action=new),
     * the servlet forwards to vehicleForm.jsp.
     */
    @Test
    public void testNewVehicleAsAdmin() throws Exception {
        when(request.getParameter("action")).thenReturn("new");
        User admin = new User(1, "adminUser", "Password@123", "admin@example.com", "admin");
        when(session.getAttribute("user")).thenReturn(admin);

        servlet.doGet(request, response);
        verify(dispatcher).forward(request, response);
    }

    /**
     * Tests that inserting a new vehicle as an admin eventually redirects to the vehicle list.
     */
    @Test
    public void testInsertVehicleAsAdmin() throws Exception {
        when(request.getParameter("action")).thenReturn("insert");
        // Provide vehicle information via request parameters
        when(request.getParameter("type")).thenReturn("Car");
        when(request.getParameter("brand")).thenReturn("Toyota");
        when(request.getParameter("model")).thenReturn("Camry");
        when(request.getParameter("pricePerDay")).thenReturn("50");
        when(request.getParameter("available")).thenReturn("on");

        User admin = new User(1, "adminUser", "Password@123", "admin@example.com", "admin");
        when(session.getAttribute("user")).thenReturn(admin);

        servlet.doGet(request, response);
        verify(response).sendRedirect("VehicleServlet");
    }

    /**
     * Tests that deleting a vehicle as an admin redirects to the vehicle list.
     */
    @Test
    public void testDeleteVehicleAsAdmin() throws Exception {
        when(request.getParameter("action")).thenReturn("delete");
        when(request.getParameter("id")).thenReturn("101");

        User admin = new User(1, "adminUser", "Password@123", "admin@example.com", "admin");
        when(session.getAttribute("user")).thenReturn(admin);

        servlet.doGet(request, response);
        verify(response).sendRedirect("VehicleServlet");
    }
}
