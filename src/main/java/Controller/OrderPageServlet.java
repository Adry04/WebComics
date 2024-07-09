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
           request.setAttribute("order", OrderDAO.getOrder(Integer.parseInt(request.getParameter("id")), (int) session.getAttribute("userId")));
           RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/orderPage.jsp");  //tenere d'occhio
           dispatcher.forward(request, response);
       } catch (ServletException e) {
           e.printStackTrace(System.out);
           String contextPath = request.getContextPath();
           response.sendRedirect(contextPath + "/");
       }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}