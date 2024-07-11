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
        <div class="input-container">
            <div>
                <label for="select-address">Seleziona l'indirizzo di spedizione</label>
                <select id="select-address">
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
                    <select id="select-payment">
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
            <button type="submit">Effettua ordine</button>
        </div>
    </form>
</body>
</html>