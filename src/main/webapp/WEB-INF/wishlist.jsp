<%@ page import="java.text.DecimalFormat" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title> WebComics - Wishlist </title>
    <link rel="stylesheet" href="Styles/nav.css">
    <link rel="stylesheet" href="Styles/footer.css">
    <script src="js/navbar.js" type="text/javascript"></script>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="Styles/comic-card.css" />
</head>
<body>
    <%@include file="navbar.jsp"%>
        <%
            if(sizeWishes > 0) {
        %>
        <div class="comics-container">
        <%
            for (Comic comic : wishComics) {
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
    <%
        } else {
    %>
        <div class="empty-section">
            <h1>La tua Wishlist è vuota</h1>
            <img src="assets/icons/box.png" alt="Non hai preferiti" />
        </div>
    <%
        }
    %>
    <%@include file="footer.jsp"%>
    <script src="js/comicCard.js"></script>
</body>
</html>