package Controller;

import Model.Cart;
import Model.Comic;
import com.google.gson.Gson;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/WEB-INF/cart.jsp");
            rd.forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace(System.out);
            String contextPath = request.getContextPath();
            response.sendRedirect(contextPath + "/");
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            String ISBN = request.getParameter("ISBN");
            int quantita = Integer.parseInt(request.getParameter("quantita"));
            if(quantita < 1) {
                throw new ServletException("Errore di quantità");
            }
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
                        if (!cartComics.contains(comic)) {
                            cartComics.add(comic);
                            cart.setComics(cartComics);
                        }
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
                        if (!cartComics.contains(comic)) {
                            cartComics.add(comic);
                            cart.setComics(cartComics);
                        }
                        cart.updateQuantity(ISBN, (cart.getQuantity(ISBN) + quantita));
                        session.setAttribute("cart", cart);
                    }
                }
                response.setStatus(HttpServletResponse.SC_OK);
            } else if(type.equals("remove")) {
                List<Comic> cartComics;
                Cart cart = (Cart) session.getAttribute("cart");
                cartComics = cart.getComics();
                int quantity = cart.getQuantity(comic.getISBN());
                if((quantity - quantita) < 0) {
                    cartComics.remove(comic);
                    cart.setComics(cartComics);
                } else {
                    cart.updateQuantity(comic.getISBN(), (quantity - quantita));
                }
            }
        } catch (ServletException e) {
            e.printStackTrace(System.out);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }
}