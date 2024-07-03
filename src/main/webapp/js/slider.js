let currentSlide = 0;
let autoPlayInterval;

function showSlide(index)            {
    const slides = document.querySelectorAll('.slide')
    if (index >= slides.length) {
        currentSlide = 0;
    } else if (index < 0) {
        currentSlide = slides.length - 1;
    } else {
        currentSlide = index;
    }
    const offset = -currentSlide * 100;
    document.querySelector('.slides').style.transform = `translateX(${offset}%)`
}

function moveSlide(direction) {
    showSlide(currentSlide + direction);
}

function startAutoPlay() {
    autoPlayInterval = setInterval(() => {
        moveSlide(1);
    }, 5000); // Cambia diapositiva ogni  secondi
}

function stopAutoPlay() {
    clearInterval(autoPlayInterval);
}

// Inizializza lo slider e l'auto-play
showSlide(currentSlide);
startAutoPlay();

const slide = document.querySelector('.slider')

slide.onmouseover = function () {
    stopAutoPlay()
}

slide.onmouseout = function () {
    startAutoPlay()
}