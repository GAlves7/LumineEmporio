/* ================== CARROSSEL PRINCIPAL ================== */
const slides = document.querySelectorAll(".slide");
const slidesWrapper = document.querySelector(".slides");
const dots = document.querySelectorAll(".dot");
const prev = document.querySelector(".prev");
const next = document.querySelector(".next");

let index = 0;
let interval = setInterval(nextSlide, 15000);

function showSlide(i) {
    slidesWrapper.style.transform = `translateX(-${i * 100}%)`;
    dots.forEach((dot, j) => dot.classList.toggle("active", j === i));
    index = i;
}

function nextSlide() {
    showSlide((index + 1) % slides.length);
}

function prevSlide() {
    showSlide((index - 1 + slides.length) % slides.length);
}

next.addEventListener("click", () => { 
    nextSlide(); 
    resetInterval();
});
prev.addEventListener("click", () => { 
    prevSlide(); 
    resetInterval();
});

dots.forEach((dot, i) => {
    dot.addEventListener("click", () => { 
        showSlide(i); 
        resetInterval();
    });
});

document.querySelector(".carousel").addEventListener("mouseenter", () => clearInterval(interval));
document.querySelector(".carousel").addEventListener("mouseleave", () => interval = setInterval(nextSlide, 15000));

function resetInterval() {
    clearInterval(interval);
    interval = setInterval(nextSlide, 15000);
}

showSlide(0);