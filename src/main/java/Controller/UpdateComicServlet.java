package Controller;

import Model.Comic;
import Model.ComicDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.File;
import java.io.IOException;

@WebServlet("/admin-update-comic")
public class UpdateComicServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("userId") == null) {
                throw new ServletException("Utente non loggato");
            } else if(!(Boolean) session.getAttribute("isAdmin")) {
                throw new ServletException("L'utente non è un admin");
            }
            String isbn = request.getParameter("isbn");
            if(isbn == null || isbn.isEmpty()) {
                throw new ServletException("ISBN non inserito");
            }
            Comic comic = ComicDAO.getComic(isbn);
            request.setAttribute("comic", comic);
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/WEB-INF/admin/update-comic.jsp");
            rd.forward(request, response);
        } catch (ServletException e) {
            System.out.println(e);
            String contextPath = request.getContextPath();
            response.sendRedirect(contextPath + "/");
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String ISBN = request.getParameter("isbn");
            System.out.println(ISBN);

            String autore = request.getParameter("autore");
            System.out.println(request.getParameter("autore"));

            double prezzo = Double.parseDouble(request.getParameter("prezzo"));
            System.out.println(prezzo);

            String titolo = request.getParameter("titolo");
            System.out.println(titolo);

            String descrizione = request.getParameter("descrizione");
            System.out.println(descrizione);

            String categoria = request.getParameter("categoria");
            System.out.println(categoria);

            Comic oldComic = ComicDAO.getComic(ISBN);
            int sconto = 0;
            if (!request.getParameter("sconto").isEmpty()) {
                sconto = Integer.parseInt(request.getParameter("sconto"));
            }
            //immagine
            Part filePart = request.getPart("immagine");
            String immagine = "";
            if (filePart != null) {
                String fileName = filePart.getSubmittedFileName();
                String mimeType = filePart.getContentType();
                if (!(mimeType.equals("image/jpeg") || mimeType.equals("image/png"))) {
                    request.setAttribute("error-form", "Tipo di file non supportato");
                    throw new ServletException("Tipo di file non supportato");
                }
                String path = request.getServletContext().getRealPath("") + "uploads" + File.separator;
                String filePath = path + fileName;
                String oldFilePath = path + oldComic.getImmagine();
                File dir = new File(path);
                if (!dir.exists()) {
                    if (!dir.mkdir()) {
                        throw new ServletException("Errore nel salvataggio del file");
                    }
                }
                File uploadedFile = new File(filePath);
                immagine = "uploads" + File.separator + fileName;
                if (!uploadedFile.exists()) {
                    System.out.println("File caricato con successo: " + fileName);
                    filePart.write(filePath);
                    File oldFile = new File(oldFilePath);
                    if (!oldFile.delete()) {
                        throw new ServletException("Errore nella cancellazione del file");
                    }
                }
            }
            if(!ComicDAO.doUpdate(new Comic(autore, prezzo, titolo, descrizione, categoria, sconto, immagine))){
                request.setAttribute("error-form", "Errore nell'aggiunta del fumetto");
                throw new ServletException("Errore nell'aggiunta del fumetto");
            } else {
                String contextPath = request.getContextPath();
                response.sendRedirect(contextPath + "/admin");
            }
        } catch (ServletException e) {
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/admin/comic.jsp");  //tenere d'occhio
            dispatcher.forward(request, response);
        }
    }
}