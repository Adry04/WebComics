package Controller;

import Model.Cart;
import Model.CartDAO;
import Model.Comic;
import Model.ComicDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Comic> comics = ComicDAO.getComics();
            request.setAttribute("comics", comics);
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/WEB-INF/admin/comic.jsp");
            rd.forward(request, response);
        } catch (ServletException e) {
            System.out.println(e);
            String contextPath = request.getContextPath();
            response.sendRedirect(contextPath + "/");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession(false);
            String ISBN = request.getParameter("ISBN");
            int quantita = Integer.parseInt(request.getParameter("quantita"));
            String type = request.getParameter("requestType");
            if (type.equals("add")) {
                if (session.getAttribute("userId") != null) {
                    if (CartDAO.isIn(ISBN, (int) session.getAttribute("userId"))) {
                        if (!CartDAO.addQuantity(ISBN, (int) session.getAttribute("userId"), quantita)) {
                            throw new ServletException("Errore aggiunta quantità");
                        }
                    } else {
                        if (!CartDAO.addComic(ISBN, (int) session.getAttribute("userId"), quantita)) {
                            throw new ServletException("Errore aggiunta fumetto nel carrello");
                        }
                    }
                } else {
                    List<Comic> cartComics = new ArrayList<>();
                    if (session.getAttribute("cart") == null) {
                        Cart cart = new Cart(cartComics);
                        session.setAttribute("cart", cart);
                        cart.updateQuantity(ISBN, quantita);
                    } else {
                        Cart cart = (Cart) session.getAttribute("cart");
                        int oldQuantity = cart.getQuantity(ISBN);
                        cart.updateQuantity(ISBN, (oldQuantity + quantita));
                    }
                }
            }
        } catch (ServletException e) {
            System.err.println(e);
            RequestDispatcher rs = request.getRequestDispatcher("/");
            rs.forward(request, response);
        }
    }
}