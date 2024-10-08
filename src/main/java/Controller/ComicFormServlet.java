//Aggiunta di prodotti
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
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@WebServlet("/admin-comic-form")
@MultipartConfig(   //serve per il multipart form data
        fileSizeThreshold = 1024 * 1024, // 1 MB
        maxFileSize = 1024 * 1024 * 10,      // 10 MB
        maxRequestSize = 1024 * 1024 * 100   // 100 MB
)
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
            //CARICAMENTO IMMAGINE PRODOTTO
            Part filePart = request.getPart("image");
            if(ComicDAO.existsISBN(ISBN, titolo)) {
                request.setAttribute("error-form", "Esiste già un fumetto con questo ISBN");
                throw new ServletException("Esiste già un fumetto con questo ISBN");
            }
            String fileName = filePart.getSubmittedFileName();
            String mimeType = filePart.getContentType();
            if (!(mimeType.equals("image/jpeg") || mimeType.equals("image/png"))) {
                request.setAttribute("error-form", "Tipo di file non supportato, inserisci un .jpg o un .png");
                throw new ServletException("Tipo di file non supportato, inserisci un .jpg o un .png");
            }
            String path = request.getServletContext().getRealPath("") + "uploads" + File.separator;
            String filePath = path + fileName;
            File dir = new File(path);
            if (!dir.exists()) {
                if (!dir.mkdir()) {
                    throw new ServletException("Errore nel salvataggio del file");
                }
            }
            File uploadedFile = new File(filePath);
            String immagine = "uploads" + File.separator + fileName;
            if(!request.getParameter("sconto").isEmpty()) {
                sconto = Integer.parseInt(request.getParameter("sconto"));
            }
            String ISBNPattern = "[0-9]{13}";
            if (!ISBN.matches(ISBNPattern)) {
                request.setAttribute("error-form", "ISBN non conforme");
                throw new ServletException("ISBN non conforme");
            }
            LocalDate currentDate = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.ITALIAN);
            String formattedDate = currentDate.format(formatter);
            if (!uploadedFile.exists()) {
                System.out.println("File caricato con successo: " + fileName);
                filePart.write(filePath);
            } else {
                request.setAttribute("error-form", "L'immagine esiste già");
                System.out.println("Errore nel caricamento del file: " + fileName);
                throw new ServletException("L'immagine esiste già");
            }
            Comic comic = new Comic(ISBN, autore, prezzo, titolo, descrizione, categoria, sconto, immagine, formattedDate);
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
        } catch (SQLException | IOException e) {
            e.printStackTrace(System.out);
            response.setStatus(response.SC_INTERNAL_SERVER_ERROR);
        }
    }
}