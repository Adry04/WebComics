//Servlet per la gestione dell'accout es logout
package Controller;

import java.io.*;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

import Model.Cart;
import Model.CartDAO;
import Model.Comic;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/account")
public class AccountServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("userId") == null) {
                throw new ServletException("You are not logged in");
            }

            RequestDispatcher rd = getServletContext().getRequestDispatcher("/WEB-INF/account.jsp");
            rd.forward(request, response);
        } catch (ServletException e) {
            System.err.println("\n" + e + "\n");
            String contextPath = request.getContextPath();
            response.sendRedirect(contextPath + "/login");
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String action = request.getParameter("action");
        HttpSession session = request.getSession(false);
        if(action.equals("logout") && session != null) {
            List<Cookie> cookies = List.of(request.getCookies());
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("authToken")) {
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                    break;
                }
            }
            if(session.getAttribute("cart") == null) {
                session.invalidate();
            } else {
                Cart cart = (Cart) session.getAttribute("cart");
                try {
                    CartDAO.addCart(cart, (Integer) session.getAttribute("userId"));
                } catch (SQLException e) {
                    e.printStackTrace(System.out);
                    response.setStatus(response.SC_INTERNAL_SERVER_ERROR);
                }
                session.invalidate();
            }
        }
    }
}