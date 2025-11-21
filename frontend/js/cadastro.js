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

// --- Máscaras e formatação ---
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

// Permite letras, números, ponto, underline e hífen no email
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

// --- Mostrar / ocultar senha ---
toggleCadastroSenha.addEventListener("click", () => {
    const type = cadastroSenha.type === "password" ? "text" : "password";
    cadastroSenha.type = type;
    toggleCadastroSenha.classList.toggle("fa-eye-slash");
});

toggleCadastroRepetirSenha.addEventListener("click", () => {
    const type = cadastroRepetirSenha.type === "password" ? "text" : "password";
    cadastroRepetirSenha.type = type;
    toggleCadastroRepetirSenha.classList.toggle("fa-eye-slash");
});

// --- Código de verificação ---
let codigoGerado = null;

btnEnviarCodigo.addEventListener("click", () => {
    codigoGerado = Math.floor(100000 + Math.random() * 900000).toString();
    alert("Código enviado! (temporário): " + codigoGerado);
});

// --- Submit do formulário ---
formCadastro.addEventListener("submit", async (event) => {
    event.preventDefault();

    // Valida código
    if (codigoInput.value !== codigoGerado) {
        alert("Código inválido!");
        return;
    }

    const emailCompleto = emailUserInput.value + "@gmail.com";

    // Valida senha
    const senha = cadastroSenha.value;
    const repetirSenha = cadastroRepetirSenha.value;

    // Mínimo 8 caracteres, 1 maiúscula, 1 número, 1 caractere especial
    const senhaRegex = /^(?=.*[A-Z])(?=.*[0-9])(?=.*[\W_]).{8,}$/;

    if (!senhaRegex.test(senha)) {
        alert("A senha deve conter: 1 letra maiúscula, 1 número, 1 caractere especial e mínimo 8 caracteres.");
        return;
    }

    if (senha !== repetirSenha) {
        alert("As senhas não coincidem!");
        return;
    }

    const usuario = {
        nome: nomeInput.value,
        email: emailCompleto,
        password: senha,
        telefone: telInput.value.replace(/\D/g, ""), // remove tudo que não é número
        cpf: cpfInput.value.replace(/\D/g, "")       // remove tudo que não é número
    };

    try {
        const response = await api.post("/auth/register", usuario);

        // Para produção, usaremos cookies seguros ao invés de localStorage
        localStorage.setItem("isLoggedIn", "true");
        localStorage.setItem("userImage", "img/userPerfil/userNovo.png");
        localStorage.setItem("token", response.data.token);
        localStorage.setItem("expiration", Date.now() + 14 * 24 * 60 * 60 * 1000);

        window.location.href = "index.html";
        
    } catch (error) {
        console.error(error);
        alert("Erro no servidor, tente novamente mais tarde!");
    }
});
