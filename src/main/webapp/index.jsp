<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page import="Model.Comic" %>
<%@ page import="Model.ComicDAO" %>
<%@ page import="java.util.List" %>
<html>
<%
    List<Comic> comics = ComicDAO.getNews("", 4);   //Stringa vuota vuol dire che non vogliamo specificare la categoria 4 vogliamo che il limite di fumetti sia 4
%>
<head>
    <title> WebComics - Home </title>
    <link rel="stylesheet" href="Styles/comic-card.css" />
    <link rel="stylesheet" href="Styles/home.css" />
    <link rel="stylesheet" href="Styles/slider.css" />
    <link rel="stylesheet" href="Styles/nav.css">
    <link rel="stylesheet" href="Styles/footer.css">
    <script src="js/navbar.js" type="text/javascript"></script>
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body>
    <%@include file="/WEB-INF/navbar.jsp"%>
    <div class="slider">
        <div class="slides">
            <div class="slide"><img src="assets/slider-image/image1.png" alt="Image 1"></div>
            <div class="slide"><img src="assets/slider-image/image2.png" alt="Image 2"></div>
            <div class="slide"><img src="assets/slider-image/image3.png" alt="Image 3"></div>
        </div>
        <button class="prev" onclick="moveSlide(-1)">&#10094;</button>
        <button class="next" onclick="moveSlide(1)">&#10095;</button>
    </div>
    <h1 class="title-section">MANGA & FUMETTI</h1>
    <div class="comics-container">
        <%
            if (comics != null) {
            for(Comic comic : comics) {
                DecimalFormat df = new DecimalFormat("#.00");
                String price = df.format(comic.getPrice());
                String finalPrice = df.format(comic.getFinalPrice());
                boolean isWished = false;
                if(wishComics != null && !wishComics.isEmpty()) {
                    isWished = wishComics.contains(comic);
                }
                System.out.println(isWished);
        %>
           <%@include file="WEB-INF/comicCard.jsp"%>
        <%
            }}
        %>
    </div>
    <%@include file="WEB-INF/footer.jsp"%>
    <script src="js/comicCard.js"></script>
    <script src="js/slider.js"></script>
</body>
</html>