package Controller;

import Model.BankAccount;
import Model.CreditCard;
import Model.UserDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@WebServlet("/form-payment-method")
public class PaymentMethodForm extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("userId") == null) {
                throw new ServletException("Devi essere loggato");
            }
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/addPaymentMethod.jsp");
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
            String methodType = request.getParameter("choose-method");
            if (methodType.equals("CreditCard")) {
                String numeroCarta = request.getParameter("card-number");
                String intestatario = request.getParameter("card-owner");
                String cvc = request.getParameter("cvc");
                String dataScadenzaForm = request.getParameter("expiring");
                String cvcPattern = "^[0-9]{3}$";
                String numberPattern = "^(?:[0-9]{4}[-\\s]?){3}[0-9]{4}$";
                String datePattern = "^\\d{2}/\\d{2}/\\d{4}$";
                String ownerPattern = "^[A-Za-z]+ [A-Za-z]+$";
                LocalDate today = LocalDate.now();
                LocalDate date = LocalDate.parse(dataScadenzaForm);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.ITALIAN);
                String dataScadenza = date.format(formatter);
                if (!numeroCarta.matches(numberPattern)) {
                    throw new ServletException("Numero carda non valido");
                } else if(!cvc.matches(cvcPattern)){
                    throw new ServletException("CVC non valido");
                } else if(!intestatario.matches(ownerPattern)) {
                    throw new ServletException("Intestatario non valido");
                } else if(!dataScadenza.matches(datePattern)) {
                    throw new ServletException("Data di scadenza non valida");
                } else if (today.isAfter(date)) {
                    throw new ServletException("Carta di Credito scaduta");
                } else {
                    CreditCard c = new CreditCard(numeroCarta, intestatario, cvc, dataScadenza);
                    if (!UserDAO.doCardSave((int) session.getAttribute("userId"), c)) {
                        request.setAttribute("error-payment", "Errore aggiunta carta, controlla se è già presente nel tuo account");
                        throw new ServletException("Errore nel salvataggio del metodo di pagamento (Carta Di Credito)");
                    }
                }
            } else if (methodType.equals("BankAccount")) {
                String IBAN = request.getParameter("iban");
                String intestatario = request.getParameter("bank-account-owner");
                String bankIbanPattern = "^[A-Z]{2}[0-9]{2}[A-Z0-9]{1,30}$";
                String ownerPattern = "^[A-Za-z]+ [A-Za-z]+$";
                if (!intestatario.matches(ownerPattern)) {
                    throw new ServletException("Credenziali intestatario non valide");
                } else if (!IBAN.matches(bankIbanPattern)) {
                    throw new ServletException("IBAN non valido");
                } else {
                    BankAccount b = new BankAccount(intestatario, IBAN);
                    if (!UserDAO.doBankAccountSave((int) session.getAttribute("userId"), b)) {
                        request.setAttribute("error-payment", "Errore aggiunta del conto, controlla se è già presente nel tuo account");
                        throw new ServletException("Errore nel salvataggio del metodo di pagamento (Conto Corrente)");
                    }
                }
            } else {
                throw new ServletException("Errore, imetodo di pagamento assente");
            }
            String contextPath = request.getContextPath();
            response.sendRedirect(contextPath + "/payment-method");
        } catch (ServletException e) {
            e.printStackTrace(System.out);
            response.setStatus(response.SC_BAD_REQUEST);
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/addPaymentMethod.jsp");  //tenere d'occhio
            dispatcher.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }
}