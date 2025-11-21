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

function gerarCard(produto) {
    return `
        <div class="card-produto">
            <img src="${produto.imagem || ''}" alt="${produto.nome || ''}">
            <h3>${produto.nome || ''}</h3>
            <p class="preco">${produto.preco ? 'R$ ' + produto.preco : ''}</p>

            <div class="card-actions">
                <button class="btn-card btn-add">
                    <i class="fa-solid fa-cart-plus"></i>
                </button>

                <button class="btn-card btn-reservar">Reservar</button>
            </div>
        </div>
    `;
}

// Mock por enquanto — será substituído pelo backend
const produtosFeminina = [{}, {}, {}, {}];
const produtosCosmeticos = [{}, {}, {}, {}];

function carregarCatalogo() {
    document.getElementById("cat-feminina").innerHTML =
        produtosFeminina.map(p => gerarCard(p)).join("");

    document.getElementById("cat-cosmeticos").innerHTML =
        produtosCosmeticos.map(p => gerarCard(p)).join("");
}

carregarCatalogo();
