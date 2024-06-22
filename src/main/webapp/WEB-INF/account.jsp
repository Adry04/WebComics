<html>
<head>
    <title> <%=session.getAttribute("nome")%> - Account </title>
    <link rel="stylesheet" href="Styles/account.css">
</head>
<body>
    <%@include file="navbar.jsp"%>
    <h1>Ciao <%=session.getAttribute("nome")%></h1>
    <h2>Il mio account</h2>
    <div class="section-container">
        <div class="subsection">
            <!--ORDINI-->
        </div>
        <div class="subsection">
            <!--PAGAMENTI-->
        </div>
        <div class="subsection">
            <!--INDIRIZZI DI SPEDIZIONE-->
        </div>
        <button onclick="logout()" class="logout-button">LOGOUT</button>
    </div>
    <%@include file="footer.jsp"%>
    <script type="text/javascript">
        const logout = () => {
            const url = document.URL
            const body = 'action=logout';
            fetch(url, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                },
                body: body //passa il parametro con questo valore
            }).then(e => {
                window.location = document.baseURI
            }).catch(error => {
                console.log(error)
            })
        }
    </script>
</body>
</html>