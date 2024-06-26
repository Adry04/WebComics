<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="Model.Comic" %>
<%@ page import="java.util.List" %>
<%@ page import="Model.ComicDAO" %>
<%@ page import="java.text.DecimalFormat" %>
<html>
<%
    List<Comic> comics = ComicDAO.getNews("", 4); //Stringa vuota vuol dire che non vogliamo specificare la categoria 4 vogliamo che il limite di fumetti sia 4
%>
<head>
    <title> WebComics - Home </title>
    <link rel="stylesheet" href="Styles/comic-card.css" />
    <link rel="stylesheet" href="Styles/home.css" />
    <link rel="stylesheet" href="Styles/slider.css" />
</head>
<body>
    <%@include file="WEB-INF/navbar.jsp"%>
    <div class="slider-container">
        <span id="slider-image-1"></span>
        <span id="slider-image-2"></span>
        <span id="slider-image-3"></span>
        <div class="image-container">
            <img src="" class="slider-image" alt="Slider 1"/>
            <img src="" class="slider-image" alt="Slider 2"/>
            <img src="" class="slider-image" alt="Slider 3"/>
        </div>
        <div class="button-container">
            <a href="#slider-image-1" class="slider-change"></a>
            <a href="#slider-image-2" class="slider-change"></a>
            <a href="#slider-image-3" class="slider-change"></a>
        </div>
    </div>
    <h1 class="title-section">MANGA & FUMETTI</h1>
    <div class="comics-container">
        <%
            if (!comics.isEmpty()) {
            for(Comic comic : comics) {
                DecimalFormat df = new DecimalFormat("#.00");
                String price = df.format(comic.getPrice());
                String finalPrice = df.format(comic.getFinalPrice());
        %>
            <div class="comic-card">
                <img src="<%=comic.getImmagine()%>" alt="<%=comic.getTitle()%>">
                <p class="title">
                    <%=comic.getTitle().length() > 20 ? comic.getTitle().substring(0, 20) + "..." : comic.getTitle() %>
                </p>
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
                <div class="wish-container">
                    <svg id="no-wish" class="wish" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24"><path fill="#AA5050" d="m12.1 18.55l-.1.1l-.11-.1C7.14 14.24 4 11.39 4 8.5C4 6.5 5.5 5 7.5 5c1.54 0 3.04 1 3.57 2.36h1.86C13.46 6 14.96 5 16.5 5c2 0 3.5 1.5 3.5 3.5c0 2.89-3.14 5.74-7.9 10.05M16.5 3c-1.74 0-3.41.81-4.5 2.08C10.91 3.81 9.24 3 7.5 3C4.42 3 2 5.41 2 8.5c0 3.77 3.4 6.86 8.55 11.53L12 21.35l1.45-1.32C18.6 15.36 22 12.27 22 8.5C22 5.41 19.58 3 16.5 3"/></svg>
                    <svg id="wish" class="wish no-display" xmlns="http://www.w3.org/2000/svg" width="1em" height="1em" viewBox="0 0 24 24"><path fill="#AA5050" d="m12 21.35l-1.45-1.32C5.4 15.36 2 12.27 2 8.5C2 5.41 4.42 3 7.5 3c1.74 0 3.41.81 4.5 2.08C13.09 3.81 14.76 3 16.5 3C19.58 3 22 5.41 22 8.5c0 3.77-3.4 6.86-8.55 11.53z"/></svg>
                </div>
                <button class="button-product">AGGIUNGI AL CARRELLO</button>
            </div>
        <%
            }}
        %>
    </div>
    <%@include file="WEB-INF/footer.jsp"%>
    <script src="js/comicCard.js"></script>
</body>
</html>