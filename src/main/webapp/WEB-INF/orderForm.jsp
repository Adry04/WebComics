<!-- form per l'effettuazione dell'ordine -->
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>Creazione ordine</title>
    <link rel="stylesheet" href="Styles/form.css">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="shortcut icon" href="assets/favicon.ico" type="image/x-icon">
</head>
<body>
    <form action="" method="POST">
        <button class="onclick-button" onclick="history.back()" aria-label="Back Button" onkeydown="history.back()" type="button">
            <svg aria-label="Back" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 32 32"><path fill="#321b41" d="m2.36 11.23l8.31 7.57c.61.56 1.6.12 1.6-.71v-3.63c0-.35.29-.64.64-.64h15.86c.66 0 1.19-.53 1.19-1.19V8.42c0-.66-.53-1.19-1.19-1.19H12.91c-.35 0-.64-.29-.64-.64V2.96c0-.83-.99-1.27-1.6-.71L2.36 9.82a.946.946 0 0 0 0 1.41m13.97 17.53l-2.54-6.95c-.16-.49-.62-.81-1.13-.81c-.51 0-.97.32-1.12.79l-2.57 6.98c-.18.48.07 1 .55 1.18c.1.03.21.05.32.05c.37 0 .73-.23.86-.6l.41-1.13c.04.01.08.01.12.01h2.87c.03 0 .07 0 .1-.01l.41 1.12c.18.48.7.73 1.18.55c.47-.17.71-.7.54-1.18m-4.54-2.32l.87-2.38l.86 2.38zm-3.56-2.73c0 .53-.15 1.02-.41 1.43a2.7 2.7 0 0 1 1.07 2.15c0 1.38-1.05 2.54-2.4 2.69c-.04.01-.09.02-.13.01c-.06.01-.12.01-.18.01H2.92a.92.92 0 0 1-.92-.93v-7.16c0-.5.41-.91.92-.91h2.6c.1 0 .19 0 .27.01a7.905 7.905 0 0 0 .19.03c1.28.22 2.25 1.34 2.25 2.67m-2.7-.87H3.84v1.74h1.68c.48 0 .88-.39.88-.87a.87.87 0 0 0-.87-.87m-1.69 3.57v1.74h2.35a.87.87 0 0 0 0-1.74zm18.76 1.94a3.308 3.308 0 0 1-6.17-1.66v-2.38c0-1.83 1.48-3.31 3.31-3.31c1.18 0 2.28.64 2.87 1.65c.26.44.1 1-.33 1.26c-.44.26-1 .11-1.26-.33a1.5 1.5 0 0 0-1.28-.74c-.8 0-1.47.66-1.47 1.47v2.38c0 .81.67 1.47 1.47 1.47c.53 0 1.01-.28 1.28-.74c.26-.43.81-.59 1.26-.33c.42.26.58.82.32 1.26m4.63-3.48l2.6 3.68c.29.42.2.99-.23 1.29c-.42.29-.99.19-1.28-.22l-2.41-3.42l-.69.69v2.2c0 .51-.41.92-.92.92s-.92-.41-.92-.92v-7.17c0-.51.41-.92.92-.92s.92.41.92.92v2.37l3.01-3.02c.36-.36.94-.36 1.3 0c.36.36.36.94 0 1.3z"></path></svg>
        </button>
        <p class="title-form">Seleziona dati per il pagamento</p>
        <div class="input-container">
            <div>
                <label for="select-address">Seleziona l'indirizzo di spedizione</label>
                <select id="select-address" name="address">
                    <c:forEach var="item" items="${requestScope.addresses}">
                        <option value="${item.id}">
                            ${item.getIndirizzo()}, ${item.getCap()}
                        </option>
                    </c:forEach>
                </select>
            </div>
            <c:if test="${not empty requestScope.creditCards or not empty requestScope.bankAccounts}">
                <div>
                    <label for="select-payment">Seleziona metodo di pagamento</label>
                    <select id="select-payment" name="payment">
                            <c:forEach var="item" items="${requestScope.creditCards}">
                                <option value="${item.id}-Carta">
                                    Carta: *************${item.getNumero().substring(item.getNumero().length() - 4)}
                                </option>
                            </c:forEach>
                            <c:forEach var="item" items="${requestScope.bankAccounts}">
                                <option value="${item.id}-Conto">
                                    Conto: **********************${item.getIBAN().substring(item.getIBAN().length() - 4)}
                                </option>
                            </c:forEach>
                    </select>
                </div>
            </c:if>
            <p id="error-text" class="error-text <%=request.getAttribute("error-form") == null ? "remove-item" : ""%>"><%=request.getAttribute("error-form") != null ? request.getAttribute("error-form") : ""%></p>
            <button type="submit">Effettua ordine</button>
        </div>
    </form>
    <script src="js/form.js"></script>
</body>
</html>