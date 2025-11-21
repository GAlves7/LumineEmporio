// Botão de logout
const btnSair = document.querySelector('.perfil-botoes .sair');

btnSair.addEventListener('click', () => {
    // Limpa todos os dados de login
    localStorage.removeItem('isLoggedIn');
    localStorage.removeItem('userImage');
    localStorage.removeItem('token');
    localStorage.removeItem('loginExpiracao');

    // Redireciona para a página inicial/catalogo
    window.location.href = 'index.html';
});

// Lógica futura:
// Quando o backend estiver pronto, aqui vai buscar os dados do usuário (nome, email, telefone, foto, etc.)
// e atualizar os elementos do DOM para exibir as informações corretas do perfil.
