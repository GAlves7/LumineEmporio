// Seleciona os elementos
const searchBox = document.querySelector('.search-box');
const searchInput = document.querySelector('.search-box input');

// Expande ao clicar no input
searchInput.addEventListener('focus', () => {
    searchBox.classList.add('active');
});

// Retrai ao sair do foco (clicar fora)
searchInput.addEventListener('blur', () => {
    if (searchInput.value === '') {
        searchBox.classList.remove('active');
    }
});
