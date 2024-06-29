//Controlli sul fumetto
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

@WebServlet("/comic")
public class ComicPageServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession(false);
            String isbn = request.getParameter("isbn");
            if(isbn.isEmpty()) {
                String contextPath = request.getContextPath();
                response.sendRedirect(contextPath + "/");
            }
            Comic comic = ComicDAO.getComic(isbn);
            request.setAttribute("comic", comic);
            boolean isWished = false;
            if(session.getAttribute("userId") != null) {
                isWished = ComicDAO.isWished(isbn, (Integer) session.getAttribute("userId"));
            }
            request.setAttribute("isWished", isWished);
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/WEB-INF/comic-page.jsp");
            rd.forward(request, response);
        } catch (ServletException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}