//
package Controller;

import Model.Comic;
import Model.ComicDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/comic")
public class ComicPageServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            String id = (String) request.getAttribute("id");
            if(id.isEmpty()) {
                String contextPath = request.getContextPath();
                response.sendRedirect(contextPath + "/");
            }
            Comic comic = ComicDAO.getComic(id);
            request.setAttribute("comic", comic);

            RequestDispatcher rd = getServletContext().getRequestDispatcher("/WEB-INF/comic-page.jsp");
            rd.forward(request, response);
        } catch (ServletException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}