import api from './api.js'; // Importa instância do Axios já configurada com baseURL

// ==================== ELEMENTOS DA PESQUISA ====================
const searchBox = document.querySelector('.search-box');        // Container da barra de busca
const searchInput = searchBox.querySelector('input');           // Campo de input
const searchResults = document.querySelector('.search-results'); // Container de resultados

// Expansão visual do input ao focar
searchInput.addEventListener('focus', () => searchBox.classList.add('active'));

// Remove expansão quando sai do foco, apenas se estiver vazio
searchInput.addEventListener('blur', () => {
    if (searchInput.value === '') searchBox.classList.remove('active');
});

// ==================== RENDERIZAR RESULTADOS DE BUSCA ====================
function renderResults(produtos) {
    searchResults.innerHTML = ''; // Limpa resultados anteriores

    // Se não houver produtos, esconde a lista
    if (produtos.length === 0) {
        searchResults.style.display = 'none';
        return;
    }

    // Cria cards para cada produto encontrado
    produtos.forEach(prod => {
        const card = document.createElement('div');
        card.classList.add('result-card');

        // Monta a imagem do produto (ou usa imagem padrão)
        const imagemFinal = (prod.imagemProduto && prod.imagemProduto.length > 0)
            ? `${api.defaults.baseURL}/catalogo/imagem/${prod.imagemProduto[0].idImagemProd}`
            : "img/userPerfil/userNovo.png";

        // Formata o preço no padrão brasileiro
        const precoFormatado = prod.preco.toLocaleString('pt-BR', { 
            minimumFractionDigits: 2, 
            maximumFractionDigits: 2 
        });

        // Estrutura interna do card
        card.innerHTML = `
            <img src="${imagemFinal}" alt="${prod.nome}" class="card-img">
            <strong>${prod.nome}</strong>
            <span>${prod.descricao}</span>
            <span>R$ ${precoFormatado}</span>
        `;

        // Ao clicar, vai para a página do produto
        card.onclick = () => window.location.href = `produto.html?id=${prod.idProduto}`;

        searchResults.appendChild(card);
    });

    searchResults.style.display = 'flex'; // Mostra resultados
}

// ==================== BUSCA VIA API (Axios) ====================
async function buscarProdutos(query) {

    // Se o texto da busca estiver vazio, esconde resultados
    if (!query) {
        searchResults.style.display = 'none';
        return;
    }

    try {
        // Faz requisição GET /catalogo/pesquisa?q=...&pageSize=7
        const { data } = await api.get('catalogo/pesquisa', { 
            params: { q: query, pageSize: 7 } 
        });

        // Renderiza resultados recebidos
        renderResults(data.content);

    } catch (err) {
        console.error('Erro na busca:', err);
        searchResults.style.display = 'none';
    }
}

// Chama a busca sempre que o usuário digita
searchInput.addEventListener('input', () =>
    buscarProdutos(searchInput.value.trim())
);

// ==================== LOGIN & PERFIL ====================

// Verifica expiração do token salvo
const expiration = localStorage.getItem('expiration');
if (expiration && Date.now() > expiration) {
    // Se expirou, limpa dados do login
    localStorage.removeItem('isLoggedIn');
    localStorage.removeItem('userImage');
    localStorage.removeItem('token');
    localStorage.removeItem('expiration');
}

const isLoggedIn = localStorage.getItem('isLoggedIn');
const userImage = localStorage.getItem('userImage');
const btnUser = document.querySelector('.btn-user');

// Se logado, mostra avatar e redireciona para perfil ao clicar
if (isLoggedIn && userImage) {
    btnUser.innerHTML = `<img src="${userImage}" alt="Perfil" class="user-avatar">`;
    btnUser.onclick = () => window.location.href = 'perfil.html';

} else {
    // Se não estiver logado, mostra ícone normal
    btnUser.innerHTML = `<i class="fa-solid fa-user"></i>`;
}

// ==================== CARRINHO ====================
const btnCart = document.querySelector('.btn-cart');
btnCart.onclick = () => window.location.href = 'carrinho.html';

// ==================== ATUALIZAR BADGE DO CARRINHO ====================
async function atualizarBadgeCarrinho() {
    const token = localStorage.getItem('token');
    const badge = document.getElementById('cartBadge');

    // Se não tiver token, oculta o badge
    if (!token) {
        badge.style.display = 'none';
        return;
    }

    try {
        // Consulta quantidade de itens no carrinho do usuário
        const { data } = await api.get('/reserva/carrinho', {
            headers: { Authorization: `Bearer ${token}` }
        });

        const total = data.totalElements;

        // Exibe o número no badge se houver itens
        if (total > 0) {
            badge.textContent = total;
            badge.style.display = 'flex';
        } else {
            badge.style.display = 'none';
        }

    } catch (err) {
        console.error("Erro ao atualizar ícone do carrinho:", err);
        badge.style.display = 'none';
    }
}

// Executado automaticamente ao carregar o site
atualizarBadgeCarrinho();
