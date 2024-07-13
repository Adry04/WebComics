package Controller;

import Model.*;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
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
            response.sendRedirect(contextPath + "/login");
        } catch (SQLException e) {
            e.printStackTrace(System.out);
            response.setStatus(response.SC_INTERNAL_SERVER_ERROR);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession(false);
            if(session == null || session.getAttribute("userId") == null || session.getAttribute("cart") == null) {
                throw new ServletException("Errore utente");
            }
            if (request.getParameter("address") == null || request.getParameter("payment") == null) {
                request.setAttribute("error-form", "Paremtri di richiesta mancanti");
                throw new ServletException("Parametri mancanti");
            }
            int idAddress = Integer.parseInt(request.getParameter("address"));
            String payment = request.getParameter("payment");
            String[] parts = payment.split("-");
            Address a = UserDAO.getAddress(idAddress);
            CreditCard card = null;
            BankAccount bankAccount = null;
            // Estrai le parti
            int idPayment = Integer.parseInt(parts[0]);
            String paymentMethod = parts[1];
            if(paymentMethod.equalsIgnoreCase("Carta")) {
                card = UserDAO.getCard(idPayment);
            } else if(paymentMethod.equalsIgnoreCase("conto")) {
                bankAccount = UserDAO.getBankAccount(idPayment);
            } else {
                request.setAttribute("error-form", "Errore di attributi");
                throw new ServletException("Errore di attributi");
            }
            if (!OrderDAO.doSave((int) session.getAttribute("userId"), (Cart) session.getAttribute("cart"), a, card, bankAccount)) {
                request.setAttribute("error-form", "Errore nel salvataggio riprova più tardi");
                throw new ServletException("Errore salvataggio ordine");
            } else {
                String contextPath = request.getContextPath();
                response.sendRedirect(contextPath + "/order");
            }
        } catch (ServletException e) {
            e.printStackTrace(System.out);
            String contextPath = request.getContextPath();
            response.sendRedirect(contextPath + "/order-form");
        } catch (SQLException e) {
            e.printStackTrace(System.out);
            response.setStatus(response.SC_INTERNAL_SERVER_ERROR);
        }
    }
}