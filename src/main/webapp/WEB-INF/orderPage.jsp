<%@ page import="Model.Order" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title>WebComics - Ordini</title>
    <link rel="stylesheet" href="Styles/comic-card.css" />
    <link rel="stylesheet" href="Styles/nav.css">
    <link rel="stylesheet" href="Styles/footer.css">
    <script src="js/navbar.js" type="text/javascript"></script>
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<%
    Order order = (Order) request.getAttribute("order");
%>
<body>
    <%@include file="navbar.jsp"%>
    <h1 class="title-section">ORDINE NUMERO: <%=order.getIdOrdine()%></h1>
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
            <%@include file="comicCard.jsp"%>
        <%
            }
        %>
    </div>
    <%@include file="footer.jsp"%>
    <script src="js/comicCard.js"></script>
</body>
</html>