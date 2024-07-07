package Controller;

import Model.Cart;
import Model.OrderDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/order")
public class OrderServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("userId") == null) {
                throw new ServletException("Devi essere loggato");
            }
            session.setAttribute("orders", OrderDAO.getOrders((int) session.getAttribute("userId")));
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/order.jsp");  //tenere d'occhio
            dispatcher.forward(request, response);
        } catch (ServletException e) {
            String contextPath = request.getContextPath();
            response.sendRedirect(contextPath + "/");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("userId") == null) {
                throw new ServletException("Errore, l'utente non ha effettuato l'accesso");
            } else if (session.getAttribute("cart") == null) {
                throw new ServletException("Errore, il carrello è vuoto");
            }
            Cart cart = (Cart) session.getAttribute("cart");
            if(cart.getComics().isEmpty()) {
                response.setStatus(response.SC_UNAUTHORIZED);
                return;
            }
            if(!OrderDAO.doSave((int) session.getAttribute("userId"), cart)){
                throw new ServletException("Errore nella creazione dell'ordine");
            }
            session.setAttribute("cart", new Cart());
            response.setStatus(response.SC_OK);
        } catch (ServletException e) {
            e.printStackTrace(System.out);
            response.setStatus(response.SC_BAD_REQUEST);
        }
    }
}