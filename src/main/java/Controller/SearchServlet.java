package Controller;

import Model.ComicDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/search")
public class SearchServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            if (request.getParameter("parameter") == null || request.getParameter("parameter").isEmpty()) {
                throw new ServletException("Parametro id mancante");
            }
            request.setAttribute("comics", ComicDAO.search(request.getParameter("parameter")));
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/searchPage.jsp");
            dispatcher.forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace(System.out);
            String contextPath = request.getContextPath();
            response.sendRedirect(contextPath + "/");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}