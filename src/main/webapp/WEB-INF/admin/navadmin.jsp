<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<nav>
    <div class="top-section">
        <img onclick="window.open('./')" src="assets/logo.png" alt="Logo WebComics" title="Visualizza il sito">
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