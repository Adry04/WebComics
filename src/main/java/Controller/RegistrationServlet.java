package Controller;

import java.io.*;
import java.sql.*;

import Model.ConPool;
import Model.User;
import Model.UserDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {

    public void init() {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/registration.jsp");  //tenere d'occhio
        dispatcher.forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String firstName = (String) request.getAttribute("firstName");
        String lastName = (String) request.getAttribute("lastName");
        String email = (String) request.getAttribute("email");
        String password = (String) request.getAttribute("password");
        String passwordConfirm = (String) request.getAttribute("passwordConfirm");
        try {
            Connection connection = ConPool.getConnection();
            String query = "SELECT `email` FROM `utente` WHERE `email` = ?";
            PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, email);
            if (ps.execute()) {
                connection.close();
                request.setAttribute("error", "Esiste già un utente con questa email");
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/registration.jsp");  //tenere d'occhio
                dispatcher.forward(request, response);
            }
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            if (password.equals(passwordConfirm)) {
                User user = new User(firstName, lastName, email, false);
                UserDAO userDao = new UserDAO();
                userDao.doSave(user);
                connection.close();
            } else {
                connection.close();
                request.setAttribute("error", "Le due password devono coincidere");
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/registration.jsp");  //tenere d'occhio
                dispatcher.forward(request, response);
            }
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/index.jsp");  //tenere d'occhio
            dispatcher.forward(request, response);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void destroy() {
    }
}