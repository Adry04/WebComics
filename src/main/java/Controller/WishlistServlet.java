package Controller;

import Model.ComicDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/wish-operation")
public class WishlistServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) {

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("userId") == null) {
                throw new ServletException("Utente non loggato");
            }
            int idUtente = (int) session.getAttribute("userId");
            String ISBN = request.getParameter("ISBN");
            String type = request.getParameter("requestType");
            if(type.equals("add")) {
                if (ComicDAO.addWish(ISBN, idUtente)) {
                    response.setStatus(HttpServletResponse.SC_OK);
                } else {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                }
            } else {
                if (ComicDAO.removeWish(ISBN, idUtente)) {
                    response.setStatus(HttpServletResponse.SC_OK);
                } else {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                }
            }
        } catch (ServletException e) {
            request.setAttribute("error", e);
            throw new RuntimeException(e);
        }
    }
}