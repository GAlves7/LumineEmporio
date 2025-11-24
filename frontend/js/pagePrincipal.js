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

    // Formata o preço
    const precoFormatado = produto.preco.toLocaleString('pt-BR', { minimumFractionDigits: 2, maximumFractionDigits: 2 });

    return `
        <div class="card-produto">
            <img src="${imagemFinal}" alt="${produto.nome}" onclick="window.location.href='produto.html?id=${produto.idProduto}'">
            <h3>${produto.nome}</h3>
            <p class="preco">R$ ${precoFormatado}</p>
            <div class="card-actions">
                <button class="btn-card btn-add" data-id="${produto.idProduto}">
                    <i class="fa-solid fa-cart-plus"></i>
                </button>
            </div>
        </div>
    `;
}

/* ================== CARREGAR CATALOGO DO BACKEND ================== */
async function carregarProdutosPorCategoria(id, destino) {
    try {
        const produtos = (await api.get(`/catalogo/categoria/${id}`)).data;
        document.getElementById(destino).innerHTML = produtos.map(gerarCard).join("");

        // Adicionar evento aos botões de adicionar ao carrinho
        const botoes = document.querySelectorAll(`#${destino} .btn-add`);
        botoes.forEach(btn => {
            btn.addEventListener("click", async (e) => {
                e.stopPropagation(); // evita que o click abra a página do produto

                const token = localStorage.getItem("token");
                if (!token) {
                    alert("Você precisa estar logado para adicionar produtos ao carrinho.");
                    return;
                }

                const idProduto = btn.getAttribute("data-id");

                try {
                    // Pega o produto inteiro para pegar a variação
                    const produtoData = (await api.get(`/catalogo/produto/${idProduto}`, {
                        headers: { Authorization: `Bearer ${token}` }
                    })).data;

                    // Pega a primeira variação ou produto padrão
                    const variacao = produtoData.produtoVariacao?.[0] || { idProdutoVar: produtoData.idProduto, preco: produtoData.preco };

                    const formData = new FormData();
                    formData.append("idProdutoVar", variacao.idProdutoVar);
                    formData.append("quantidade", 1);

                    await api.put("/reserva/carrinho-add", formData, {
                        headers: { Authorization: `Bearer ${token}` }
                    });

                    alert("Produto adicionado ao carrinho!");
                    window.location.reload(); // recarrega a página para atualizar o ícone

                    // Opcional: atualizar carrinho dinamicamente chamando listarCarrinho()
                    // listarCarrinho(); // se carrinho.js estiver importado

                } catch (erro) {
                    console.error("Erro ao adicionar ao carrinho:", erro);
                    alert("Erro ao adicionar ao carrinho");
                }
            });
        });

    } catch (erro) {
        console.error("Erro ao carregar categoria", id, erro);
        document.getElementById(destino).innerHTML =
            `<p style="color:red;">Erro ao carregar produtos.</p>`;
    }
}

/* ================== CARREGAR TODO CATALOGO ================== */
function carregarCatalogo() {
    carregarProdutosPorCategoria(2, "cat-feminina"); // Moda Feminina
    carregarProdutosPorCategoria(1, "cat-cosmeticos"); // Cosméticos
}

/* ================== INICIALIZAÇÃO ================== */
carregarCatalogo();
