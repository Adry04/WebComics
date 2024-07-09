<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title> Aggiunta metodi di pagamento - <%=session.getAttribute("nome")%> </title>
    <link rel="stylesheet" href="Styles/form.css">
    <script src="js/navbar.js" type="text/javascript"></script>
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body>
    <form method="POST" action="" onsubmit="return controlForm()">
        <svg onclick="location.href='./'" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 32 32"><path fill="#321b41" d="m2.36 11.23l8.31 7.57c.61.56 1.6.12 1.6-.71v-3.63c0-.35.29-.64.64-.64h15.86c.66 0 1.19-.53 1.19-1.19V8.42c0-.66-.53-1.19-1.19-1.19H12.91c-.35 0-.64-.29-.64-.64V2.96c0-.83-.99-1.27-1.6-.71L2.36 9.82a.946.946 0 0 0 0 1.41m13.97 17.53l-2.54-6.95c-.16-.49-.62-.81-1.13-.81c-.51 0-.97.32-1.12.79l-2.57 6.98c-.18.48.07 1 .55 1.18c.1.03.21.05.32.05c.37 0 .73-.23.86-.6l.41-1.13c.04.01.08.01.12.01h2.87c.03 0 .07 0 .1-.01l.41 1.12c.18.48.7.73 1.18.55c.47-.17.71-.7.54-1.18m-4.54-2.32l.87-2.38l.86 2.38zm-3.56-2.73c0 .53-.15 1.02-.41 1.43a2.7 2.7 0 0 1 1.07 2.15c0 1.38-1.05 2.54-2.4 2.69c-.04.01-.09.02-.13.01c-.06.01-.12.01-.18.01H2.92a.92.92 0 0 1-.92-.93v-7.16c0-.5.41-.91.92-.91h2.6c.1 0 .19 0 .27.01a7.905 7.905 0 0 0 .19.03c1.28.22 2.25 1.34 2.25 2.67m-2.7-.87H3.84v1.74h1.68c.48 0 .88-.39.88-.87a.87.87 0 0 0-.87-.87m-1.69 3.57v1.74h2.35a.87.87 0 0 0 0-1.74zm18.76 1.94a3.308 3.308 0 0 1-6.17-1.66v-2.38c0-1.83 1.48-3.31 3.31-3.31c1.18 0 2.28.64 2.87 1.65c.26.44.1 1-.33 1.26c-.44.26-1 .11-1.26-.33a1.5 1.5 0 0 0-1.28-.74c-.8 0-1.47.66-1.47 1.47v2.38c0 .81.67 1.47 1.47 1.47c.53 0 1.01-.28 1.28-.74c.26-.43.81-.59 1.26-.33c.42.26.58.82.32 1.26m4.63-3.48l2.6 3.68c.29.42.2.99-.23 1.29c-.42.29-.99.19-1.28-.22l-2.41-3.42l-.69.69v2.2c0 .51-.41.92-.92.92s-.92-.41-.92-.92v-7.17c0-.51.41-.92.92-.92s.92.41.92.92v2.37l3.01-3.02c.36-.36.94-.36 1.3 0c.36.36.36.94 0 1.3z"></path></svg>
        <p class="title-form">Scegli il metodo di pagamento</p>
            <div class="radio-container">
                <div class="radio-item">
                    <input id="method1" type="radio" name="choose-method" value="CreditCard" onclick="showCreditCard()">
                    <label for="method1"> Carta Di Credito</label>
                    <input id="method2" type="radio" name="choose-method" value="BankAccount" onclick="showBankAccount()">
                    <label for="method2"> Conto Corrente</label>
                </div>
            </div>
        <div class="input-container">
        <div id="credit-card" class="no-display-form">
                <p class="title-form">Carta di Credito</p>
                <div>
                    <label for="card-number">Numero Carta </label>
                    <input type="text" placeholder="XXXX-XXXX-XXXX-XXXX" id="card-number" name="card-number" maxlength=19/>
                </div>
                <div>
                    <label for="card-owner">Nome e Cognome dell'intestario </label>
                    <input type="text" placeholder="Inserisci Nome e Cognome dell'intestatario" id="card-owner" name="card-owner"/>
                </div>
                <div>
                    <label for="cvc">Inserisci il CVC (codice di tre cifre sul retro della carta) </label>
                    <input type="text" placeholder="XXX" id="cvc" name="cvc" maxlength="3"/>
                </div>
                <div>
                    <label for="expiring">Inserisci la data di scadenza della carta </label>
                    <input type="date" id="expiring" name="expiring"/>
                </div>
            </div>
            <div id="bank-account" class="no-display-form">
                <p class="title-form">Conto Corrente</p>
                <div>
                    <label for="bank-account-owner">Nome e Cognome dell'intestario </label>
                    <input type="text" placeholder="Inserisci Nome e Cognome dell'intestatario" id="bank-account-owner" name="bank-account-owner"/>
                </div>
                <div>
                    <label for="iban">Inserisci l'IBAN</label>
                    <input type="text" placeholder="Inserire l'IBAN'" id="iban" name="iban" maxlength="27"/>
                </div>
            </div>
            <p id="error-text" class="error-text <%=request.getAttribute("error") == null ? "remove-item" : ""%>"><%=request.getAttribute("error") != null ? request.getAttribute("error") : ""%></p>
            <button type="submit"> Aggiungi metodo </button>
            <div class="link-container">
                <p> Hai già un metodo di pagamento? </p>
                <a href="payment-method"> Pagamenti </a>
            </div>
        </div>
    </form>
    <script>

    </script>
    <script src="js/paymentMethodForm.js" type="text/javascript"></script>
</body>
</html>