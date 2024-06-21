<%@ page import="java.util.List" %>
<%@ page import="Model.User" %>
<html>
<head>
    <link rel="stylesheet" href="../../Styles/admin/user.css">
</head>
<body>
    <div class="container-page">
        <%@include file="navadmin.jsp"%>
        <div class="user-contaier">
            <%
                List<User> users = (List<User>) request.getAttribute("users");
                for (User user : users) {
            %>
                <div class="user-section">
                    <p><%=user.completeName()%></p>
                    <p><%=user.getEmail()%></p>
                </div>
             <% } %>
        </div>
    </div>
</body>
</html>