// ============================================================================
// IMPORTAÇÃO DA API (Axios pré-configurado)
// ============================================================================
import api from './api.js';


// ============================================================================
// CAPTURA DOS ELEMENTOS DO DOM
// ============================================================================
const nomeInput = document.getElementById("nomeCompleto");
const telInput = document.getElementById("telefone");
const emailUserInput = document.getElementById("emailUser");
const codigoInput = document.getElementById("codigoInput");
const btnEnviarCodigo = document.getElementById("btnEnviarCodigo");
const cpfInput = document.getElementById("cpf");

const cadastroSenha = document.getElementById("cadastroSenha");
const cadastroRepetirSenha = document.getElementById("cadastroRepetirSenha");
const toggleCadastroSenha = document.getElementById("toggleCadastroSenha");
const toggleCadastroRepetirSenha = document.getElementById("toggleCadastroRepetirSenha");

const formCadastro = document.getElementById("formCadastro");


// ============================================================================
// MÁSCARAS E TRATAMENTO DOS CAMPOS
// ============================================================================

// Impede números no nome
nomeInput.addEventListener("input", () => {
    nomeInput.value = nomeInput.value.replace(/[0-9]/g, "");
});

// Formata o nome com iniciais maiúsculas ao sair do campo
nomeInput.addEventListener("blur", () => {
    nomeInput.value = nomeInput.value
        .toLowerCase()
        .replace(/\b\w/g, l => l.toUpperCase());
});

// Máscara para telefone no formato (DD) 99999-9999
telInput.addEventListener("input", () => {
    let v = telInput.value.replace(/\D/g, "");

    // Limita a 11 dígitos
    if (v.length > 11) v = v.slice(0, 11);

    if (v.length > 6)
        telInput.value = `(${v.slice(0,2)}) ${v.slice(2,7)}-${v.slice(7,11)}`;
    else if (v.length > 2)
        telInput.value = `(${v.slice(0,2)}) ${v.slice(2,7)}`;
    else
        telInput.value = v;
});

// Permite apenas caracteres válidos antes do @gmail.com
emailUserInput.addEventListener("input", () => {
    emailUserInput.value = emailUserInput.value.replace(/[^a-zA-Z0-9._-]/g, "");
});

// Máscara do CPF: 999.999.999-99
cpfInput.addEventListener("input", () => {
    let v = cpfInput.value.replace(/\D/g, "");
    if (v.length > 11) v = v.slice(0, 11);

    if (v.length > 9)
        cpfInput.value = `${v.slice(0,3)}.${v.slice(3,6)}.${v.slice(6,9)}-${v.slice(9,11)}`;
    else if (v.length > 6)
        cpfInput.value = `${v.slice(0,3)}.${v.slice(3,6)}.${v.slice(6,9)}`;
    else if (v.length > 3)
        cpfInput.value = `${v.slice(0,3)}.${v.slice(3,6)}`;
    else
        cpfInput.value = v;
});


// ============================================================================
// EXIBIR / OCULTAR SENHAS
// ============================================================================

// Alterna o campo "senha" entre password e texto
toggleCadastroSenha.addEventListener("click", () => {
    cadastroSenha.type = cadastroSenha.type === "password" ? "text" : "password";
    toggleCadastroSenha.classList.toggle("fa-eye-slash");
});

// Alterna o campo "repetir senha"
toggleCadastroRepetirSenha.addEventListener("click", () => {
    cadastroRepetirSenha.type = cadastroRepetirSenha.type === "password" ? "text" : "password";
    toggleCadastroRepetirSenha.classList.toggle("fa-eye-slash");
});


// ============================================================================
// ENVIO DO CÓDIGO DE VERIFICAÇÃO PARA O EMAIL DO USUÁRIO
// ============================================================================
btnEnviarCodigo.addEventListener("click", async () => {
    const emailCompleto = emailUserInput.value.trim() + "@gmail.com";

    // Valida se o usuário digitou algo antes de enviar o código
    if (emailUserInput.value.trim() === "") {
        alert("Digite um email antes de enviar o código.");
        return;
    }

    try {
        // Cria envio multipart porque o backend está configurado assim
        const formData = new FormData();
        formData.append("email", emailCompleto);

        // Requisição POST para envio do código
        await api.post("auth/register/enviar-codigo", formData, {
            headers: { "Content-Type": "multipart/form-data" }
        });

        console.log("Código enviado para seu email!");

    } catch (error) {
        console.error(error);
        alert("Erro ao enviar o código. Tente novamente mais tarde!");
    }
});


// ============================================================================
// CADASTRO DO USUÁRIO NO SERVIDOR
// ============================================================================
formCadastro.addEventListener("submit", async (event) => {
    event.preventDefault(); // Impede recarregar a página

    const emailCompleto = emailUserInput.value.trim() + "@gmail.com";

    const senha = cadastroSenha.value;
    const repetirSenha = cadastroRepetirSenha.value;

    // Regex para validar senha forte
    const senhaRegex = /^(?=.*[A-Z])(?=.*[0-9])(?=.*[\W_]).{8,}$/;

    // Verifica se senha é forte
    if (!senhaRegex.test(senha)) {
        alert("A senha deve conter: 1 letra maiúscula, 1 número, 1 caractere especial e mínimo 8 caracteres.");
        return;
    }

    // Verifica se senhas coincidem
    if (senha !== repetirSenha) {
        alert("As senhas não coincidem!");
        return;
    }

    // Verifica se o usuário digitou o código recebido no email
    if (codigoInput.value.trim() === "") {
        alert("Digite o código enviado para seu email.");
        return;
    }

    // Criação do objeto enviado ao backend
    const usuario = {
        nome: nomeInput.value.trim(),
        email: emailCompleto,
        password: senha,
        telefone: telInput.value.replace(/\D/g, ""), // remove máscara
        cpf: cpfInput.value.replace(/\D/g, ""),      // remove máscara
        codigo: codigoInput.value.trim()
    };

    try {
        // Envia requisição ao backend
        const response = await api.post("auth/register", usuario);

        // --------------------------------------------------------------------
        // LOGIN AUTOMÁTICO APÓS O CADASTRO COM SUCESSO
        // --------------------------------------------------------------------
        localStorage.setItem("isLoggedIn", "true");
        localStorage.setItem("userImage", "img/userPerfil/userNovo.png");
        localStorage.setItem("token", response.data.token);

        // Expira em 14 dias
        localStorage.setItem("expiration", Date.now() + 14 * 24 * 60 * 60 * 1000);

        // Redireciona para a página principal
        window.location.href = "index.html";

    } catch (error) {
        console.log(error);
        alert(error.response?.data?.message || "Erro no servidor. Tente novamente mais tarde!");
    }
});
