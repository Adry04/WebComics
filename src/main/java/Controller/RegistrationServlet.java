package Controller;

import java.io.*;
import java.sql.*;

import Model.ConPool;
import Model.User;
import Model.UserDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.owasp.encoder.Encode;

@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {

    public void init() {
    }

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
        String emailPattern = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9_-]+\\.[a-zA-Z]{2,}$";
        String passwordPattern = "^(?=.*[0-9])(?=.*[!@#$%^&*])(?=.*[A-Z]).{6,}$";
        try {
            if(!email.matches(emailPattern)) {
                request.setAttribute("error", "Email non valida");
                throw new ServletException("Email non valida");
            } if (!password.matches(passwordPattern)) {
                request.setAttribute("error", "La password deve contenere almeno 6 caratteri, una maiuscola, un carattere speciale e un numero.");
                throw new ServletException("La password deve contenere almeno 6 caratteri, una maiuscola, un carattere speciale e un numero");
            }
            Connection connection = ConPool.getConnection();
            String query = "SELECT email FROM utente WHERE email = ?";
            PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                connection.close();
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
                connection.close();
            } else {
                connection.close();
                request.setAttribute("error", "Le due password devono coincidere");
                throw new ServletException("Le due password devono coincidere");
            }
            String contextPath = request.getContextPath();
            response.sendRedirect(contextPath + "/login");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ServletException e) {
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/registration.jsp");   //tenere d'occhio
            dispatcher.forward(request, response);
        }
    }

    public void destroy() {
    }
}