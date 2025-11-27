import api from "./api.js"; // Importa a instância configurada do Axios para fazer requisições

/* PEGAR ID DA URL */
const params = new URLSearchParams(window.location.search); // Lê parâmetros da URL
const id = params.get("id"); // Pega o ID do produto

if (!id) {
    alert("Produto não encontrado"); // Caso não tenha ID, exibe erro
}

/* ELEMENTOS */
// Pega todos os elementos HTML que vão ser manipulados
const imgEl = document.getElementById("produto-img");
const nomeEl = document.getElementById("produto-nome");
const descEl = document.getElementById("produto-descricao");
const precoEl = document.getElementById("produto-preco");
const tamanhoEl = document.getElementById("produto-tamanho");
const corEl = document.getElementById("produto-cor");
const estoqueEl = document.getElementById("produto-estoque");
const btnComprar = document.getElementById("btn-comprar");

let produtoData = null;          // Guarda o produto completo da API
let variacaoSelecionada = null;  // Guarda a variação atualmente escolhida

/* CARREGAR PRODUTO */
async function carregarProduto() {
    try {
        // Busca o produto pelo ID
        const produto = (await api.get(`/catalogo/produto/${id}`)).data;
        produtoData = produto;

        // --- IMAGEM DO PRODUTO ---
        const imagem = produto.imagemProduto?.length > 0
            ? `${api.defaults.baseURL}/catalogo/imagem/${produto.imagemProduto[0].idImagemProd}`
            : "img/userPerfil/userNovo.png"; // fallback
        imgEl.src = imagem;

        // --- NOME / DESCRIÇÃO / PREÇO ---
        nomeEl.innerText = produto.nome;
        descEl.innerText = produto.descricao;
        precoEl.innerText = `R$ ${produto.preco.toLocaleString('pt-BR', { minimumFractionDigits: 2 })}`;

        // --- VARIAÇÕES DO PRODUTO (cor, tamanho, preço, estoque) ---
        if(produto.produtoVariacao?.length > 0){
            // Limpa selects antes de preencher
            tamanhoEl.innerHTML = "";
            corEl.innerHTML = "";

            // Para cada variação, cria opções nos selects
            produto.produtoVariacao.forEach((v, index) => {

                // Cria opção de tamanho
                const optT = document.createElement("option");
                optT.value = index; // guarda o índice da variação
                optT.text = v.tamanho;
                tamanhoEl.appendChild(optT);

                // Cria opção de cor (pega todas as cores da variação)
                const optC = document.createElement("option");
                optC.value = index;
                optC.text = v.cores?.map(c=>c.nome).join(", ");
                corEl.appendChild(optC);
            });

            // Deixa a primeira variação como selecionada por padrão
            variacaoSelecionada = produto.produtoVariacao[0];

            // Atualiza estoque e preço conforme essa variação
            atualizarEstoque();
        }

    } catch (erro) {
        console.error("Erro ao carregar produto", erro);
        alert("Erro ao carregar produto"); // fallback de erro
    }
}

/* ATUALIZAR ESTOQUE AO MUDAR VARIAÇÃO */
function atualizarEstoque() {
    if(!variacaoSelecionada) return;

    // Mostra o estoque da variação escolhida
    estoqueEl.innerText = variacaoSelecionada.estoque;

    // Atualiza o preço dessa variação
    precoEl.innerText = `R$ ${variacaoSelecionada.preco.toLocaleString('pt-BR', { minimumFractionDigits: 2 })}`;
}

/* EVENTOS DE SELEÇÃO */

// Quando muda o tamanho, troca a variação
tamanhoEl.addEventListener("change", e => {
    const index = parseInt(e.target.value);
    variacaoSelecionada = produtoData.produtoVariacao[index];
    atualizarEstoque(); // Atualiza estoque e preço
});

// Quando muda a cor, também troca a variação
corEl.addEventListener("change", e => {
    const index = parseInt(e.target.value);
    variacaoSelecionada = produtoData.produtoVariacao[index];
    atualizarEstoque();
});

/* BOTÃO COMPRAR COM CHECK DE LOGIN E ABRIR MODAL */
btnComprar.addEventListener("click", async () => {
    const token = localStorage.getItem("token");

    // Verifica login
    if (!token) {
        alert("Você precisa estar logado para adicionar produtos ao carrinho.");
        return;
    }

    // Verifica se uma variação foi escolhida
    if (!variacaoSelecionada) {
        alert("Selecione uma variação do produto");
        return;
    }

    try {
        // Prepara os dados a enviar (id da variação e quantidade)
        const formData = new FormData();
        formData.append("idProdutoVar", variacaoSelecionada.idProdutoVar);
        formData.append("quantidade", 1);

        // Envia requisição para a API
        await api.put("/reserva/carrinho-add", formData, {
            headers: { Authorization: `Bearer ${token}` }
        });

        alert("Produto adicionado ao carrinho!");

        // Recarrega a página para atualizar o ícone do carrinho (no header)
        window.location.reload();

    } catch (erro) {
        console.error("Erro ao adicionar ao carrinho:", erro);
        alert("Erro ao adicionar ao carrinho");
    }
});

// Inicia carregamento do produto ao entrar na página
carregarProduto();
