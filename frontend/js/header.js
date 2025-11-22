import api from './api.js'; // Axios já configurado com baseURL

// Elementos da pesquisa
const searchBox = document.querySelector('.search-box');
const searchInput = searchBox.querySelector('input');
const searchForm = document.querySelector('.search-form');
const searchResults = document.querySelector('.search-results');

// Expansão do input
searchInput.addEventListener('focus', () => searchBox.classList.add('active'));
searchInput.addEventListener('blur', () => {
    if (searchInput.value === '') searchBox.classList.remove('active');
});

// Função para renderizar resultados
function renderResults(produtos, query) {
    searchResults.innerHTML = '';
    if (produtos.length === 0) {
        searchResults.style.display = 'none';
        return;
    }

    // Card "Ver todos os resultados"
    const verTodos = document.createElement('div');
    verTodos.classList.add('result-card', 'ver-todos');
    verTodos.textContent = 'Ver todos os resultados';
    verTodos.onclick = () => window.location.href = `pesquisa.html?q=${encodeURIComponent(query)}`;
    searchResults.appendChild(verTodos);

    // Cards dos produtos
    produtos.forEach(prod => {
        const card = document.createElement('div');
        card.classList.add('result-card');
        card.innerHTML = `<strong>${prod.nome}</strong><span>${prod.descricao}</span><span>R$ ${prod.preco.toLocaleString('pt-BR', { minimumFractionDigits: 2, maximumFractionDigits: 2 })}</span>`;
        card.onclick = () => window.location.href = `pesquisa.html?q=${encodeURIComponent(query)}`;
        searchResults.appendChild(card);
    });

    searchResults.style.display = 'flex';
}

// Função para buscar produtos via Axios
async function buscarProdutos(query) {
    if (!query) { 
        searchResults.style.display = 'none'; 
        return; 
    }
    try {
        const { data } = await api.get('catalogo/pesquisa', { params: { q: query, pageSize: 3 } });
        renderResults(data.content, query);
    } catch (err) {
        console.error('Erro na busca:', err);
        searchResults.style.display = 'none';
    }
}

// Evento input para busca dinâmica
searchInput.addEventListener('input', () => buscarProdutos(searchInput.value.trim()));

// Evento submit (Enter ou lupa) -> vai para página completa
searchForm.addEventListener('submit', (e) => {
    e.preventDefault();
    const query = searchInput.value.trim();
    if (query) window.location.href = `pesquisa.html?q=${encodeURIComponent(query)}`;
});

// ================= LOGIN E CARRINHO =================
const expiration = localStorage.getItem('expiration');
if (expiration && Date.now() > expiration) {
    localStorage.removeItem('isLoggedIn');
    localStorage.removeItem('userImage');
    localStorage.removeItem('token');
    localStorage.removeItem('expiration');
}

const isLoggedIn = localStorage.getItem('isLoggedIn');
const userImage = localStorage.getItem('userImage');
const btnUser = document.querySelector('.btn-user');

if (isLoggedIn && userImage) {
    btnUser.innerHTML = `<img src="${userImage}" alt="Perfil" class="user-avatar">`;
    btnUser.onclick = () => window.location.href = 'perfil.html';
} else {
    btnUser.innerHTML = `<i class="fa-solid fa-user"></i>`;
}

const btnCart = document.querySelector('.btn-cart');
btnCart.onclick = () => window.location.href = 'carrinho.html';
