<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="Model.Comic" %>
<%@ page import="java.util.List" %>
<%@ page import="Model.ComicDAO" %>
<html>
<%
    List<Comic> comics = ComicDAO.getComics();
%>
<head>
    <title> WebComics - Home </title>
    <link rel="stylesheet" href="Styles/comic-card.css" />
    <link rel="stylesheet" href="Styles/home.css" />
</head>
<body>
    <%@include file="WEB-INF/navbar.jsp"%>
    <div class="comics-container">
        <%
            if (!comics.isEmpty()) {
            for(Comic comic : comics) {
        %>
            <h1 class="title-section">MANGE & FUMETTI</h1>
            <div class="comic-card">
                <img src="<%=comic.getImmagine()%>" alt="<%=comic.getTitle()%>">
                <p class="title">
                    <%=comic.getTitle().length() > 20 ? comic.getTitle().substring(0, 20) + "..." : comic.getTitle() %>
                </p>
                <%
                    if (comic.getSale() > 0) {
                %>
                    <p class="sale-section">
                        <span class="price">
                            <%=comic.getPrice() + " €"%>
                        </span>
                        <span class="sale">
                            <%=comic.getSale() + "%"%>
                        </span>
                        <span>
                            <%=comic.getData()%>
                        </span>
                    </p>
                <%
                    }
                %>
                <p class="price"><%=comic.getFinalPrice() + " €"%></p>
            </div>
        <%
            }}
        %>
    </div>
    <%@include file="WEB-INF/footer.jsp"%>
</body>
</html>