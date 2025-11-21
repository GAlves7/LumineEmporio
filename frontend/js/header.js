// Busca expandida
const searchBox = document.querySelector('.search-box');
const searchInput = searchBox.querySelector('input');

searchInput.addEventListener('focus', () => searchBox.classList.add('active'));
searchInput.addEventListener('blur', () => {
    if (searchInput.value === '') searchBox.classList.remove('active');
});

// CHECA EXPIRAÇÃO DO LOGIN
const expiration = localStorage.getItem('expiration');
if (expiration && Date.now() > expiration) {
    // Expirou: desloga
    localStorage.removeItem('isLoggedIn');
    localStorage.removeItem('userImage');
    localStorage.removeItem('token');
    localStorage.removeItem('expiration');
}

// Atualiza botão de usuário se estiver logado
const isLoggedIn = localStorage.getItem('isLoggedIn');
const userImage = localStorage.getItem('userImage');
const btnUser = document.querySelector('.btn-user');

if (isLoggedIn && userImage) {
    btnUser.innerHTML = `<img src="${userImage}" alt="Perfil" class="user-avatar">`;
    btnUser.onclick = () => window.location.href = 'perfil.html';
} else {
    btnUser.innerHTML = `<i class="fa-solid fa-user"></i>`;
}
