package Controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            System.out.println("Arrivo qui 1");
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("userId") == null) {
                System.out.println("Arrivo qui 2");
                throw new ServletException("Utente non loggato");
            } else if(!(Boolean) session.getAttribute("isAdmin")) {
                System.out.println("Arrivo qui 3");
                throw new ServletException("L'utente non è un admin");
            }
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/WEB-INF/admin/product.jsp");
            rd.forward(request, response);
        } catch (ServletException e) {
            System.out.println(e + "djawndjawnojdnaw");
            String contextPath = request.getContextPath();
            response.sendRedirect(contextPath + "/");
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}