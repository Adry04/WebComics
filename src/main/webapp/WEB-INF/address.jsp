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
        <h1 class="title title-payment" id="title-address" data-size="<%=addresses.size()%>">Indirizzi Di Spedizione</h1>
        <%
            for (Address a : addresses) {
        %>
        <div class="container" id="address-<%=a.getId()%>">
            <button aria-label="Delete" onclick="deleteAddress(<%=a.getId()%>)" tabindex="0" onkeydown="deleteAddress(<%=a.getId()%>)" class="delete-button">
                <svg aria-label="Delete SVG" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 14 14"><path fill="black" fill-rule="evenodd" d="M1.707.293A1 1 0 0 0 .293 1.707L5.586 7L.293 12.293a1 1 0 1 0 1.414 1.414L7 8.414l5.293 5.293a1 1 0 0 0 1.414-1.414L8.414 7l5.293-5.293A1 1 0 0 0 12.293.293L7 5.586z" clip-rule="evenodd"/></svg>
            </button>
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
        <button class="add-button" aria-label="aggiungi indirizzo di spedizione" onclick="location.href = 'address-form'">Aggiungi indirizzo di spedizione</button>
    </div>
    <%@include file="footer.jsp"%>
    <script src="js/userData.js"></script>
</body>
</html>