package Controller;

import Model.Hash;
import Model.UserDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/account-setting")
public class AccountSettingServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("userId") == null) {
                throw new ServletException("Non sei loggato");
            }
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/WEB-INF/account-setting.jsp");
            rd.forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace(System.out);
            String contextPath = request.getContextPath();
            response.sendRedirect(contextPath + "/");
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
                HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("userId") == null) {
                throw new ServletException("Devi essere loggato");
            }
            String emailPattern = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9_.-]+\\.[a-zA-Z]{2,}$";
            String passwordPattern = "^(?=.*[0-9])(?=.*[!@#$%^&*])(?=.*[A-Z]).{6,}$";
            if((request.getParameter("nome") != null && !request.getParameter("nome").isEmpty()) && (request.getParameter("email") != null && !request.getParameter("email").isEmpty()) && (request.getParameter("cognome") != null && !request.getParameter("cognome").isEmpty())) {
                String nome = request.getParameter("nome");
                String email = request.getParameter("email");
                String cognome = request.getParameter("cognome");
                if(session.getAttribute("nome").equals(nome) && session.getAttribute("email").equals(cognome) && session.getAttribute("cognome").equals(cognome)) {
                    request.setAttribute("error", "I dati non sono stati cambiati");
                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/WEB-INF/account-setting.jsp");
                    rd.forward(request, response);
                    return;
                }
                if(UserDAO.isExistsEmail(email) && !session.getAttribute("email").equals(email)){
                    request.setAttribute("error", "Email già esistente");
                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/WEB-INF/account-setting.jsp");
                    rd.forward(request, response);
                    return;
                }
                if(!email.matches(emailPattern)) {
                    request.setAttribute("error", "Email non valida");
                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/WEB-INF/account-setting.jsp");
                    rd.forward(request, response);
                    return;
                }
                UserDAO.doUpdateUser(email, nome, cognome, (int) session.getAttribute("userId"));
                session.setAttribute("email", email);
                session.setAttribute("nome", nome);
                session.setAttribute("cognome", cognome);
                String contextPath = request.getContextPath();
                response.sendRedirect(contextPath + "/account");
            } else if((request.getParameter("old-password") != null && !request.getParameter("old-password").isEmpty()) && (request.getParameter("new-password") != null && !request.getParameter("new-password").isEmpty()) && (request.getParameter("confirm-new-password") != null && !request.getParameter("confirm-new-password").isEmpty())) {
                String oldPassword = request.getParameter("old-password");
                String newPassword = request.getParameter("new-password");
                String confirmNewPasssword = request.getParameter("confirm-new-password");
                String hashedPassword = UserDAO.getPassword((Integer) session.getAttribute("userId"));
                if(Hash.checkPassword(oldPassword, hashedPassword)) {
                    if(newPassword.equals(confirmNewPasssword)) {
                        if(newPassword.matches(passwordPattern)) {
                            String newHashedPassword = Hash.hashPassword(newPassword);
                            UserDAO.doUpdateUserPassword(newHashedPassword, (Integer) session.getAttribute("userId"));
                            String contextPath = request.getContextPath();
                            response.sendRedirect(contextPath + "/account");
                        } else {
                            request.setAttribute("error-password", "La password deve contenere almeno 6 caratteri, una maiuscola, un carattere speciale e un numero");
                            RequestDispatcher rd = getServletContext().getRequestDispatcher("/WEB-INF/account-setting.jsp");
                            rd.forward(request, response);
                        }
                    } else {
                        request.setAttribute("error-password", "Le due password devono coincidere");
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/WEB-INF/account-setting.jsp");
                        rd.forward(request, response);
                    }
                } else {
                    request.setAttribute("error-password", "La password corrente non è corretta");
                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/WEB-INF/account-setting.jsp");
                    rd.forward(request, response);
                }
            } else {
                throw new ServletException("Errore di parametri");
            }
        } catch (ServletException e){
            e.printStackTrace(System.out);
            String contextPath = request.getContextPath();
            response.sendRedirect(contextPath + "/");
        } catch (SQLException e){
            e.printStackTrace(System.out);
            response.setStatus(response.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
