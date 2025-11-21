import api from './api.js';

// Seleciona elementos do modal
const btnUser = document.querySelector('.btn-user');
const loginModal = document.getElementById('loginModal');
const togglePassword = document.getElementById('togglePassword');
const loginPassword = document.getElementById('loginPassword');
const btnLogin = document.getElementById('btnLogin');
const keepLogged = document.getElementById('keepLogged'); // checkbox "manter logado"

// Abrir/fechar modal ao clicar no ícone de usuário
btnUser.addEventListener('click', () => {
    if (!localStorage.getItem('isLoggedIn')) {
        loginModal.style.display = loginModal.style.display === 'block' ? 'none' : 'block';
    }
});

// Fechar modal ao clicar fora da caixa
window.addEventListener('click', (e) => {
    if (e.target === loginModal) loginModal.style.display = 'none';
});

// Mostrar/ocultar senha
togglePassword.addEventListener('click', () => {
    const type = loginPassword.getAttribute('type') === 'password' ? 'text' : 'password';
    loginPassword.setAttribute('type', type);
    togglePassword.classList.toggle('fa-eye-slash');
});

// Botão de login
btnLogin.addEventListener('click', async () => {
    const email = document.getElementById('loginEmail').value.trim();
    const senha = loginPassword.value.trim();

    if (!email || !senha) {
        alert("Preencha todos os campos!");
        return;
    }

    try {
        const response = await api.post('/auth/login', { email, password: senha });

        // Salvar estado do login
        localStorage.setItem('isLoggedIn', 'true');
        localStorage.setItem('userImage', 'img/userPerfil/userNovo.png'); // temporário
        localStorage.setItem('token', response.data.token);

        // Salvar tempo de expiração
        const expirationTime = keepLogged.checked
            ? Date.now() + 14 * 24 * 60 * 60 * 1000 // 14 dias
            : Date.now() + 2 * 60 * 60 * 1000;     // 2 horas
        localStorage.setItem('expiration', expirationTime);

        // Fecha modal e redireciona para o perfil
        loginModal.style.display = 'none';
        window.location.href = 'index.html'; // Redirecionamento automático

    } catch (error) {
        console.error("Erro no login:", error.response || error);
        alert("Erro no login! Verifique email e senha.");
    }
});
