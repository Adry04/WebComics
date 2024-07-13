function openMobileNav() {
    console.log("CIAO")
    document.body.style.overflow = 'hidden';
    document.getElementById("background-mobile-nav").classList.add("display-background-mobile-nav")
    document.getElementById("mobile-nav").classList.add("display-mobile-nav")
}

function closeMobileNav() {
    document.body.style.overflow = 'auto';
    document.getElementById("background-mobile-nav").classList.remove("display-background-mobile-nav")
    document.getElementById("mobile-nav").classList.remove("display-mobile-nav")
}