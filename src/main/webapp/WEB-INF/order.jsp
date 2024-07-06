<%@ page import="Model.*" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title>WebComics - <%=(session.getAttribute("userId") == null) ? "Ordini" : session.getAttribute("userId") + " Ordini"%></title>
    <link rel="stylesheet" href="Styles/nav.css">
    <link rel="stylesheet" href="Styles/footer.css">
    <script src="js/navbar.js" type="text/javascript"></script>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="Styles/comic-card.css">
    <link rel="stylesheet" href="Styles/order.css">
</head>
<body>
    <%@include file="navbar.jsp"%>
    <div class="container-page">
        <h1 class="title-section">Ordini</h1>
        <%
            List<Order> orders = new ArrayList<>();
            if(session.getAttribute("orders") != null) {
                orders = (List<Order>) session.getAttribute("orders");
            }
            for (Order order : orders) {
                DecimalFormat df = new DecimalFormat("#.00");
                String totalPrice = df.format(order.getPrezzoTotale());
        %>
        <div class="order-container" onclick="location.href = 'order-page?id=<%=order.getIdOrdine()%>'">
            <p class="data">Quantità: <%=order.getQuantita()%></p>
            <p class="price">Prezzo: <%=totalPrice%> €</p>
        </div>
        <%
            }
        %>
    </div>
    <%@include file="footer.jsp"%>
    <script src="js/comicCard.js"></script>
</body>
</html>