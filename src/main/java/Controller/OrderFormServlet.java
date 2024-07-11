package Controller;

import Model.*;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@WebServlet("/order-form")
public class OrderFormServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("userId") == null) {
                throw new ServletException("Utente non loggato");
            } else if (session.getAttribute("cart") == null) {
                throw new ServletException("Non hai prodotti");
            }
            List<CreditCard> creditCards = UserDAO.getAllCards((int) session.getAttribute("userId"));
            List<BankAccount> bankAccounts = UserDAO.getAllBankAccounts((int) session.getAttribute("userId"));
            List<Address> addresses = UserDAO.getAllAddresses((int) session.getAttribute("userId"));
            if (Objects.requireNonNull(creditCards).isEmpty() && Objects.requireNonNull(bankAccounts).isEmpty()) {
                String contextPath = request.getContextPath();
                response.sendRedirect(contextPath + "/form-payment-method?order=1");
                return;
            } else if (Objects.requireNonNull(addresses).isEmpty()) {
                String contextPath = request.getContextPath();
                response.sendRedirect(contextPath + "/address-form?order=1");
                return;
            }
            request.setAttribute("addresses", addresses);
            request.setAttribute("creditCards", creditCards);
            request.setAttribute("bankAccounts", bankAccounts);
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/WEB-INF/orderForm.jsp");
            rd.forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace(System.out);
            String contextPath = request.getContextPath();
            response.sendRedirect(contextPath + "/");
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {   //TODO servlet del post dopo che il checkout è stato effettuato
            String ISBN = request.getParameter("ISBN");
            String ISBNPattern = "[0-9]{13}";
            if (!ISBN.matches(ISBNPattern)) {
                request.setAttribute("error-form", "ISBN non conforme");
                throw new ServletException("ISBN non conforme");
            }
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/order.jsp");
            dispatcher.forward(request, response);
        } catch (ServletException e) {
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/order-form.jsp");
            dispatcher.forward(request, response);
        }
    }
}