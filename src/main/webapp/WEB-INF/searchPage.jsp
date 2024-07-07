<%@ page import="java.text.DecimalFormat" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Fumetti - <%=request.getParameter("parameter")%></title>
    <link rel="stylesheet" href="Styles/nav.css">
    <link rel="stylesheet" href="Styles/footer.css">
    <script src="js/navbar.js" type="text/javascript"></script>
    <link rel="stylesheet" href="Styles/comic-card.css">
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body>
    <%@include file="navbar.jsp"%>
    <%
        List<Comic> comics = (List<Comic>) request.getAttribute("comics");
        if (!comics.isEmpty()) {
    %>
       <h1>Ricerca per <%=request.getParameter("parameter")%></h1>
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
        <%@include file="comicCard.jsp"%>
    <%
    }
    %>
    </div>
    <% } else {
    %>
    <div class="empty-section">
        <h1>Non ci sono fumetti con questo parametro di ricerca: "<%=request.getParameter("parameter")%>"</h1>
    </div>
    <%
        }
    %>
    <%@include file="footer.jsp"%>
</body>
</html>