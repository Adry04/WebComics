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
        <button class="subsection" onclick="location.href = 'order'" aria-label="I miei ordini" onkeydown="location.href = 'order'">
            <h2>I miei ordini</h2>
        </button>
        <button class="subsection" onclick="location.href = 'payment-method'" aria-label="Metodi di pagamento" onkeydown="location.href = 'payment-method'">
            <h2>Metodi di pagamento</h2>
        </button>
        <button class="subsection" onclick="location.href = 'address'" aria-label="Indirizzi di spedizione" onkeydown="location.href = 'address'">
            <h2 >Indirizzi di spedizione</h2>
        </button>
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