import api from './api.js';

/* ==========================================================
   1) PRIMEIRA PÁGINA — ENVIO DO E-MAIL DE RECUPERAÇÃO
   Esta parte só é executada se existir o input #emailRecuperar.
   Isso evita erros caso o mesmo JS seja carregado em outra página.
========================================================== */
const emailInput = document.getElementById('emailRecuperar');
const btnEnviar = document.querySelector('.btn-enviar');

if (emailInput && btnEnviar) {

    // Clique no botão "Enviar link de recuperação"
    btnEnviar.addEventListener('click', async (e) => {
        e.preventDefault(); // impede comportamento padrão do botão

        const email = emailInput.value.trim(); // remove espaços extras

        // Validação básica
        if (!email) {
            alert("Por favor, digite seu e-mail!");
            return;
        }

        try {
            /* ------------------------------------------------------------
               Envia o e-mail para o backend gerar o link de redefinição.
               O endpoint /auth/redefinir-senha dispara o envio do e-mail.
            ------------------------------------------------------------ */
            await api.post('/auth/redefinir-senha', { email });

            alert("Um link para redefinir sua senha foi enviado ao seu e-mail!");

            // O backend realmente envia o e-mail — aqui apenas avisamos o usuário.
        } catch (error) {
            console.error("Erro ao enviar email para recuperação:", error.response || error);
            alert("E-mail não encontrado ou ocorreu um erro. Verifique e tente novamente.");
        }
    });
}

/* ==========================================================
   2) SEGUNDA PÁGINA — DEFINIR A NOVA SENHA
   Somente executa se os inputs de nova senha existirem na página.
========================================================== */

const novaSenha = document.getElementById('novaSenha');
const confirmarNovaSenha = document.getElementById('confirmarNovaSenha');
const btnSalvarSenha = document.getElementById('btnSalvarSenha');

if (novaSenha && confirmarNovaSenha && btnSalvarSenha) {

    btnSalvarSenha.addEventListener('click', async () => {

        const senha1 = novaSenha.value.trim();
        const senha2 = confirmarNovaSenha.value.trim();

        /* ------------------------------------------------------------
           Validações básicas antes de enviar ao backend
        ------------------------------------------------------------ */
        if (!senha1 || !senha2) {
            alert("Preencha todos os campos de senha!");
            return;
        }

        if (senha1 !== senha2) {
            alert("As senhas não coincidem!");
            return;
        }

        /* ------------------------------------------------------------
           Recupera o token e tokenId enviados pelo link do e-mail.
           Ex.: recuperarNovaSenha.html?token=...&tokenId=...
        ------------------------------------------------------------ */
        const urlParams = new URLSearchParams(window.location.search);
        const token = urlParams.get('token');
        const tokenId = urlParams.get('tokenId');

        if (!token || !tokenId) {
            alert("Link inválido. Gere um novo link de recuperação.");
            return;
        }

        try {
            /* ------------------------------------------------------------
               O backend exige multipart/form-data.
               Por isso a senha é enviada dentro de um FormData.
            ------------------------------------------------------------ */
            const formData = new FormData();
            formData.append("novaSenha", senha1);

            /* ------------------------------------------------------------
               Requisição responsável por validar o token
               e salvar a nova senha no banco.
            ------------------------------------------------------------ */
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
            window.location.href = "index.html"; // redireciona para login/home

        } catch (error) {
            console.error("Erro ao redefinir senha:", error.response || error);
            alert(error.response?.data || "Ocorreu um erro. Gere outro link.");
        }

    });
}
