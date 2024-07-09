<%@ page import="Model.Order" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <link rel="stylesheet" href="Styles/admin/order.css">
    <title>Admin Page - <%=session.getAttribute("nome")%> Ordini</title>
    <link rel="stylesheet" href="Styles/admin/navadmin.css"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
    <%@include file="navadmin.jsp"%>
    <div class="container-page">
        <div class="title-page">
            <h1>Ordini</h1>
            <svg xmlns="http://www.w3.org/2000/svg" width="1em" height="1em" viewBox="0 0 24 24"><path fill="none" stroke="black" stroke-linecap="round" stroke-linejoin="round" stroke-width="1.5" d="m12 21l8.131-4.208c.316-.164.474-.245.589-.366a1 1 0 0 0 .226-.373c.054-.159.054-.336.054-.692V7.533M12 21l-8.131-4.208c-.316-.164-.474-.245-.589-.366a1.009 1.009 0 0 1-.226-.373C3 15.894 3 15.716 3 15.359V7.533M12 21v-9.063m9-4.404l-9 4.404m9-4.404l-4.5-2.33M3 7.534l8.269-4.28c.268-.138.401-.208.542-.235a.994.994 0 0 1 .378 0c.14.027.274.097.541.235l3.77 1.95M3 7.534l4.5 2.202m4.5 2.202L7.5 9.735m0 0l9-4.531m-9 4.531v2.202"></path></svg>        </div>
        <%
            List<Order> orders = new ArrayList<>();
            if(session.getAttribute("orders") != null) {
                orders = (List<Order>) session.getAttribute("orders");
            }
            for (Order order : orders) {
                DecimalFormat df = new DecimalFormat("#.00");
                String totalPrice = df.format(order.getPrezzoTotale());
        %>
        <div class="order-section" onclick="location.href = 'order-page?id=<%=order.getIdOrdine()%>'">
            <p class="data">Quantità: <%=order.getQuantita()%></p>
            <p class="price">Prezzo: <%=totalPrice%> €</p>
        </div>
        <%
            }
        %>
    </div>
</body>
</html>