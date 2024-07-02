<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>WebComics - Carrello</title>
    <link rel="stylesheet" href="Styles/nav.css">
    <link rel="stylesheet" href="Styles/footer.css">
    <script src="js/navbar.js" type="text/javascript"></script>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <link rel="stylesheet" href="Styles/comic-card.css"/>
</head>
<body>
    <%@include file="navbar.jsp"%>
    <h1 class="title-section">CARRELLO</h1>
    <c:set var="sizeCartComics" scope="page" value="<%=sizeCartComics%>"/>
    <c:if test="${sizeCartComics > 0}">
        <div class="comics-container">
        <%
            for(Comic comic : cartComics){
                DecimalFormat df = new DecimalFormat("#.00");
                String price = df.format(comic.getPrice());
                String finalPrice = df.format(comic.getFinalPrice());
                boolean isWished = false;
                if(wishComics != null && !wishComics.isEmpty()) {
                    isWished = wishComics.contains(comic);
                }
        %>
            <%@include file="comicCard.jsp"%>
        <%
            }
        %>
        </div>
    </c:if>
    <c:if test="${sizeCartComics == 0}">
        <div class="empty-section">
            <h1>Il tuo carrello è vuoto</h1>
            <img src="assets/icons/box.png" alt="Carrello vuoto"/>
        </div>
    </c:if>
    <%@include file="footer.jsp"%>
    <script src="js/comicCard.js"></script>
</body>
</html>