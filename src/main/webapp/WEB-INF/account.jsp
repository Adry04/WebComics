<html>
<head>
    <title> <%=session.getAttribute("nome")%> - Account </title>
    <link rel="stylesheet" href="Styles/account.css">
    <link rel="stylesheet" href="Styles/nav.css">
    <link rel="stylesheet" href="Styles/footer.css">
    <script src="js/navbar.js" type="text/javascript"></script>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="shortcut icon" href="assets/favicon.ico" type="image/x-icon">
</head>
<body>
    <%@include file="navbar.jsp"%>
    <h1>Ciao <%=session.getAttribute("nome")%></h1>
    <h2>Il mio account:</h2>
    <div class="account-section-container">
        <div class="subsection">
            <h2  onclick="location.href = 'order'" aria-label="I miei ordini">I miei ordini</h2>
        </div>
        <div class="subsection">
            <h2 onclick="location.href = 'payment-method'" aria-label="Metodi di pagamento">Metodi di pagamento</h2>
        </div>
        <div class="subsection">
            <h2 onclick="location.href = 'address'" aria-label="Indirizzi di spedizione">Indirizzi di spedizione</h2>
        </div>
        <button type="button" onclick="logout()" class="logout-button">LOGOUT</button>
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
            }).then(() => {
                window.location = document.baseURI
            }).catch(error => {
                console.log(error)
            })
        }
    </script>
</body>
</html>