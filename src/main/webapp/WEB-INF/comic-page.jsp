<%@ page import="Model.Comic" %>
<html>
<head>
    <link rel="stylesheet" href="Styles/comic-page.css">
</head>
<body>
    <%@include file="navbar.jsp"%>
    <%
        Comic comic = (Comic) request.getAttribute("comic");
    %>
    <div class="comic-page">
        <img src="<%=comic.getImmagine()%>" alt="<%=comic.getTitle()%>">
        <p class="title"><%=comic.getTitle()%></p>
        <p class="description"><%=comic.getDesc()%></p>
        <p class="author"><%=comic.getAuthor()%></p>
        <p class="price"><%=comic.getPrice()%></p>
    </div>
    <%@include file="footer.jsp"%>
</body>
</html>