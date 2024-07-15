<%@ page import="java.text.DecimalFormat" %>
<%@ page import="Model.*" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>WebComics - Ordini</title>
    <link rel="stylesheet" href="Styles/comic-card.css" />
    <link rel="stylesheet" href="Styles/nav.css">
    <link rel="stylesheet" href="Styles/footer.css">
    <script src="js/navbar.js" type="text/javascript"></script>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="shortcut icon" href="assets/favicon.ico" type="image/x-icon">    
</head>
<%
    Order order = (Order) request.getAttribute("order");
%>
<body>
    <%@include file="navbar.jsp"%>
    <div class="title-container">
        <h1 class="title-section">ORDINE NUMERO: <%=order.getIdOrdine()%></h1>
        <h1 class="title-section">Totale ordine: <%=order.getStringPrezzoTotale()%> €</h1>
        <c:if test="${sessionScope.isAdmin && requestScope.orderUser != null}">
            <% User user = (User) request.getAttribute("orderUser"); %>
            <h1 class="title-section">Utente: <%=user.getEmail()%></h1>
        </c:if>
        <h1 class="title-section">Indirizzo: <%=order.getIndirizzo()%>, <%=order.getCAP()%></h1>
        </div>
    <div class="comics-container">
        <%
            List<Comic> comics = order.getComics();
            for (Comic comic : comics) {
                DecimalFormat df = new DecimalFormat("#.00");
                String price = df.format(comic.getPrice());
                String finalPrice = df.format(comic.getPrice());
                boolean isWished = false;
                if (wishComics != null && !wishComics.isEmpty()) {
                    isWished = wishComics.contains(comic);
                }
        %>
            <div class="single-card-container">
                <p class="quantity-text">x <%=comic.getQuantita()%></p>
                <%@include file="comicCard.jsp"%>
            </div>
        <%
            }
        %>
    </div>
    <%@include file="footer.jsp"%>
    <script src="js/comicCard.js"></script>
</body>
</html>