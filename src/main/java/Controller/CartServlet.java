package Controller;

import Model.Cart;
import Model.CartDAO;
import Model.Comic;
import Model.ComicDAO;
import com.google.gson.Gson;
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
        HttpSession session = request.getSession();
        String ISBN = request.getParameter("ISBN");
        int quantita = Integer.parseInt(request.getParameter("quantita"));
        String comicJson = request.getParameter("comic");
        Gson gson = new Gson();
        // Parsing della stringa JSON in un oggetto Comic
        Comic comic = gson.fromJson(comicJson, Comic.class);
        String type = request.getParameter("requestType");
        if (type.equals("add")) {
            if (session.getAttribute("userId") != null) {
                List<Comic> cartComics = new ArrayList<>();
                if (session.getAttribute("cart") == null) {
                    cartComics.add(comic);
                    Cart cart = new Cart((Integer) session.getAttribute("userId"), cartComics);
                    cart.updateQuantity(ISBN, quantita);
                    session.setAttribute("cart", cart);
                } else {
                    Cart cart = (Cart) session.getAttribute("cart");
                    cartComics = cart.getComics();
                    cartComics.add(comic);
                    cart.setComics(cartComics);
                    cart.updateQuantity(ISBN, (cart.getQuantity(ISBN) + quantita));
                    session.setAttribute("cart", cart);
                }
            } else {
                List<Comic> cartComics = new ArrayList<>();
                if (session.getAttribute("cart") == null) {
                    Cart cart = new Cart(cartComics);
                    cartComics.add(comic);
                    cart.setComics(cartComics);
                    cart.updateQuantity(ISBN, quantita);
                    session.setAttribute("cart", cart);
                } else {
                    Cart cart = (Cart) session.getAttribute("cart");
                    cartComics = cart.getComics();
                    cartComics.add(comic);
                    cart.setComics(cartComics);
                    cart.updateQuantity(ISBN, (cart.getQuantity(ISBN) + quantita));
                    session.setAttribute("cart", cart);
                }
            }
            response.setStatus(HttpServletResponse.SC_OK);
        }
    }
}