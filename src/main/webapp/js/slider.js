let currentSlide = 0;
let autoPlayInterval;

//Funzione per mostrare lo slider e tornare insietro alla prima slide se si supera la terza
function showSlide(index){
    const slides = document.querySelectorAll('.slide');
    if (index >= slides.length) {
        currentSlide = 0;
    } else if (index < 0) {
        currentSlide = slides.length - 1;
    } else {
        currentSlide = index;
    }
    const offset = -currentSlide * 100;
    document.querySelector('.slides').style.transform = `translateX(${offset}%)`;
}

//Movimento dello slider
function moveSlide(direction) {
    showSlide(currentSlide + direction);
}

//Intervallo durata scroll dello slider
function startAutoPlay() {
    autoPlayInterval = setInterval(() => {
        moveSlide(1);
    }, 5000); // Cambia diapositiva ogni 5 secondi
}

//Funzione che stoppa l'autoplay se l'utente ci passa sopra con il mouse
function stopAutoPlay() {
    clearInterval(autoPlayInterval);
}

// Inizializza lo slider e l'auto-play
showSlide(currentSlide);
startAutoPlay();

const slide = document.querySelector('.slider');

//Blocco dell'autoplay dello slider
slide.onmouseover = function () {
    stopAutoPlay()
}

//Sblocco dell'autiplay dello slider
slide.onmouseout = function () {
    startAutoPlay()
}