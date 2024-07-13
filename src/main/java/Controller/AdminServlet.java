//Accesso alla zona se admin
package Controller;

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

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("userId") == null) {
                throw new ServletException("utente non loggato");
            } else if(!(Boolean) session.getAttribute("isAdmin")) {
                throw new ServletException("L'utente non è un admin");
            }
            List<Comic> comics = ComicDAO.getComics();
            request.setAttribute("comics", comics);
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/WEB-INF/admin/comic.jsp");
            rd.forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace(System.out);
            String contextPath = request.getContextPath();
            response.sendRedirect(contextPath + "/");
        } catch (SQLException e) {
            response.setStatus(response.SC_INTERNAL_SERVER_ERROR);
            throw new RuntimeException(e);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String ISBN = request.getParameter("ISBN");
            if(!ComicDAO.doDelete(ISBN)){
                throw new ServletException("Errore eliminazione prodotto");
            }
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (ServletException e) {
            e.printStackTrace(System.out);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } catch (SQLException e) {
            e.printStackTrace(System.out);
            response.setStatus(response.SC_INTERNAL_SERVER_ERROR);
        }
    }
}