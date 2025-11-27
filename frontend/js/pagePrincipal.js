/* IMPORTA AXIOS CONFIGURADO */
// Importa o arquivo api.js, que já vem configurado com o baseURL da sua API.
// Isso facilita fazer requisições GET, POST, PUT, etc.
import api from "./api.js";


/* ================== CARROSSEL PRINCIPAL ================== */
// Seleciona os elementos do carrossel para controlar as imagens

const slidesWrapper = document.querySelector(".slides"); 
// A div que envolve todos os slides (usada para mover as imagens)

const slides = document.querySelectorAll(".slide"); 
// Cada slide individual

const dots = document.querySelectorAll(".dot"); 
// As bolinhas abaixo do banner que indicam qual slide está ativo

const prev = document.querySelector(".prev"); 
// Botão de seta para voltar

const next = document.querySelector(".next"); 
// Botão de seta para avançar

let index = 0;   // Armazena o slide atual
let interval = null; // Usado para controlar o tempo automático


// Mostra o slide baseado no índice recebido
function showSlide(i) {
    index = i; // Atualiza o índice atual

    // Move os slides para o lado usando CSS (transform)
    slidesWrapper.style.transform = `translateX(-${i * 100}%)`;

    // Atualiza qual bolinha está ativa
    dots.forEach((dot, j) => dot.classList.toggle("active", j === i));
}

// Vai para o próximo slide
function nextSlide() { showSlide((index + 1) % slides.length); }

// Vai para o slide anterior
function prevSlide() { showSlide((index - 1 + slides.length) % slides.length); }

// Inicia o carrossel automático (a cada 15 segundos troca o slide)
function startInterval() {
    interval = setInterval(nextSlide, 15000);
}

// Reinicia o tempo quando o usuário clica nas setas ou bolinhas
function resetInterval() {
    clearInterval(interval);
    startInterval();
}

// Eventos das setas de navegação
next.addEventListener("click", () => { nextSlide(); resetInterval(); });
prev.addEventListener("click", () => { prevSlide(); resetInterval(); });

// Quando o usuário clica em uma bolinha, muda o slide
dots.forEach((dot, i) => dot.addEventListener("click", () => { showSlide(i); resetInterval(); }));

// Pausa o carrossel quando o mouse está em cima
const carousel = document.querySelector(".carousel");
carousel.addEventListener("mouseenter", () => clearInterval(interval));
// Retoma o carrossel quando o mouse sai
carousel.addEventListener("mouseleave", startInterval);

// Inicia o estado inicial
showSlide(0);
startInterval();



/* ================== GERAR CARDS ================== */
// Função que monta o card HTML para cada produto da API
function gerarCard(produto) {

    // Se o produto tiver imagem, usa ela. Senão usa uma imagem padrão
    const imagemFinal = (produto.imagemProduto && produto.imagemProduto.length > 0)
        ? `${api.defaults.baseURL}/catalogo/imagem/${produto.imagemProduto[0].idImagemProd}`
        : "img/userPerfil/userNovo.png";

    // Formata o preço, deixando 2 casas decimais e formato brasileiro
    const precoFormatado = produto.preco.toLocaleString(
        'pt-BR', 
        { minimumFractionDigits: 2, maximumFractionDigits: 2 }
    );

    // Retorna o HTML do card do produto
    return `
        <div class="card-produto">
            <!-- Quando clicado, abre a página do produto -->
            <img src="${imagemFinal}" alt="${produto.nome}" onclick="window.location.href='produto.html?id=${produto.idProduto}'">
            
            <h3>${produto.nome}</h3>
            <p class="preco">R$ ${precoFormatado}</p>

            <div class="card-actions">
                <!-- Botão de adicionar ao carrinho -->
                <button class="btn-card btn-add" data-id="${produto.idProduto}">
                    <i class="fa-solid fa-cart-plus"></i>
                </button>
            </div>
        </div>
    `;
}



/* ================== CARREGAR CATALOGO DO BACKEND ================== */
// Busca produtos por categoria e adiciona os cards na tela
async function carregarProdutosPorCategoria(id, destino) {

    try {
        // Faz requisição para API usando o ID da categoria
        const produtos = (await api.get(`/catalogo/categoria/${id}`)).data;

        // Coloca os cards na div correta (ex: cat-feminina)
        document.getElementById(destino).innerHTML =
            produtos.map(gerarCard).join("");

        // Seleciona todos os botões "Adicionar ao carrinho" da categoria
        const botoes = document.querySelectorAll(`#${destino} .btn-add`);

        // Adiciona evento de clique para cada botão
        botoes.forEach(btn => {
            btn.addEventListener("click", async (e) => {

                e.stopPropagation(); 
                // Impede que clicar no botão abra a página do produto

                const token = localStorage.getItem("token");

                // Se o usuário não está logado, impede adicionar ao carrinho
                if (!token) {
                    alert("Você precisa estar logado para adicionar produtos ao carrinho.");
                    return;
                }

                const idProduto = btn.getAttribute("data-id");

                try {
                    // Busca o produto completo para pegar variações
                    const produtoData = (await api.get(
                        `/catalogo/produto/${idProduto}`,
                        { headers: { Authorization: `Bearer ${token}` } }
                    )).data;

                    // Se tiver variação usa a primeira, se não usa o produto normal
                    const variacao =
                        produtoData.produtoVariacao?.[0] || {
                            idProdutoVar: produtoData.idProduto,
                            preco: produtoData.preco
                        };

                    // Prepara os dados para enviar ao backend
                    const formData = new FormData();
                    formData.append("idProdutoVar", variacao.idProdutoVar);
                    formData.append("quantidade", 1);

                    // Envia requisição para adicionar ao carrinho
                    await api.put("/reserva/carrinho-add", formData, {
                        headers: { Authorization: `Bearer ${token}` }
                    });

                    alert("Produto adicionado ao carrinho!");
                    window.location.reload(); // Atualiza ícone do carrinho

                } catch (erro) {
                    console.error("Erro ao adicionar ao carrinho:", erro);
                    alert("Erro ao adicionar ao carrinho");
                }
            });
        });

    } catch (erro) {
        console.error("Erro ao carregar categoria", id, erro);

        // Mostra erro na tela caso falhe
        document.getElementById(destino).innerHTML =
            `<p style="color:red;">Erro ao carregar produtos.</p>`;
    }
}



/* ================== CARREGAR TODO CATALOGO ================== */
// Chama as funções para carregar todas as categorias do site
function carregarCatalogo() {
    carregarProdutosPorCategoria(2, "cat-feminina");  // ID 2 = Moda Feminina
    carregarProdutosPorCategoria(1, "cat-cosmeticos"); // ID 1 = Cosméticos
}



/* ================== INICIALIZAÇÃO ================== */
// Quando o arquivo é carregado, inicia o catálogo
carregarCatalogo();
