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
<button class="button-mobile-menu" aria-label="Menu Button" onclick="openMobileNav()" tabindex="0" onkeydown="openMobileNav()">
    <svg aria-label="Menu Icon" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24"><g fill="none"><path d="M24 0v24H0V0zM12.593 23.258l-.011.002l-.071.035l-.02.004l-.014-.004l-.071-.035q-.016-.005-.024.005l-.004.01l-.017.428l.005.02l.01.013l.104.074l.015.004l.012-.004l.104-.074l.012-.016l.004-.017l-.017-.427q-.004-.016-.017-.018m.265-.113l-.013.002l-.185.093l-.01.01l-.003.011l.018.43l.005.012l.008.007l.201.093q.019.005.029-.008l.004-.014l-.034-.614q-.005-.019-.02-.022m-.715.002a.02.02 0 0 0-.027.006l-.006.014l-.034.614q.001.018.017.024l.015-.002l.201-.093l.01-.008l.004-.011l.017-.43l-.003-.012l-.01-.01z"/><path fill="black" d="M20 17.5a1.5 1.5 0 0 1 .144 2.993L20 20.5H4a1.5 1.5 0 0 1-.144-2.993L4 17.5zm0-7a1.5 1.5 0 0 1 0 3H4a1.5 1.5 0 0 1 0-3zm0-7a1.5 1.5 0 0 1 0 3H4a1.5 1.5 0 1 1 0-3z"/></g></svg>
</button>
<button class="background-mobile-nav" onclick="closeMobileNav()" tabindex="0" onkeydown="closeMobileNav()" id="background-mobile-nav"></button>
<div class="mobile-nav" id="mobile-nav">
    <button aria-label="Close" onclick="closeMobileNav()" tabindex="0" onkeydown="closeMobileNav()" class="close-button">
        <svg aria-label="Close SVG" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24"><g fill="none" fill-rule="evenodd"><path d="M24 0v24H0V0zM12.593 23.258l-.011.002l-.071.035l-.02.004l-.014-.004l-.071-.035q-.016-.005-.024.005l-.004.01l-.017.428l.005.02l.01.013l.104.074l.015.004l.012-.004l.104-.074l.012-.016l.004-.017l-.017-.427q-.004-.016-.017-.018m.265-.113l-.013.002l-.185.093l-.01.01l-.003.011l.018.43l.005.012l.008.007l.201.093q.019.005.029-.008l.004-.014l-.034-.614q-.005-.019-.02-.022m-.715.002a.02.02 0 0 0-.027.006l-.006.014l-.034.614q.001.018.017.024l.015-.002l.201-.093l.01-.008l.004-.011l.017-.43l-.003-.012l-.01-.01z"/><path fill="black" d="m12 14.122l5.303 5.303a1.5 1.5 0 0 0 2.122-2.122L14.12 12l5.304-5.303a1.5 1.5 0 1 0-2.122-2.121L12 9.879L6.697 4.576a1.5 1.5 0 1 0-2.122 2.12L9.88 12l-5.304 5.304a1.5 1.5 0 1 0 2.122 2.12z"/></g></svg>
    </button>
    <div class="top-section">
        <img onclick="window.open('./')" src="assets/logo.png" alt="Logo WebComics" title="Visualizza il sito">
    </div>
    <div class="link-section">
        <a href="admin">
            <div class="link-container">
                <p>PRODOTTI</p>
            </div>
        </a>
        <a href="admin-order">
            <div class="link-container">
                <p>ORDINI</p>
            </div>
        </a>
        <a href="admin-user">
            <div class="link-container">
                <p>UTENTI</p>
            </div>
        </a>
    </div>
</div>
<script>
    if(document.URL.includes("order")) {
        document.getElementById("ordini-link").style.fontWeight = '700'
    } else if (document.URL.includes("user")) {
        document.getElementById("utenti-link").style.fontWeight = '700'
    } else  {
        document.getElementById("prodotti-link").style.fontWeight = '700'
    }
</script>
<script src="js/admin/navadmin.js"></script>