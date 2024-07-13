//Registrazioni utente
package Controller;

import java.io.*;
import java.sql.*;
import java.util.Objects;
import Model.*;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.owasp.encoder.Encode;

@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession(false);
        if(session != null && session.getAttribute("userId") != null){
            String contextPath = request.getContextPath();
            response.sendRedirect(contextPath + "/");
            return;
        }
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/registration.jsp");  //tenere d'occhio
        dispatcher.forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession s = request.getSession(false);
        if(s != null && s.getAttribute("userId") != null){
            String contextPath = request.getContextPath();
            response.sendRedirect(contextPath + "/");
            return;
        }
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String passwordConfirm = request.getParameter("passwordConfirm");
        email = Encode.forHtml(email);
        password = Encode.forHtml(password);
        String emailPattern = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9_.-]+\\.[a-zA-Z]{2,}$";
        String passwordPattern = "^(?=.*[0-9])(?=.*[!@#$%^&*])(?=.*[A-Z]).{6,}$";
        try {
            if(!email.matches(emailPattern)) {
                request.setAttribute("error", "Email non valida");
                throw new ServletException("Email non valida");
            } if (!password.matches(passwordPattern)){
                request.setAttribute("error", "La password deve contenere almeno 6 caratteri, una maiuscola, un carattere speciale e un numero.");
                throw new ServletException("La password deve contenere almeno 6 caratteri, una maiuscola, un carattere speciale e un numero.");
            }
            if (UserDAO.existsUser(email)) {
                request.setAttribute("error", "Esiste già un utente con questa email");
                throw new ServletException("Email already exists");
            }
            if (password.equals(passwordConfirm)) { //se non esiste già la mail e la password è confermata
                User user = new User(firstName, lastName, email, false);
                UserDAO userDao = new UserDAO();
                if (!userDao.doSave(user, password)) {
                    request.setAttribute("error", "Errore nel salvataggio dei dati, ritenta");
                    throw new ServletException("Errore nel salvataggio dei dati");
                }
            } else {
                request.setAttribute("error", "Le due password devono coincidere");
                throw new ServletException("Le due password devono coincidere");
            }
            if (Objects.requireNonNull(s).getAttribute("cart") != null) {
                Cart cart = (Cart) s.getAttribute("cart");
                int id;
                if ((id = UserDAO.getUserId(email)) > 0) {
                    if (!CartDAO.addCart(cart, id)) {
                        throw new ServletException("Errore nel caricamento del carrello");
                    }
                } else {
                    throw new ServletException("Errore nel prendere l'id");
                }
            }
            s.invalidate();
            String contextPath = request.getContextPath();
            response.sendRedirect(contextPath + "/login");
        } catch (SQLException e) {
            response.setStatus(response.SC_INTERNAL_SERVER_ERROR);
        } catch (ServletException e) {
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/registration.jsp");
            dispatcher.forward(request, response);
        }
    }
}