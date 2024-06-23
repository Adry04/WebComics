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
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/admin-comic-form")
public class ComicFormServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("userId") == null) {
                throw new ServletException("Utente non loggato");
            } else if(!(Boolean) session.getAttribute("isAdmin")) {
                throw new ServletException("L'utente non è un admin");
            }
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/WEB-INF/admin/comic-form.jsp");
            rd.forward(request, response);
        } catch (ServletException e) {
            String contextPath = request.getContextPath();
            response.sendRedirect(contextPath + "/");
        }
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String ISBN = request.getParameter("ISBN");
            String autore = request.getParameter("autore");
            double prezzo = Double.parseDouble(request.getParameter("prezzo"));
            String titolo = request.getParameter("titolo");
            String descrizione = request.getParameter("descrizione");
            String categoria = request.getParameter("categoria");
            int sconto = 0;
            if(request.getParameter("sconto") != "") {
                sconto = Integer.parseInt(request.getParameter("sconto"));
            }
            if(ComicDAO.existsISBN(ISBN, titolo)) {
                request.setAttribute("error-form", "Esiste già questo fumetto");
                throw new ServletException("Esiste già un fumetto con questo ISBN");
            }
            String ISBNPattern = "[0-9]{13}";
            if (!ISBN.matches(ISBNPattern)) {
                request.setAttribute("error-form", "ISBN non conforme");
                throw new ServletException("ISBN non conforme");
            }
            Comic comic = new Comic(ISBN, autore, prezzo, titolo, descrizione, categoria, sconto);
            if(!ComicDAO.doSave(comic)){
                request.setAttribute("error-form", "Errore nell'aggiunta del fumetto");
                throw new ServletException("Errore nell'aggiunta del fumetto");
            } else {
                String contextPath = request.getContextPath();
                response.sendRedirect(contextPath + "/admin");
            }
        } catch (ServletException e) {
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/admin/comic-form.jsp");  //tenere d'occhio
            dispatcher.forward(request, response);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}