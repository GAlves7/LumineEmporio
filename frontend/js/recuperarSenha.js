import api from './api.js';

// Seleciona o input de email
const emailInput = document.getElementById('emailRecuperar');

// Seleciona o botão de envio
const btnEnviar = document.querySelector('.btn-enviar');

btnEnviar.addEventListener('click', async (e) => {
    e.preventDefault(); // impede redirecionamento padrão do <a>

    const email = emailInput.value.trim();

    if (!email) {
        alert("Por favor, digite seu e-mail!");
        return;
    }

    try {
        // Envia o email para o backend
        await api.post('/auth/redefinir-senha', { email: email });

        // Se não deu erro, considera que o email existe
        alert("Código enviado para o seu e-mail!");
        window.location.href = "recuperarSenha2.html";

    } catch (error) {
        // Se der erro, email não existe ou outro problema
        console.error("Erro ao enviar email para recuperação:", error.response || error);
        alert("E-mail não encontrado ou ocorreu um erro. Verifique e tente novamente.");
    }
});
