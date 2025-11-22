import api from './api.js';

/* ==========================================================
   1) PRIMEIRA PÁGINA — ENVIAR E-MAIL DE RECUPERAÇÃO
   Só executa se existir o input #emailRecuperar
========================================================== */
const emailInput = document.getElementById('emailRecuperar');
const btnEnviar = document.querySelector('.btn-enviar');

if (emailInput && btnEnviar) {
    btnEnviar.addEventListener('click', async (e) => {
        e.preventDefault(); // evita abrir link automaticamente

        const email = emailInput.value.trim();

        if (!email) {
            alert("Por favor, digite seu e-mail!");
            return;
        }

        try {
            // Envia o email para o backend
            await api.post('/auth/redefinir-senha', { email });

            alert("Um link para redefinir sua senha foi enviado ao seu e-mail!");

            // O BACKEND enviará o link com o token por e-mail
            // Aqui apenas avisamos o usuário
        } catch (error) {
            console.error("Erro ao enviar email para recuperação:", error.response || error);
            alert("E-mail não encontrado ou ocorreu um erro. Verifique e tente novamente.");
        }
    });
}

/* ==========================================================
   2) SEGUNDA PÁGINA — DEFINIR NOVA SENHA
========================================================== */

const novaSenha = document.getElementById('novaSenha');
const confirmarNovaSenha = document.getElementById('confirmarNovaSenha');
const btnSalvarSenha = document.getElementById('btnSalvarSenha');

if (novaSenha && confirmarNovaSenha && btnSalvarSenha) {

    btnSalvarSenha.addEventListener('click', async () => {

        const senha1 = novaSenha.value.trim();
        const senha2 = confirmarNovaSenha.value.trim();

        if (!senha1 || !senha2) {
            alert("Preencha todos os campos de senha!");
            return;
        }

        if (senha1 !== senha2) {
            alert("As senhas não coincidem!");
            return;
        }

        // Captura token e tokenId da URL
        const urlParams = new URLSearchParams(window.location.search);
        const token = urlParams.get('token');
        const tokenId = urlParams.get('tokenId');

        if (!token || !tokenId) {
            alert("Link inválido. Gere um novo link de recuperação.");
            return;
        }

        try {
            // Agora a senha vai no FormData (BODY — mais seguro)
            const formData = new FormData();
            formData.append("novaSenha", senha1);

            await api.post(`/auth/verificar-link`, formData, {
                params: {
                    token: token,
                    tokenId: tokenId
                },
                headers: {
                    "Content-Type": "multipart/form-data"
                }
            });

            alert("Senha redefinida com sucesso!");
            window.location.href = "index.html";

        } catch (error) {
            console.error("Erro ao redefinir senha:", error.response || error);
            alert(error.response?.data || "Ocorreu um erro. Gere outro link.");
        }

    });
}
