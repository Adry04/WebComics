<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="Model.Comic" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page import="com.google.gson.Gson" %>
<html>
<%
    Comic comic = (Comic) request.getAttribute("comic");
    boolean isWished = (boolean) request.getAttribute("isWished");
    DecimalFormat df = new DecimalFormat("#.00");
    String price = df.format(comic.getPrice());
    String finalPrice = df.format(comic.getFinalPrice());
    Gson gson = new Gson();
    String comicJson = gson.toJson(comic);

%>
<link rel="stylesheet" href="Styles/comic-page.css">
<link rel="stylesheet" href="Styles/nav.css">
<link rel="stylesheet" href="Styles/footer.css">
<script src="js/navbar.js" type="text/javascript"></script>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="shortcut icon" href="assets/favicon.ico" type="image/x-icon">
<title><%=comic.getTitle()%></title>
<body>
    <%@include file="navbar.jsp"%>
    <div class="comic-page">
        <div class="image-section">
            <img src="<%=comic.getImmagine()%>" alt="<%=comic.getTitle()%>">
        </div>
        <div class="data-section">
            <p class="title"><%=comic.getTitle()%></p>
            <p class="description"><%=comic.getDesc()%></p>
            <p class="author"><%=comic.getAuthor()%></p>
            <div class="price-section">
                <%
                    if (comic.getSale() > 0) {
                %>
                <p class="sale-section">
                    <span class="price">
                        <%=price + " €"%>
                    </span>
                    <span class="sale">
                        <%=comic.getSale() + "%"%>
                    </span>
                </p>
                <%
                    }
                %>
                <p class="final-price"><%=finalPrice + " €"%></p>
            </div>
            <div class="button-section">
                <div class="quantity-section">
                    <button onclick="decrementQuantity()" tabindex="0" onkeydown="decrementQuantity()" aria-label="Decrement">
                        <span class="click-quantity">-</span>
                    </button>
                    <span id="quantity">1</span>
                    <button onclick="incrementQuantity()" aria-label="Increment" tabindex="0" onkeydown="incrementQuantity()">
                        <span class="click-quantity">+</span>
                    </button>
                </div>
                <button class="cart-button" onclick='addCart("<%=comic.getISBN()%>", -1, <%=comicJson%>)'>Aggiungi al carrello</button>
            </div>
        </div>
        <button class="wish-container" data-is-wished="<%=isWished%>" onclick="toggleWish('<%=comic.getISBN()%>')" id="wish-container-<%=comic.getISBN()%>" aria-label="Toggle Wish" tabindex="0" onkeydown="toggleWish('<%=comic.getISBN()%>')">
            <svg aria-label="no wish" id="no-wish-<%=comic.getISBN()%>" class="wish <%=isWished ? "no-display" : ""%>" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24"><path fill="#AA5050" d="m12.1 18.55l-.1.1l-.11-.1C7.14 14.24 4 11.39 4 8.5C4 6.5 5.5 5 7.5 5c1.54 0 3.04 1 3.57 2.36h1.86C13.46 6 14.96 5 16.5 5c2 0 3.5 1.5 3.5 3.5c0 2.89-3.14 5.74-7.9 10.05M16.5 3c-1.74 0-3.41.81-4.5 2.08C10.91 3.81 9.24 3 7.5 3C4.42 3 2 5.41 2 8.5c0 3.77 3.4 6.86 8.55 11.53L12 21.35l1.45-1.32C18.6 15.36 22 12.27 22 8.5C22 5.41 19.58 3 16.5 3"></path></svg>
            <svg aria-label="wish" id="wish-<%=comic.getISBN()%>" class="wish <%=isWished ? "" : "no-display"%>" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24"><path fill="#AA5050" d="m12 21.35l-1.45-1.32C5.4 15.36 2 12.27 2 8.5C2 5.41 4.42 3 7.5 3c1.74 0 3.41.81 4.5 2.08C13.09 3.81 14.76 3 16.5 3C19.58 3 22 5.41 22 8.5c0 3.77-3.4 6.86-8.55 11.53z"></path></svg>
        </button>
    </div>
    <%@include file="footer.jsp"%>
    <script src="js/comicCard.js"></script>
    <script src="js/comicPage.js"></script>
</body>
</html>