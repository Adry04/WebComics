//Login utente
package Controller;

import java.io.*;
import java.sql.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import Model.*;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.owasp.encoder.Encode;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession(false);
        if(session != null && session.getAttribute("userId") != null){
            String contextPath = request.getContextPath();
            response.sendRedirect(contextPath + "/");
            return;
        }

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/login.jsp");  //tenere d'occhio
        dispatcher.forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession s = request.getSession(false);
        String contextPath = request.getContextPath();
        if(s != null && s.getAttribute("userId") != null){
            response.sendRedirect(contextPath + "/");
            return;
        }
        try {
            Connection conn = ConPool.getConnection();
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            email = Encode.forHtml(email);
            password = Encode.forHtml(password);
            String emailPattern =  "^[a-zA-Z0-9._-]+@[a-zA-Z0-9_.-]+\\.[a-zA-Z]{2,}$";
            String passwordPattern = "^(?=.*[0-9])(?=.*[!@#$%^&*])(?=.*[A-Z]).{6,}$";
            if(!email.matches(emailPattern)) {
                request.setAttribute("error", "Email non valida");
                throw new ServletException("Email non valida");
            } if (!password.matches(passwordPattern)){
                request.setAttribute("error", "La password deve contenere almeno 6 caratteri, una maiuscola, un carattere speciale e un numero.");
                throw new ServletException("La password deve contenere almeno 6 caratteri, una maiuscola, un carattere speciale e un numero.");
            }
            String query = "SELECT id, nome, cognome, isAdmin, email, pass FROM utente WHERE email = ?";
            PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                String hashedPassword = rs.getString("pass");
                if(Hash.checkPassword(password, hashedPassword)){
                    HttpSession session = request.getSession();
                    session.setAttribute("email", email);
                    session.setAttribute("nome", rs.getString("nome"));
                    session.setAttribute("cognome", rs.getString("cognome"));
                    session.setAttribute("isAdmin", rs.getBoolean("isAdmin"));
                    session.setAttribute("userId", rs.getInt("id"));
                    if (session.getAttribute("cart") != null) {
                        Cart cart = (Cart) session.getAttribute("cart");
                        List<Comic> comics = cart.getComics();
                        Cart userCart = CartDAO.getCart(rs.getInt("id"));
                        List<Comic> userComics = userCart.getComics();
                        Map map = cart.getQuantities();
                        Map userMap = userCart.getQuantities();
                        for (Comic comic : comics) {
                            if (userComics.contains(comic)) {
                                if(!CartDAO.changeQuantity(comic.getISBN(), rs.getInt("id"), ((int) map.get(comic.getISBN()) + (int) userMap.get(comic.getISBN())))) {
                                    throw new ServletException("Errore nel cambio quantità");
                                }
                            } else {
                                if(!CartDAO.addComic(rs.getInt("id"), comic, (int) map.get(comic.getISBN()))) {
                                    throw new ServletException("Errore nell'aggiungere");
                                }
                            }
                        }
                    } else {
                        Cart cart = CartDAO.getCart(rs.getInt("id"));
                        session.setAttribute("cart", cart);
                    }
                    response.sendRedirect(contextPath + "/");
                } else {
                    request.setAttribute("error", "Password sbagliata");
                    throw new ServletException("Password sbagliata");
                }
            } else {
                request.setAttribute("error", "Utente non trovato, ritenta");
                throw new ServletException("Utente non trovato, ritenta");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ServletException e) {
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/login.jsp");  //tenere d'occhio
            dispatcher.forward(request, response);
        }
    }
}