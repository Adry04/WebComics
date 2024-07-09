<%@ page import="Model.Order" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <link rel="stylesheet" href="Styles/order.css">
    <title>Admin Page - <%=session.getAttribute("nome")%> Ordini</title>
    <link rel="stylesheet" href="Styles/admin/navadmin.css"/>
    <link rel="stylesheet" href="Styles/order.css"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
    <%@include file="navadmin.jsp"%>
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
</body>
</html>