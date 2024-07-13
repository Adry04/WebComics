//Servlet form degli indirizzi
package Controller;

import Model.Address;
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

@WebServlet("/address-form")
public class AddressServletForm extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("userId") == null) {
                throw new ServletException("Devi essere loggato");
            }
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/addressForm.jsp");
            dispatcher.forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace(System.out);
            String contextPath = request.getContextPath();
            response.sendRedirect(contextPath + "/");
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("userId") == null) {
                throw new Exception("Devi essere loggato");
            }
            String indirizzo = request.getParameter("address");
            String cap = request.getParameter("cap");
            String provincia = request.getParameter("location");
            String capPattern = "^\\d{5}$";
            if (!cap.matches(capPattern)) {
                throw new ServletException("Codice avviamento postale (CAP) non valido");
            } else {
                Address a = new Address(indirizzo, cap, provincia);
                if (!UserDAO.doAddressSave((int) session.getAttribute("userId"), a)) {
                    request.setAttribute("error-address", "Errore aggiunta indirizzo, controlla se è già presente nel tuo account");
                    throw new ServletException("Errore nel salvataggio dell'indirizzo di spedizione");
                }
            }
            response.setStatus(response.SC_OK);
            if(request.getParameter("order") != null) {
                String contextPath = request.getContextPath();
                response.sendRedirect(contextPath + "/order-form");
            } else {
                String contextPath = request.getContextPath();
                response.sendRedirect(contextPath + "/address");
            }
        } catch (ServletException e) {
            e.printStackTrace(System.out);
            response.setStatus(response.SC_BAD_REQUEST);
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/addressForm.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace(System.out);
            response.setStatus(response.SC_INTERNAL_SERVER_ERROR);
        }
    }
}