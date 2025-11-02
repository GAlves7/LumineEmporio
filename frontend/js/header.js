// Busca expandida
const searchBox = document.querySelector('.search-box');
const searchInput = document.querySelector('.search-box input');

searchInput.addEventListener('focus', () => {
    searchBox.classList.add('active');
});

searchInput.addEventListener('blur', () => {
    if (searchInput.value === '') {
        searchBox.classList.remove('active');
    }
});
