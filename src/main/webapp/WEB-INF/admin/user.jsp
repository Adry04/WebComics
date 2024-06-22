<%@ page import="java.util.List" %>
<%@ page import="Model.User" %>
<html>
<head>
    <link rel="stylesheet" href="Styles/admin/user.css">
</head>
<body>
    <%@include file="navadmin.jsp"%>
    <div class="container-page">
        <div class="title-page">
            <h1>Utenti</h1>
            <svg xmlns="http://www.w3.org/2000/svg" width="1em" height="1em" viewBox="0 0 24 24"><path fill="black" fill-rule="evenodd" d="M8 4a4 4 0 1 0 0 8a4 4 0 0 0 0-8m-2 9a4 4 0 0 0-4 4v1a2 2 0 0 0 2 2h8a2 2 0 0 0 2-2v-1a4 4 0 0 0-4-4zm7.25-2.095c.478-.86.75-1.85.75-2.905a6 6 0 0 0-.75-2.906a4 4 0 1 1 0 5.811M15.466 20c.34-.588.535-1.271.535-2v-1a5.98 5.98 0 0 0-1.528-4H18a4 4 0 0 1 4 4v1a2 2 0 0 1-2 2z" clip-rule="evenodd"/></svg>
        </div>
        <div class="user-contaier">
            <%
                List<User> users = (List<User>) request.getAttribute("users");
                for (User user : users) {
            %>
                <div class="user-section" title="<%=user.completeName()%>">
                    <p><%=user.completeName()%></p>
                    <p><%=user.getEmail()%></p>
                </div>
             <% } %>
        </div>
    </div>
</body>
</html>