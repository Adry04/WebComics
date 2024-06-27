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
            System.out.println("arrivo 1");
            int idUtente = (int) session.getAttribute("userId");
            String ISBN = request.getParameter("ISBN");
            String type = request.getParameter("requestType");
            System.out.println("arrivo 2");
            if(type.equals("add")) {
                System.out.println("arrivo 3");
                if (ComicDAO.addWish(ISBN, idUtente)) {
                    System.out.println("arrivo 4");
                    response.setStatus(HttpServletResponse.SC_OK);
                } else {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                }
            } else {
                System.out.println("arrivo 5");
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