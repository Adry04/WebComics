<%@ page import="java.util.List" %>
<%@ page import="Model.Comic" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <link rel="stylesheet" href="Styles/admin/comic.css">
    <title>Admin Page - <%=session.getAttribute("nome")%></title>
    <link rel="stylesheet" href="Styles/admin/navadmin.css" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="Styles/admin/comicCard.css">
    <link rel="shortcut icon" href="assets/favicon.ico" type="image/x-icon">
</head>
<body>
    <%@include file="navadmin.jsp"%>
    <div class="container-page">
        <div class="top-section">
            <div class="text-top-section">
                <h1>Prodotti</h1>
                <svg aria-label="Prodotti" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 48 48"><g fill="none" stroke="#000" stroke-linejoin="round" stroke-width="4"><path d="M44 14L24 4L4 14V34L24 44L44 34V14Z"></path><path stroke-linecap="round" d="M4 14L24 24"></path><path stroke-linecap="round" d="M24 44V24"></path><path stroke-linecap="round" d="M44 14L24 24"></path><path stroke-linecap="round" d="M34 9L14 19"></path></g></svg>
            </div>
            <button class="add-button" onclick="location.href = 'admin-comic-form'">
                Crea prodotto
                <svg aria-label="Crea Prodotto" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24"><g fill="none" fill-rule="evenodd"><path d="M24 0v24H0V0zM12.594 23.258l-.012.002l-.071.035l-.02.004l-.014-.004l-.071-.036q-.016-.004-.024.006l-.004.01l-.017.428l.005.02l.01.013l.104.074l.015.004l.012-.004l.104-.074l.012-.016l.004-.017l-.017-.427q-.004-.016-.016-.018m.264-.113l-.014.002l-.184.093l-.01.01l-.003.011l.018.43l.005.012l.008.008l.201.092q.019.005.029-.008l.004-.014l-.034-.614q-.005-.018-.02-.022m-.715.002a.02.02 0 0 0-.027.006l-.006.014l-.034.614q.001.018.017.024l.015-.002l.201-.093l.01-.008l.003-.011l.018-.43l-.003-.012l-.01-.01z"></path><path fill="white" d="M9 5a2 2 0 0 1 2-2h2a2 2 0 0 1 2 2v4h4a2 2 0 0 1 2 2v2a2 2 0 0 1-2 2h-4v4a2 2 0 0 1-2 2h-2a2 2 0 0 1-2-2v-4H5a2 2 0 0 1-2-2v-2a2 2 0 0 1 2-2h4z"></path></g></svg>
            </button>
        </div>
        <div class="comics-container">
            <%
                if(request.getAttribute("comics") != null) {
                    List<Comic> comics = (List<Comic>) request.getAttribute("comics");
                    for (Comic comic : comics) {
                        DecimalFormat df = new DecimalFormat("#.00");
                        String price = df.format(comic.getPrice());
                        String finalPrice = df.format(comic.getFinalPrice());
            %>
            <div class="comic-container">
                <%@include file="comicCardAdmin.jsp"%>
            </div>
            <%
                    }
                }
            %>
        </div>
    </div>
</body>
</html>