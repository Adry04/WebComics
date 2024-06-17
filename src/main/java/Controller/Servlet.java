package Controller;

import java.io.*;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/WebComics")
public class Servlet extends HttpServlet {

    public void init() {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");

        /*ServletContext context = getServletContext();
        if (context.getAttribute("counter") == null) {
            context.setAttribute("counter", );
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("src/main/webapp/index.jsp");
        dispatcher.forward(request, response);
        PrintWriter out = response.getWriter();*/
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }

    public void destroy() {
    }
}