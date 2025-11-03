// Seleciona o container da caixa de busca
const searchBox = document.querySelector('.search-box');

// Seleciona o input dentro da caixa de busca
const searchInput = document.querySelector('.search-box input');

// Event listener ao focar no input (quando o usuário clica ou tabula para ele)
searchInput.addEventListener('focus', () => {
    // Adiciona a classe 'active' ao container da busca, expandindo-o visualmente
    searchBox.classList.add('active');
});

// Event listener ao perder o foco (quando o usuário clica fora do input)
searchInput.addEventListener('blur', () => {
    // Remove a classe 'active' apenas se o input estiver vazio, mantendo a busca expandida se houver texto
    if (searchInput.value === '') {
        searchBox.classList.remove('active');
    }
});
