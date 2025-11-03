// Seleciona o botão de usuário no header que abre o modal de login
const btnUser = document.querySelector('.btn-user');

// Seleciona o modal de login pelo ID
const loginModal = document.getElementById('loginModal');

// Event listener para abrir/fechar o modal ao clicar no botão de usuário
btnUser.addEventListener('click', () => {
    // Se o modal estiver aberto (display 'block'), fecha; se estiver fechado, abre
    loginModal.style.display = loginModal.style.display === 'block' ? 'none' : 'block';
});

// Event listener para fechar o modal ao clicar fora da caixa de login
window.addEventListener('click', (e) => {
    // Se o alvo do clique for o próprio modal (área de fundo), fecha o modal
    if (e.target === loginModal) {
        loginModal.style.display = 'none';
    }
});

// Seleciona o ícone de olho para mostrar/ocultar a senha
const togglePassword = document.getElementById('togglePassword');

// Seleciona o campo de senha do login
const loginPassword = document.getElementById('loginPassword');

// Event listener para alternar o tipo do input (password/text) ao clicar no ícone
togglePassword.addEventListener('click', () => {
    // Se o input estiver como 'password', muda para 'text' e vice-versa
    const type = loginPassword.getAttribute('type') === 'password' ? 'text' : 'password';
    loginPassword.setAttribute('type', type);

    // Alterna a classe do ícone para mostrar a animação de olho aberto/fechado
    togglePassword.classList.toggle('fa-eye-slash');
});
