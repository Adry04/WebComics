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
        <svg class="heart" xmlns="http://www.w3.org/2000/svg" width="1.5em" height="1.5em" viewBox="0 0 256 256"><path fill="currentColor" d="M178 40c-20.65 0-38.73 8.88-50 23.89C116.73 48.88 98.65 40 78 40a62.07 62.07 0 0 0-62 62c0 70 103.79 126.66 108.21 129a8 8 0 0 0 7.58 0C136.21 228.66 240 172 240 102a62.07 62.07 0 0 0-62-62m-50 174.8c-18.26-10.64-96-59.11-96-112.8a46.06 46.06 0 0 1 46-46c19.45 0 35.78 10.36 42.6 27a8 8 0 0 0 14.8 0c6.82-16.67 23.15-27 42.6-27a46.06 46.06 0 0 1 46 46c0 53.61-77.76 102.15-96 112.8"></path></svg>
    </div>
    <%@include file="footer.jsp"%>
</body>
</html>