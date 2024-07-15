//Login utente
package Controller;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import Controller.Exception.UserNotExists;
import Model.*;
import jakarta.servlet.RequestDispatcher;
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
        if (s != null && s.getAttribute("userId") != null) {
            response.setStatus(response.SC_UNAUTHORIZED);
            return;
        }
        try {
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
            User user = UserDAO.getUserFromEmail(email);
            if(user == null) {
                request.setAttribute("error", "Utente non trovato");
                throw new UserNotExists();
            }
            String hashedPassword = UserDAO.getPassword(Objects.requireNonNull(user).getId());
            if (Hash.checkPassword(password, hashedPassword)) {
                HttpSession session = request.getSession();
                session.setAttribute("email", email);
                session.setAttribute("nome", user.getFirstName());
                session.setAttribute("cognome", user.getLastName());
                session.setAttribute("isAdmin", user.getIsAdmin());
                session.setAttribute("userId", user.getId());
                String token = TokenUtil.generateToken(user);
                Cookie tokenCookie = new Cookie("authToken", token);
                int maxAgeInSeconds = 14 * 24 * 60 * 60; // 14 giorni * 24 ore * 60 minuti * 60 secondi
                tokenCookie.setMaxAge(maxAgeInSeconds);
                tokenCookie.setSecure(true);
                tokenCookie.setPath("tswProject_war_exploded");
                response.addCookie(tokenCookie);
                if (session.getAttribute("cart") != null) {
                    Cart cart = (Cart) session.getAttribute("cart");
                    List<Comic> comics = cart.getComics();
                    Cart userCart = CartDAO.getCart(user.getId());
                    List<Comic> userComics = Objects.requireNonNull(userCart).getComics();
                    Map<String, Integer> map = cart.getQuantities();
                    Map<String, Integer> userMap = userCart.getQuantities();
                    for (Comic comic : comics) {
                        if (userComics.contains(comic)) {
                            int comicQuantity = Integer.parseInt(map.get(comic.getISBN()).toString());
                            int userComicQuantity = Integer.parseInt(userMap.get(comic.getISBN()).toString());
                            CartDAO.changeQuantity(comic.getISBN(), user.getId(), (comicQuantity+userComicQuantity));
                        } else {
                            CartDAO.addComic(user.getId(), comic, map.get(comic.getISBN()));
                        }
                    }
                }
                Cart cart = CartDAO.getCart(user.getId());
                session.setAttribute("cart", cart);
                response.sendRedirect(contextPath + "/");
            } else {
                request.setAttribute("error", "Password sbagliata");
                throw new ServletException("Password sbagliata");
            }
        } catch (UserNotExists e) {
            response.setStatus(response.SC_BAD_REQUEST);
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/login.jsp");
            dispatcher.forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace(System.out);
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/login.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace(System.out);
            response.setStatus(response.SC_INTERNAL_SERVER_ERROR);
        }
    }
}