<%@ page import="Model.*" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title> Indirizzi di Spedizione - <%=session.getAttribute("nome")%> </title>
    <link rel="stylesheet" href="Styles/nav.css">
    <link rel="stylesheet" href="Styles/footer.css">
    <link rel="stylesheet" href="Styles/dataUser.css">
    <script src="js/navbar.js" type="text/javascript"></script>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="shortcut icon" href="assets/favicon.ico" type="image/x-icon">
</head>
<body>
    <%@include file="navbar.jsp"%>
    <div class="container-section">
        <%
            List<Address> addresses = (List<Address>) request.getAttribute("address");
            if (!addresses.isEmpty()) {
        %>
        <h1 class="title title-payment">Indirizzi Di Spedizione</h1>
        <%
            for (Address a : addresses) {
        %>
        <div class="container">
            <h1 class="title"><%=a.getIndirizzo()%></h1>
            <p>CAP: <span><%=a.getCap()%></span></p>
            <p>Provincia: <span><%=a.getProvincia()%></span></p>
        </div>
        <%
                }
            } else {
        %>
        <div class="empty-section">
            <h1>Non possiedi ancora indirizzi di spedizione</h1>
            <img src="assets/icons/address.png" alt="Non possiedi ancora indirizzi di spedizione"/>
        </div>
        <%
            }
        %>
        <button class="add-button" type="submit" value="Aggiungi indirizzo di spedizione" aria-label="aggiungi indirizzo di spedizione" onclick="location.href = 'address-form'">Aggiungi indirizzo di spedizione</button>
    </div>
    <%@include file="footer.jsp"%>
</body>
</html>