//Apertura della Navbar per cellulare
function openMobileNav() {
    document.body.style.overflow = 'hidden';
    document.getElementById("background-mobile-nav").classList.add("display-background-mobile-nav")
    document.getElementById("mobile-nav").classList.add("display-mobile-nav")
}

//Chiusura della Navbar per cellulare
function closeMobileNav() {
    document.body.style.overflow = 'auto';
    document.getElementById("background-mobile-nav").classList.remove("display-background-mobile-nav")
    document.getElementById("mobile-nav").classList.remove("display-mobile-nav")
}