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
            for(Comic comic : comics) {
        %>
            <div class="comic-container">
                <img src="<%=comic.getImmagine()%>" alt="<%=comic.getTitle()%>">
                <p class="title"><%=comic.getTitle()%></p>
            </div>
        <%
            }
        %>
    </div>
    <%@include file="WEB-INF/footer.jsp"%>
</body>
</html>