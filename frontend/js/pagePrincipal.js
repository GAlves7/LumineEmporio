/* IMPORTA AXIOS CONFIGURADO */
import api from "./api.js";

/* ================== CARROSSEL PRINCIPAL ================== */
const slidesWrapper = document.querySelector(".slides");
const slides = document.querySelectorAll(".slide");
const dots = document.querySelectorAll(".dot");
const prev = document.querySelector(".prev");
const next = document.querySelector(".next");

let index = 0;
let interval = null;

function showSlide(i) {
    index = i;
    slidesWrapper.style.transform = `translateX(-${i * 100}%)`;
    dots.forEach((dot, j) => dot.classList.toggle("active", j === i));
}

function nextSlide() { showSlide((index + 1) % slides.length); }
function prevSlide() { showSlide((index - 1 + slides.length) % slides.length); }

function startInterval() {
    interval = setInterval(nextSlide, 15000);
}

function resetInterval() {
    clearInterval(interval);
    startInterval();
}

// Eventos de navegação
next.addEventListener("click", () => { nextSlide(); resetInterval(); });
prev.addEventListener("click", () => { prevSlide(); resetInterval(); });
dots.forEach((dot, i) => dot.addEventListener("click", () => { showSlide(i); resetInterval(); }));

// Pausa ao passar o mouse
const carousel = document.querySelector(".carousel");
carousel.addEventListener("mouseenter", () => clearInterval(interval));
carousel.addEventListener("mouseleave", startInterval);

showSlide(0);
startInterval();

/* ================== GERAR CARDS ================== */
function gerarCard(produto) {
    const imagemFinal = (produto.imagemProduto && produto.imagemProduto.length > 0)
        ? `${api.defaults.baseURL}/catalogo/imagem/${produto.imagemProduto[0].idImagemProd}`
        : "img/userPerfil/userNovo.png";

    // Formata o preço para R$ 50,00
    const precoFormatado = produto.preco.toLocaleString('pt-BR', { minimumFractionDigits: 2, maximumFractionDigits: 2 });

    return `
        <div class="card-produto">
            <img src="${imagemFinal}" alt="${produto.nome}">
            <h3>${produto.nome}</h3>
            <p class="preco">R$ ${precoFormatado}</p>
            <div class="card-actions">
                <button class="btn-card btn-add">
                    <i class="fa-solid fa-cart-plus"></i>
                </button>
                <button class="btn-card btn-reservar">Reservar</button>
            </div>
        </div>
    `;
}

/* ================== CARREGAR CATALOGO DO BACKEND ================== */
async function carregarProdutosPorCategoria(id, destino) {
    try {
        const produtos = (await api.get(`/catalogo/categoria/${id}`)).data;
        document.getElementById(destino).innerHTML = produtos.map(gerarCard).join("");
    } catch (erro) {
        console.error("Erro ao carregar categoria", id, erro);
        document.getElementById(destino).innerHTML =
            `<p style="color:red;">Erro ao carregar produtos.</p>`;
    }
}

function carregarCatalogo() {
    carregarProdutosPorCategoria(2, "cat-feminina"); // Moda Feminina
    carregarProdutosPorCategoria(1, "cat-cosmeticos"); // Cosméticos
}

carregarCatalogo();
