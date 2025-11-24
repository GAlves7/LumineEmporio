import api from "./api.js";

/* PEGAR ID DA URL */
const params = new URLSearchParams(window.location.search);
const id = params.get("id");

if (!id) {
    alert("Produto não encontrado");
}

/* ELEMENTOS */
const imgEl = document.getElementById("produto-img");
const nomeEl = document.getElementById("produto-nome");
const descEl = document.getElementById("produto-descricao");
const precoEl = document.getElementById("produto-preco");
const tamanhoEl = document.getElementById("produto-tamanho");
const corEl = document.getElementById("produto-cor");
const estoqueEl = document.getElementById("produto-estoque");
const btnComprar = document.getElementById("btn-comprar");

let produtoData = null;
let variacaoSelecionada = null;

/* CARREGAR PRODUTO */
async function carregarProduto() {
    try {
        const produto = (await api.get(`/catalogo/produto/${id}`)).data;
        produtoData = produto;

        // IMAGEM
        const imagem = produto.imagemProduto?.length > 0
            ? `${api.defaults.baseURL}/catalogo/imagem/${produto.imagemProduto[0].idImagemProd}`
            : "img/userPerfil/userNovo.png";
        imgEl.src = imagem;

        // NOME / DESCRIÇÃO / PREÇO
        nomeEl.innerText = produto.nome;
        descEl.innerText = produto.descricao;
        precoEl.innerText = `R$ ${produto.preco.toLocaleString('pt-BR', { minimumFractionDigits: 2 })}`;

        // VARIAÇÕES
        if(produto.produtoVariacao?.length > 0){
            tamanhoEl.innerHTML = "";
            corEl.innerHTML = "";

            produto.produtoVariacao.forEach((v, index) => {
                // Tamanhos
                const optT = document.createElement("option");
                optT.value = index;
                optT.text = v.tamanho;
                tamanhoEl.appendChild(optT);

                // Cores (pegar só a primeira cor da variação)
                const optC = document.createElement("option");
                optC.value = index;
                optC.text = v.cores?.map(c=>c.nome).join(", ");
                corEl.appendChild(optC);
            });

            variacaoSelecionada = produto.produtoVariacao[0];
            atualizarEstoque();
        }

    } catch (erro) {
        console.error("Erro ao carregar produto", erro);
        alert("Erro ao carregar produto");
    }
}

/* ATUALIZAR ESTOQUE AO MUDAR VARIAÇÃO */
function atualizarEstoque() {
    if(!variacaoSelecionada) return;
    estoqueEl.innerText = variacaoSelecionada.estoque;
    precoEl.innerText = `R$ ${variacaoSelecionada.preco.toLocaleString('pt-BR', { minimumFractionDigits: 2 })}`;
}

/* EVENTOS DE SELEÇÃO */
tamanhoEl.addEventListener("change", e => {
    const index = parseInt(e.target.value);
    variacaoSelecionada = produtoData.produtoVariacao[index];
    atualizarEstoque();
});

corEl.addEventListener("change", e => {
    const index = parseInt(e.target.value);
    variacaoSelecionada = produtoData.produtoVariacao[index];
    atualizarEstoque();
});

/* BOTÃO COMPRAR COM CHECK DE LOGIN E ABRIR MODAL */
btnComprar.addEventListener("click", async () => {
    const token = localStorage.getItem("token");
    if (!token) {
        alert("Você precisa estar logado para adicionar produtos ao carrinho.");
        return;
    }

    if (!variacaoSelecionada) {
        alert("Selecione uma variação do produto");
        return;
    }

    try {
        const formData = new FormData();
        formData.append("idProdutoVar", variacaoSelecionada.idProdutoVar);
        formData.append("quantidade", 1);

        await api.put("/reserva/carrinho-add", formData, {
            headers: { Authorization: `Bearer ${token}` }
        });

        alert("Produto adicionado ao carrinho!");
        // Opcional: atualizar carrinho dinamicamente se quiser
        // listarCarrinho();

    } catch (erro) {
        console.error("Erro ao adicionar ao carrinho:", erro);
        alert("Erro ao adicionar ao carrinho");
    }
});

carregarProduto();
