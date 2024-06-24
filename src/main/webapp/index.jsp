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
</head>
<body>
    <%@include file="WEB-INF/navbar.jsp"%>
    <div class="comics-container">
        <%
            if (!comics.isEmpty()) {
            for(Comic comic : comics) {
        %>
        <div class="comic-container">
            <img src="<%=comic.getImmagine()%>" alt="<%=comic.getTitle()%>">
            <p class="title"><%=comic.getTitle()%></p>
            <p class="author"><%=comic.getAuthor()%></p>
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