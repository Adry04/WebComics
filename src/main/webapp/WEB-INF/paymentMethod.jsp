<%@ page import="Model.*" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title> Metodi Di Pagamento - <%=session.getAttribute("nome")%> </title>
    <link rel="stylesheet" href="Styles/dataUser.css">
    <link rel="stylesheet" href="Styles/nav.css">
    <link rel="stylesheet" href="Styles/footer.css">
    <script src="js/navbar.js" type="text/javascript"></script>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="shortcut icon" href="assets/favicon.ico" type="image/x-icon">
</head>
<body>
    <%@include file="navbar.jsp"%>
    <div class="container-section">
        <%
            PaymentMethods paymentMethods = (PaymentMethods) request.getAttribute("paymentMethods");
            List<CreditCard> creditCards = paymentMethods.getCreditCards();
            List<BankAccount> bankaccounts = paymentMethods.getBankAccounts();
            if (!creditCards.isEmpty()) {
        %>
        <h1 class="title title-payment" id="title-credit" data-size="<%=creditCards.size()%>">Carte Di Credito</h1>
        <%
                for (CreditCard creditCard : creditCards) {
        %>
        <div class="container<%=creditCard.isExpired() ? "-is-expired" : ""%>" <%=creditCard.isExpired() ? "title='Carta di credito scadura'" : ""%> id="credit-card-<%=creditCard.getId()%>">
            <button aria-label="Delete" onclick="deleteCreditCard(<%=creditCard.getId()%>)" tabindex="0" onkeydown="deleteCreditCard(<%=creditCard.getId()%>)" class="delete-button">
                <svg aria-label="Delete SVG" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 14 14"><path fill="black" fill-rule="evenodd" d="M1.707.293A1 1 0 0 0 .293 1.707L5.586 7L.293 12.293a1 1 0 1 0 1.414 1.414L7 8.414l5.293 5.293a1 1 0 0 0 1.414-1.414L8.414 7l5.293-5.293A1 1 0 0 0 12.293.293L7 5.586z" clip-rule="evenodd"/></svg>
            </button>
            <p>Intestatario: <span><%=creditCard.getIntestatario()%></span></p>
            <p>Numero di carta: <span><%="************" + creditCard.getNumero().substring(creditCard.getNumero().length() - 4)%></span></p>
            <p>CVC: <span>***</span></p>
            <p>Data Di Scadenza: <span><%=creditCard.getDataScadenza()%></span></p>
        </div>
        <%
                }
        %>
    </div>
    <%
        }
        if (!bankaccounts.isEmpty()) {
    %>
    <div class="container-section">
        <h1 class="title title-payment" id="title-conto" data-size="<%=bankaccounts.size()%>">Conti Correnti</h1>
        <%
            for (BankAccount bankAccount : bankaccounts) {
        %>
        <div class="container" id="bank-account-<%=bankAccount.getId()%>">
            <button aria-label="Delete" onclick="deleteBankAccount(<%=bankAccount.getId()%>)" tabindex="0" onkeydown="deleteBankAccount(<%=bankAccount.getId()%>)" class="delete-button">
                <svg aria-label="Delete SVG" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 14 14"><path fill="black" fill-rule="evenodd" d="M1.707.293A1 1 0 0 0 .293 1.707L5.586 7L.293 12.293a1 1 0 1 0 1.414 1.414L7 8.414l5.293 5.293a1 1 0 0 0 1.414-1.414L8.414 7l5.293-5.293A1 1 0 0 0 12.293.293L7 5.586z" clip-rule="evenodd"/></svg>
            </button>
            <p>Intestatario: <span><%=bankAccount.getIntenstatario()%></span></p>
            <p>IBAN: <span><%=bankAccount.getIBAN().substring(0, 2) + "*********************" + bankAccount.getIBAN().substring(bankAccount.getIBAN().length() - 4)%></span></p>
            <p>BIC: <span><%=bankAccount.getBic()%></span></p>
        </div>
        <%
                }
            }
            if(creditCards.isEmpty() && bankaccounts.isEmpty()){
        %>
    </div>
    <div class="empty-section">
        <h1>Non possiedi metodi di pagamento</h1>
        <img src="assets/icons/payment.png" alt="Non possiedi metodi di pagamento"/>
    </div>
    <%
        }
    %>
    <button class="add-button" onclick="location.href = 'form-payment-method'" type="button">Aggiungi metodo di pagamento</button>
    <%@include file="footer.jsp"%>
    <script src="js/userData.js"></script>
</body>
</html>