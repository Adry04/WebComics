package Controller;

import Model.UserDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/payment-method")
public class PaymentMethodServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("userId") == null) {
                throw new ServletException("Devi essere loggato");
            }
            request.setAttribute("paymentMethods", UserDAO.getPaymentsMethods((int) session.getAttribute("userId")));
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/paymentMethod.jsp");
            dispatcher.forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace(System.out);
            String contextPath = request.getContextPath();
            response.sendRedirect(contextPath + "/");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("userId") == null) {
                throw new ServletException("Devi essere loggato");
            }
            if(request.getParameter("requestType") == null || request.getParameter("id") == null || request.getParameter("paymentType") == null) {
                throw new ServletException("Errore di parametri");
            }
            String type = request.getParameter("requestType");
            int id = Integer.parseInt(request.getParameter("id"));
            if(type.equals("delete")) {
                String paymentType = request.getParameter("paymentType");
                if(paymentType.equalsIgnoreCase("carta")) {
                    if(!UserDAO.doDeleteCreditCard(id, (Integer) session.getAttribute("userId"))){
                        throw new ServletException("Errore eliminazione carta");
                    }
                } else if (paymentType.equalsIgnoreCase("conto")) {
                    if(!UserDAO.doDeleteBankAccount(id, (Integer) session.getAttribute("userId"))){
                        throw new ServletException("Errore eliminazione carta");
                    }
                } else {
                    throw new ServletException("Errore metodo di pagamento");
                }
            }
        } catch (ServletException e) {
            e.printStackTrace(System.out);
            String contextPath = request.getContextPath();
            response.sendRedirect(contextPath + "/");
        }
    }
}