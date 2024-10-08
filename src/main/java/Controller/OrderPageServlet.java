//Servlet della pagina degli ordini
package Controller;

import Model.OrderDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/order-page")
public class OrderPageServlet extends HttpServlet {
   public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       try {
           HttpSession session = request.getSession(false);
           if (session == null || session.getAttribute("userId") == null) {
               throw new ServletException("Devi essere loggato");
           } else if (request.getParameter("id") == null || request.getParameter("id").isEmpty()) {
               throw new ServletException("Parametro id mancante");
           }
           if(!(Boolean) session.getAttribute("isAdmin")) {
               request.setAttribute("order", OrderDAO.getOrder(Integer.parseInt(request.getParameter("id")), (int) session.getAttribute("userId")));
           } else {
               request.setAttribute("order", OrderDAO.getOrderAdmin(Integer.parseInt(request.getParameter("id"))));
               request.setAttribute("orderUser", OrderDAO.getUserFromOrder(Integer.parseInt(request.getParameter("id"))));
           }
           RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/orderPage.jsp");  //tenere d'occhio
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
}