//Preleva gli indirizzi
package Controller;

import Model.UserDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;


@WebServlet("/address")
public class AddressServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("userId") == null) {
                throw new ServletException("Devi essere loggato");
            }
            request.setAttribute("address", UserDAO.getAllAddresses((int) session.getAttribute("userId")));
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/address.jsp");
            dispatcher.forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace(System.out);
            String contextPath = request.getContextPath();
            response.sendRedirect(contextPath + "/");
        } catch (SQLException e) {
            e.printStackTrace(System.out);
            response.setStatus(response.SC_INTERNAL_SERVER_ERROR);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("userId") == null) {
                throw new ServletException("Devi essere loggato");
            }
            if(request.getParameter("requestType") == null || request.getParameter("id") == null) {
                throw new ServletException("Errore di parametri");
            }
            String type = request.getParameter("requestType");
            int id = Integer.parseInt(request.getParameter("id"));
            if(type.equals("delete")) {
                if(!UserDAO.doDeleteAddress((Integer) session.getAttribute("userId"), id)) {
                    throw new ServletException("Errore eliminazione indirizzo");
                }
            }
        } catch (ServletException e) {
            e.printStackTrace(System.out);
            String contextPath = request.getContextPath();
            response.sendRedirect(contextPath + "/");
        } catch (SQLException e) {
            e.printStackTrace(System.out);
            response.setStatus(response.SC_INTERNAL_SERVER_ERROR);
        }
    }
}