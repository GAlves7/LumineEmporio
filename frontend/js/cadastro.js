import api from './api.js';

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
// MÁSCARAS
// ============================================================================
nomeInput.addEventListener("input", () => {
    nomeInput.value = nomeInput.value.replace(/[0-9]/g, "");
});

nomeInput.addEventListener("blur", () => {
    nomeInput.value = nomeInput.value
        .toLowerCase()
        .replace(/\b\w/g, l => l.toUpperCase());
});

telInput.addEventListener("input", () => {
    let v = telInput.value.replace(/\D/g, "");
    if (v.length > 11) v = v.slice(0, 11);

    if (v.length > 6) telInput.value = `(${v.slice(0,2)}) ${v.slice(2,7)}-${v.slice(7,11)}`;
    else if (v.length > 2) telInput.value = `(${v.slice(0,2)}) ${v.slice(2,7)}`;
    else telInput.value = v;
});

emailUserInput.addEventListener("input", () => {
    emailUserInput.value = emailUserInput.value.replace(/[^a-zA-Z0-9._-]/g, "");
});

cpfInput.addEventListener("input", () => {
    let v = cpfInput.value.replace(/\D/g, "");
    if (v.length > 11) v = v.slice(0, 11);

    if (v.length > 9) cpfInput.value = `${v.slice(0,3)}.${v.slice(3,6)}.${v.slice(6,9)}-${v.slice(9,11)}`;
    else if (v.length > 6) cpfInput.value = `${v.slice(0,3)}.${v.slice(3,6)}.${v.slice(6,9)}`;
    else if (v.length > 3) cpfInput.value = `${v.slice(0,3)}.${v.slice(3,6)}`;
    else cpfInput.value = v;
});

// ============================================================================
// EXIBIR / OCULTAR SENHA
// ============================================================================
toggleCadastroSenha.addEventListener("click", () => {
    cadastroSenha.type = cadastroSenha.type === "password" ? "text" : "password";
    toggleCadastroSenha.classList.toggle("fa-eye-slash");
});

toggleCadastroRepetirSenha.addEventListener("click", () => {
    cadastroRepetirSenha.type = cadastroRepetirSenha.type === "password" ? "text" : "password";
    toggleCadastroRepetirSenha.classList.toggle("fa-eye-slash");
});

// ============================================================================
// ENVIAR CÓDIGO PARA O EMAIL
// ============================================================================
btnEnviarCodigo.addEventListener("click", async () => {
    const emailCompleto = emailUserInput.value.trim() + "@gmail.com";

    if (emailUserInput.value.trim() === "") {
        alert("Digite um email antes de enviar o código.");
        return;
    }

    try {
        const formData = new FormData();
        formData.append("email", emailCompleto);

        await api.post("auth/register/enviar-codigo", formData, {
            headers: { "Content-Type": "multipart/form-data" }
        });

        console.log("Código enviado para seu email!");

    } catch (error) {
        console.error(error);
        alert("Erro ao enviar o código. Tente novamente.");
    }
});

// ============================================================================
// CADASTRAR USUÁRIO
// ============================================================================
formCadastro.addEventListener("submit", async (event) => {
    event.preventDefault();

    const emailCompleto = emailUserInput.value.trim() + "@gmail.com";

    const senha = cadastroSenha.value;
    const repetirSenha = cadastroRepetirSenha.value;

    // Senha forte
    const senhaRegex = /^(?=.*[A-Z])(?=.*[0-9])(?=.*[\W_]).{8,}$/;

    if (!senhaRegex.test(senha)) {
        alert("A senha deve conter: 1 letra maiúscula, 1 número, 1 caractere especial e mínimo 8 caracteres.");
        return;
    }

    if (senha !== repetirSenha) {
        alert("As senhas não coincidem!");
        return;
    }

    if (codigoInput.value.trim() === "") {
        alert("Digite o código enviado para seu email.");
        return;
    }

    const usuario = {
        nome: nomeInput.value.trim(),
        email: emailCompleto,
        password: senha,
        telefone: telInput.value.replace(/\D/g, ""),
        cpf: cpfInput.value.replace(/\D/g, ""),
        codigo: codigoInput.value.trim()
    };

    try {
        const response = await api.post("auth/register", usuario);

        // Login automático pós cadastro
        localStorage.setItem("isLoggedIn", "true");
        localStorage.setItem("userImage", "img/userPerfil/userNovo.png");
        localStorage.setItem("token", response.data.token);
        localStorage.setItem("expiration", Date.now() + 14 * 24 * 60 * 60 * 1000);

        window.location.href = "index.html";

    } catch (error) {
        console.log(error);
        alert(error.response?.data?.message || "Erro no servidor. Tente novamente.");
    }
});
