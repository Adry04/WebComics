<%@ page import="Model.*" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Metodi Di Pagamento - <%=session.getAttribute("nome")%></title>
    <link rel="stylesheet" href="Styles/paymentMethods.css">
    <link rel="stylesheet" href="Styles/nav.css">
    <link rel="stylesheet" href="Styles/footer.css">
    <script src="js/navbar.js" type="text/javascript"></script>
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body>
    <%@include file="navbar.jsp"%>
    <div class="payments-container">
        <%
            PaymentMethods paymentMethods = (PaymentMethods) request.getAttribute("paymentMethods");
            List<CreditCard> creditCards = paymentMethods.getCreditCards();
            List<BankAccount> bankaccounts = paymentMethods.getBankAccounts();
            if (!creditCards.isEmpty()) {
        %>
        <h1 class="title-payment">Carte Di Credito:</h1>
        <%
                for (CreditCard creditCard : creditCards) {
        %>
        <div class="payment-container <%=creditCard.isExpired() ? "is-expired" : ""%>" <%=creditCard.isExpired() ? "title='Carta di credito scadura'" : ""%>>
            <p class="numero"><%=creditCard.getNumero()%></p>
            <p class="intenstatario"><%=creditCard.getIntestatario()%></p>
            <p class="cvc"><%=creditCard.getCvc()%></p>
            <p class="data-scadenza"><%=creditCard.getDataScadenza()%></p>
        </div>
        <%
                }
            }
            if (!bankaccounts.isEmpty()){
        %>
        <h1 class="title-payment">Conti Correnti:</h1>
        <%
            for (BankAccount bankAccount : bankaccounts) {
        %>
        <div class="payment-container">
            <p class="intestatario"><%=bankAccount.getIntenstatario()%></p>
            <p class="IBAN"><%=bankAccount.getIBAN()%></p>
        </div>
        <%
                }
            } else {
        %>
        <div class="empty-section">
            <h1>Non possiedi metodi di pagamento</h1>
            <img src="assets/icons/payment.png" alt="Non possiedi metodi di pagamento"/>
        </div>
        <%
            }
        %>
        <div class="add-button">
            <button type="submit" value="Aggiungi metodo di pagamento" onclick="location.href = 'form-payment-method'">Aggiungi metodo di pagamento</button>
        </div>
    </div>
    <%@include file="footer.jsp"%>
</body>
</html>