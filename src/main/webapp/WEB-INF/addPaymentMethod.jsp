<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title> Aggiunta metodi di pagamento - <%=session.getAttribute("nome")%> </title>
    <link rel="stylesheet" href="Styles/nav.css">
    <link rel="stylesheet" href="Styles/footer.css">
    <link rel="stylesheet" href="Styles/form.css">
    <script src="js/navbar.js" type="text/javascript"></script>
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body>
    <%@include file="navbar.jsp"%>
    <form method="POST">
        <p class="title-form">Scegli il metodo di pagamento</p>
        <div class="input-container">
            <div>
                <label for="method1">
                    <input id="method1" type="radio" name="choose-method" value="Carta Di Credito"> Carta Di Credito
                </label>
                <label for="method2">
                    <input id="method2" type="radio" name="choose-method" value="Conto Corrente"> Conto Corrente
                </label>
            </div>
            <script>
                //funzione js per mostrare il metodo della carta o del conto corrente in tempo reale in base alla scelta dell'utente
            </script>
            <div id="credit-card">
                <p class="title-form">Carta di Credito</p>
                <div>
                    <label for="card-number">Numero Carta </label>
                    <input type="text" placeholder="XXXX-XXXX-XXXX-XXXX" id="card-number" name="card-number" maxlength="16" required/>
                </div>
                <div>
                    <label for="card-owner">Nome e Cognome dell'intestario </label>
                    <input type="text" placeholder="Inserisci Nome e Cognome dell'intestatario" id="card-owner" name="card-owner" required/>
                </div>
                <div>
                    <label for="cvc">Inserisci il CVC (codice di tre cifre sul retro della carta) </label>
                    <input type="text" placeholder="XXX" id="cvc" name="cvc" maxlength="3" required/>
                </div>
                <div>
                    <label for="expiring">Inserisci la data di scadenza della carta </label>
                    <input type="date" id="expiring" name="expiring" required/>
                </div>
            </div>
            <div id="bank-account">
                <p class="title-form">Conto Corrente</p>
                <div>
                    <label for="bank-account-owner">Nome e Cognome dell'intestario </label>
                    <input type="text" placeholder="Inserisci Nome e Cognome dell'intestatario" id="bank-account-owner" name="bank-account-owner" required/>
                </div>
                <div>
                    <label for="iban">Inserisci l'IBAN</label>
                    <input type="text" placeholder="Inserire l'IBAN'" id="iban" name="iban" maxlength="27" required/>
                </div>
            </div>
            <p class="error-text <%=request.getAttribute("error") == null ? "remove-item" : ""%>"><%=request.getAttribute("error") != null ? request.getAttribute("error") : ""%></p>
            <button type="submit"> Login </button>
            <div class="link-container">
                <p> Hai già un metodo di pagamento? </p>
                <a href="payment-method"> Pagamenti </a>
            </div>
        </div>
    </form>
    <%@include file="footer.jsp"%>
</body>
</html>