//Servlet del form sui metodi di pagamento
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
import java.sql.SQLException;
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
                throw new Exception("Devi essere loggato");
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
                String ownerPattern = "^[a-zA-Zà-ÿÀ-ÿ'’\\- ]+$";
                LocalDate today = LocalDate.now();
                LocalDate date = LocalDate.parse(dataScadenzaForm);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.ITALIAN);
                String dataScadenza = date.format(formatter);
                if (!numeroCarta.matches(numberPattern)) {
                    request.setAttribute("error-payment", "Numero di carta non valido");
                    throw new ServletException("Numero carda non valido");
                } else if(!cvc.matches(cvcPattern)){
                    request.setAttribute("error-payment", "Credenziali cvc non valide");
                    throw new ServletException("CVC non valido");
                } else if(!intestatario.matches(ownerPattern)) {
                    request.setAttribute("error-payment", "Credenziali intestatario non valide");
                    throw new ServletException("Intestatario non valido");
                } else if(!dataScadenza.matches(datePattern)) {
                    request.setAttribute("error-payment", "Data di scadenza non valida");
                    throw new ServletException("Data di scadenza non valida");
                } else if (today.isAfter(date)) {
                    request.setAttribute("error-payment", "Carta di credito errata");
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
                String bic = request.getParameter("bic");
                String bankIbanPattern = "^[A-Z]{2}[0-9A-Z]{25}$";
                String ownerPattern = "^[a-zA-Zà-ÿÀ-ÿ'’\\- ]+$";
                String bicPattern = "^[A-Z]{6}[0-9]{2}$";
                if (!intestatario.matches(ownerPattern)) {
                    request.setAttribute("error-payment", "Credenziali intestatario non valide");
                    throw new ServletException("Credenziali intestatario non valide");
                } else if (!IBAN.matches(bankIbanPattern)) {
                    request.setAttribute("error-payment", "IBAN non valido");
                    throw new ServletException("IBAN non valido");
                } else if (!bic.matches(bicPattern)) {
                    request.setAttribute("error-payment", "Codice BIC non valido");
                } else {
                    BankAccount b = new BankAccount(intestatario, IBAN, bic);
                    if (!UserDAO.doBankAccountSave((int) session.getAttribute("userId"), b)) {
                        request.setAttribute("error-payment", "Errore aggiunta del conto, controlla se è già presente nel tuo account");
                        throw new ServletException("Errore nel salvataggio del metodo di pagamento (Conto Corrente)");
                    }
                }
            } else {
                throw new ServletException("Errore, imetodo di pagamento assente");
            }
            response.setStatus(response.SC_OK);
            if(request.getParameter("order") != null) {
                String contextPath = request.getContextPath();
                response.sendRedirect(contextPath + "/order-form");
            } else {
                String contextPath = request.getContextPath();
                response.sendRedirect(contextPath + "/payment-method");
            }
        } catch (ServletException e) {
            e.printStackTrace(System.out);
            response.setStatus(response.SC_BAD_REQUEST);
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/addPaymentMethod.jsp");  //tenere d'occhio
            dispatcher.forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace(System.out);
            response.setStatus(response.SC_INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            e.printStackTrace(System.out);
            response.setStatus(response.SC_BAD_REQUEST);
            String contextPath = request.getContextPath();
            response.sendRedirect(contextPath + "/");
        }
    }
}