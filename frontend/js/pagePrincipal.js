// Seleciona todos os slides dentro do carrossel
const slides = document.querySelectorAll(".slide");

// Seleciona o container que envolve todos os slides (para aplicar a translação)
const slidesWrapper = document.querySelector(".slides");

// Seleciona todos os indicadores (bolinhas) do carrossel
const dots = document.querySelectorAll(".dot");

// Seleciona os botões de navegação anterior e próximo
const prev = document.querySelector(".prev");
const next = document.querySelector(".next");

// Índice do slide atual
let index = 0;

// Configura autoplay: troca de slides a cada 15 segundos
let interval = setInterval(nextSlide, 15000);

// Função para mostrar o slide de índice i
function showSlide(i) {
    // Move o container de slides horizontalmente
    slidesWrapper.style.transform = `translateX(-${i * 100}%)`;

    // Atualiza o indicador ativo
    dots.forEach((dot, j) => dot.classList.toggle("active", j === i));

    // Atualiza o índice atual
    index = i;
}

// Função para ir para o próximo slide
function nextSlide() {
    showSlide((index + 1) % slides.length); // volta para o primeiro slide ao chegar no final
}

// Função para ir para o slide anterior
function prevSlide() {
    showSlide((index - 1 + slides.length) % slides.length); // volta para o último slide se estiver no primeiro
}

// Event listeners para os botões de navegação
next.addEventListener("click", () => { 
    nextSlide(); 
    resetInterval(); // reinicia o autoplay ao clicar
});
prev.addEventListener("click", () => { 
    prevSlide(); 
    resetInterval(); // reinicia o autoplay ao clicar
});

// Event listeners para os indicadores (bolinhas)
dots.forEach((dot, i) => {
    dot.addEventListener("click", () => { 
        showSlide(i); 
        resetInterval(); // reinicia o autoplay ao clicar
    });
});

// Pausa o autoplay quando o mouse passa sobre o carrossel
document.querySelector(".carousel").addEventListener("mouseenter", () => clearInterval(interval));

// Retoma o autoplay quando o mouse sai do carrossel
document.querySelector(".carousel").addEventListener("mouseleave", () => interval = setInterval(nextSlide, 15000));

// Função para reiniciar o autoplay (usada ao clicar em botões ou indicadores)
function resetInterval() {
    clearInterval(interval);
    interval = setInterval(nextSlide, 15000);
}

// Inicializa o carrossel mostrando o primeiro slide
showSlide(0);
