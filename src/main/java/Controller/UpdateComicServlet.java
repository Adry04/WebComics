package Controller;

import Model.Comic;
import Model.ComicDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

@WebServlet("/admin-update-comic")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, // 1 MB
        maxFileSize = 1024 * 1024 * 10,      // 10 MB
        maxRequestSize = 1024 * 1024 * 100   // 100 MB
)
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
            String contextPath = request.getContextPath();
            response.sendRedirect(contextPath + "/");
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String ISBN = request.getParameter("isbn");
            String autore = request.getParameter("autore");
            double prezzo = Double.parseDouble(request.getParameter("prezzo"));
            String titolo = request.getParameter("titolo");
            String descrizione = request.getParameter("descrizione");
            String categoria = request.getParameter("categoria");

            Comic oldComic = ComicDAO.getComic(ISBN);
            int sconto = 0;
            if (!request.getParameter("sconto").isEmpty()) {
                sconto = Integer.parseInt(request.getParameter("sconto"));
            }
            //immagine
            Part filePart = request.getPart("immagine");
            String immagine = Objects.requireNonNull(oldComic).getImmagine();
            if (filePart != null && filePart.getSize() > 0) {
                String fileName = filePart.getSubmittedFileName();
                String mimeType = filePart.getContentType();
                if (!(mimeType.equals("image/jpeg") || mimeType.equals("image/png"))) {
                    request.setAttribute("error-form", "Tipo di file non supportato");
                    throw new ServletException("Tipo di file non supportato");
                }
                String path = request.getServletContext().getRealPath("") + File.separator;
                String filePath = path + File.separator + "uploads" + File.separator + fileName;
                String dirPath = path + File.separator + "uploads";
                String oldFilePath = path + oldComic.getImmagine();
                File dir = new File(dirPath);
                if (!dir.exists()) {
                    if (!dir.mkdir()) {
                        throw new ServletException("Errore nel salvataggio del file");
                    }
                }
                File uploadedFile = new File(filePath);
                immagine = "uploads" + File.separator + fileName;
                if (!uploadedFile.exists()) {
                    filePart.write(filePath);
                    File oldFile = new File(oldFilePath);
                    if(oldFile.exists()) {
                        if (!oldFile.delete()) {
                            throw new ServletException("Errore nella cancellazione del file");
                        }
                    }
                }
            }
            if(!ComicDAO.doUpdate(new Comic(ISBN, autore, prezzo, titolo, descrizione, categoria, sconto, immagine))){
                request.setAttribute("error-form", "Errore nell'aggiunta del fumetto");
                throw new ServletException("Errore nell'aggiunta del fumetto");
            } else {
                String contextPath = request.getContextPath();
                response.sendRedirect(contextPath + "/admin");
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/admin/comic.jsp");  //tenere d'occhio
            dispatcher.forward(request, response);
        }
    }
}