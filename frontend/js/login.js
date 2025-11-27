// ============================================================================
// IMPORTAÇÃO DO AXIOS CONFIGURADO
// ============================================================================
import api from './api.js';


// ============================================================================
// SELEÇÃO DE ELEMENTOS DO MODAL DE LOGIN
// ============================================================================
const btnUser = document.querySelector('.btn-user');          // Ícone do usuário no header
const loginModal = document.getElementById('loginModal');     // Modal de login
const togglePassword = document.getElementById('togglePassword'); // Ícone do "mostrar senha"
const loginPassword = document.getElementById('loginPassword');   // Campo de senha
const btnLogin = document.getElementById('btnLogin');             // Botão "Entrar"
const keepLogged = document.getElementById('keepLogged');         // Checkbox "Manter-me logado"


// ============================================================================
// ABRIR / FECHAR MODAL AO CLICAR NO ÍCONE DO USUÁRIO
// ============================================================================
btnUser.addEventListener('click', () => {

    // Só abre o modal se o usuário NÃO estiver logado
    if (!localStorage.getItem('isLoggedIn')) {

        // Alterna entre mostrar e ocultar o modal
        loginModal.style.display =
            loginModal.style.display === 'block' ? 'none' : 'block';
    }
});


// ============================================================================
// FECHAR O MODAL AO CLICAR FORA DA CAIXA
// ============================================================================
window.addEventListener('click', (e) => {
    // Se o clique foi exatamente no fundo do modal → fecha
    if (e.target === loginModal) {
        loginModal.style.display = 'none';
    }
});


// ============================================================================
// MOSTRAR / OCULTAR SENHA
// ============================================================================
togglePassword.addEventListener('click', () => {

    // Alterna entre password <-> text
    const type = loginPassword.type === 'password' ? 'text' : 'password';
    loginPassword.type = type;

    // Alterna entre ícone aberto / fechado
    togglePassword.classList.toggle('fa-eye-slash');
});


// ============================================================================
// PROCESSO DE LOGIN
// ============================================================================
btnLogin.addEventListener('click', async () => {

    // Pegando valores dos inputs
    const email = document.getElementById('loginEmail').value.trim();
    const senha = loginPassword.value.trim();

    // Validação simples
    if (!email || !senha) {
        alert("Preencha todos os campos!");
        return;
    }

    try {
        // Envia email e senha para o backend usando Axios
        const response = await api.post('/auth/login', {
            email,
            password: senha
        });

        // ====================================================================
        // SALVANDO DADOS DO LOGIN NO LOCALSTORAGE
        // ====================================================================

        localStorage.setItem('isLoggedIn', 'true');

        // Imagem temporária do usuário (caso não tenha foto cadastrada)
        localStorage.setItem('userImage', 'img/userPerfil/userNovo.png');

        // Token JWT entregue pelo backend
        localStorage.setItem('token', response.data.token);

        // Expiração do login → 14 dias se "manter logado", senão 2h
        const expirationTime = keepLogged.checked
            ? Date.now() + 14 * 24 * 60 * 60 * 1000   // 14 dias
            : Date.now() + 2 * 60 * 60 * 1000;        // 2 horas

        localStorage.setItem('expiration', expirationTime);

        // Fecha modal
        loginModal.style.display = 'none';

        // Redireciona usuário para página principal após login
        window.location.href = 'index.html';

    } catch (error) {
        console.error("Erro no login:", error.response || error);
        alert("Erro no login! Verifique email e senha ou tente novamente mais tarde!.");
    }
});
