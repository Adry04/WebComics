<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page import="Model.Comic" %>
<%@ page import="Model.ComicDAO" %>
<%@ page import="java.util.List" %>
<%@ page import="java.sql.SQLException" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<%
    List<Comic> comics = new ArrayList<>();
    List<Comic> comicsManga = new ArrayList<>();
    List<Comic> comicsFumetto = new ArrayList<>();
    try {
        comics = ComicDAO.getNews("", 8);    //Stringa vuota vuol dire che non vogliamo specificare la categoria 4 vogliamo che il limite di fumetti sia 4
        comicsManga = ComicDAO.getNews("manga", 8);
        comicsFumetto = ComicDAO.getNews("fumetto", 8);
    } catch (SQLException e) {
        e.printStackTrace(System.out);
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        throw new RuntimeException("Errore durante l'accesso al database", e);
    }
%>
<head>
    <title> WebComics - Home </title>
    <link rel="stylesheet" href="Styles/comic-card.css" />
    <link rel="stylesheet" href="Styles/slider.css" />
    <link rel="stylesheet" href="Styles/nav.css">
    <link rel="stylesheet" href="Styles/footer.css">
    <script src="js/navbar.js" type="text/javascript"></script>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="shortcut icon" href="assets/favicon.ico" type="image/x-icon">
</head>
<body>
    <%@include file="/WEB-INF/navbar.jsp"%>
    <div class="slider">
        <div class="slides">
            <div class="slide"><img onclick="location.href ='search?parameter=supereroi'"  aria-label="Supereroi" title="Visualizza i nostri fumetti di supereroi" src="assets/slider-image/image1.png" alt="Image 1"></div>
            <div class="slide"><img onclick="location.href ='search?parameter=nuove-uscite'" aria-label="Nuove Uscite" title="Visualizza le nostre nuove uscite" src="assets/slider-image/image2.png" alt="Image 2"></div>
            <div class="slide"><img onclick="location.href = 'search?parameter=shonen'" aria-label="Shonen" title="Visualizza i nostri shonen" src="assets/slider-image/image3.png" alt="Image 3"></div>
        </div>
        <button class="prev" onclick="moveSlide(-1)" aria-label="Prev Slide">&#10094;</button>
        <button class="next" onclick="moveSlide(1)" aria-label="Next Slide">&#10095;</button>
    </div>

        <%
            if (!comics.isEmpty()) {
        %>
            <h1 class="title-section">NUOVE USCITE - FUMETTI</h1>
            <div class="comics-container">
            <%
                for(Comic comic : comics) {
                    DecimalFormat df = new DecimalFormat("#.00");
                    String price = df.format(comic.getPrice());
                    String finalPrice = df.format(comic.getFinalPrice());
                    boolean isWished = false;
                    if(wishComics != null && !wishComics.isEmpty()) {
                        isWished = wishComics.contains(comic);
                    }
            %>
               <%@include file="WEB-INF/comicCard.jsp"%>
            <%
                    }
                }
            %>
            </div>
        <%
            if (!comicsManga.isEmpty()) {
        %>
        <h1 class="title-section">NUOVE USCITE - MANGA</h1>
    <div class="comics-container">
        <%
            for(Comic comic : comicsManga) {
                DecimalFormat df = new DecimalFormat("#.00");
                String price = df.format(comic.getPrice());
                String finalPrice = df.format(comic.getFinalPrice());
                boolean isWished = false;
                if(wishComics != null && !wishComics.isEmpty()) {
                    isWished = wishComics.contains(comic);
                }
        %>
        <%@include file="WEB-INF/comicCard.jsp"%>
        <%
                }
            }
        %>
    </div>
        <%
            if (!comicsFumetto.isEmpty()) {
        %>
        <h1 class="title-section">NUOVI FUMETTI</h1>
    <div class="comics-container">
        <%
            for(Comic comic : comicsFumetto) {
                DecimalFormat df = new DecimalFormat("#.00");
                String price = df.format(comic.getPrice());
                String finalPrice = df.format(comic.getFinalPrice());
                boolean isWished = false;
                if(wishComics != null && !wishComics.isEmpty()) {
                    isWished = wishComics.contains(comic);
                }
        %>
        <%@include file="WEB-INF/comicCard.jsp"%>
        <%
                }
            }
        %>
    </div>
    <%@include file="WEB-INF/footer.jsp"%>
    <script src="js/comicCard.js"></script>
    <script src="js/slider.js"></script>
</body>
</html>