//Servlet della wishlist
package Controller;

import Controller.Exception.ComicDisponibility;
import Model.Comic;
import Model.ComicDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/wishlist")
public class WishlistServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("userId") == null) {
                throw new ServletException("Utente non loggato");
            }
            List<Comic> comicWishes = ComicDAO.getWishes((Integer) session.getAttribute("userId"));
            request.setAttribute("comicWishes", comicWishes);
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/WEB-INF/wishlist.jsp");
            rd.forward(request, response);
        } catch (ServletException e) {
            String contextPath = request.getContextPath();
            response.sendRedirect(contextPath + "/login");
        } catch (SQLException e) {
            e.printStackTrace(System.out);
            response.setStatus(response.SC_INTERNAL_SERVER_ERROR);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("userId") == null) {
                throw new ServletException("Utente non loggato");
            }
            int idUtente = (int) session.getAttribute("userId");
            String ISBN = request.getParameter("ISBN");
            if(ISBN.equals("null")) {
                throw new ComicDisponibility("Fumetto non più disponibile");
            }
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
            response.setStatus(response.SC_BAD_REQUEST);
        } catch (ComicDisponibility e) {
            response.setStatus(response.SC_UNAUTHORIZED);
        } catch (SQLException e) {
            e.printStackTrace(System.out);
            response.setStatus(response.SC_INTERNAL_SERVER_ERROR);
        }
    }
}