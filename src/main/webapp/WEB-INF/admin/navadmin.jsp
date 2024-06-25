<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>Admin Page - <%=session.getAttribute("nome")%></title>
    <link rel="stylesheet" href="Styles/admin/navadmin.css" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
    <nav>
        <div class="top-section">
            <img onclick="window.open('./')" src="assets/logo.png" alt="Logo WebComics">
            <div title="Visualizza sito">
                <svg onclick="window.open('./')" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 2048 2048"><path fill="white" d="M1024 0q141 0 272 36t245 103t207 160t160 208t103 245t37 272q0 141-36 272t-103 245t-160 207t-208 160t-245 103t-272 37q-141 0-272-36t-245-103t-207-160t-160-208t-103-244t-37-273q0-141 36-272t103-245t160-207t208-160T751 37t273-37m0 1920q123 0 237-32t214-90t182-141t140-181t91-214t32-238q0-123-32-237t-90-214t-141-182t-181-140t-214-91t-238-32q-123 0-237 32t-214 90t-182 141t-140 181t-91 214t-32 238q0 123 32 237t90 214t141 182t181 140t214 91t238 32m597-880l48-144h75l-85 256h-75l-48-144l-48 144h-75l-85-256h75l48 144l48-144h74zm-464-144h75l-85 256h-75l-48-144l-48 144h-75l-85-256h75l48 144l48-144h74l48 144zm-512 0h75l-85 256h-75l-48-144l-48 144h-75l-85-256h75l48 144l48-144h74l48 144z"></path></svg>
            </div>
        </div>
        <div class="link-section">
            <a href="admin">
                <div class="link-container">
                    <p id="prodotti-link">PRODOTTI</p>
                </div>
            </a>
            <a href="admin-order">
                <div class="link-container">
                    <p id="ordini-link">ORDINI</p>
                </div>
            </a>
            <a href="admin-user">
                <div class="link-container">
                    <p id="utenti-link">UTENTI</p>
                </div>
            </a>
        </div>
    </nav>
    <script>
        if(document.URL.includes("order")) {
            document.getElementById("ordini-link").style.fontWeight = '700'
        } else if (document.URL.includes("user")) {
            document.getElementById("utenti-link").style.fontWeight = '700'
        } else  {
            document.getElementById("prodotti-link").style.fontWeight = '700'
        }
    </script>
</body>
</html>