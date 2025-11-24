// ==== EDITAR CONTA JS ====
// Importa a API
import api from "./api.js";

// Seleção dos inputs
const nomeInput = document.getElementById("nomeCompleto");
const telefoneInput = document.getElementById("telefone");
const emailInput = document.getElementById("email");
const senhaInput = document.getElementById("senha");
const senha2Input = document.getElementById("senha2");
const btnSalvar = document.getElementById("btnSalvar");

// ===========================
// Nome: somente letras e espaços
// ===========================
nomeInput.addEventListener("input", () => {
    nomeInput.value = nomeInput.value.replace(/[^a-zA-ZÀ-ÿ\s]/g, "");
});

nomeInput.addEventListener("blur", () => {
    const nomeFormatado = nomeInput.value
        .split(" ")
        .filter(word => word !== "")
        .map(word => word.charAt(0).toUpperCase() + word.slice(1).toLowerCase())
        .join(" ");
    nomeInput.value = nomeFormatado;
});

// ===========================
// Telefone: máscara brasileira
// ===========================
telefoneInput.addEventListener("input", () => {
    let tel = telefoneInput.value.replace(/\D/g, "");
    if (tel.length > 11) tel = tel.slice(0, 11);

    if (tel.length > 10) {
        telefoneInput.value = `(${tel.slice(0,2)}) ${tel.slice(2,7)}-${tel.slice(7,11)}`;
    } else if (tel.length > 5) {
        telefoneInput.value = `(${tel.slice(0,2)}) ${tel.slice(2,6)}-${tel.slice(6,10)}`;
    } else if (tel.length > 2) {
        telefoneInput.value = `(${tel.slice(0,2)}) ${tel.slice(2)}`;
    } else if (tel.length > 0) {
        telefoneInput.value = `(${tel}`;
    }
});

// ===========================
// Email: caracteres válidos
// ===========================
emailInput.addEventListener("input", () => {
    emailInput.value = emailInput.value.replace(/[^a-zA-Z0-9@._-]/g, "");
});

// ===========================
// Olho da senha
// ===========================
function criarOlhoSenha(input) {
    const wrapper = document.createElement("div");
    wrapper.style.position = "relative";
    input.parentNode.insertBefore(wrapper, input);
    wrapper.appendChild(input);

    const btn = document.createElement("button");
    btn.type = "button";
    btn.innerHTML = '<i class="fa-solid fa-eye"></i>';
    btn.style.position = "absolute";
    btn.style.right = "10px";
    btn.style.top = "50%";
    btn.style.transform = "translateY(-50%)";
    btn.style.background = "transparent";
    btn.style.border = "none";
    btn.style.cursor = "pointer";
    btn.style.color = "#333";
    wrapper.appendChild(btn);

    btn.addEventListener("click", () => {
        if (input.type === "password") {
            input.type = "text";
            btn.innerHTML = '<i class="fa-solid fa-eye-slash"></i>';
        } else {
            input.type = "password";
            btn.innerHTML = '<i class="fa-solid fa-eye"></i>';
        }
    });
}

criarOlhoSenha(senhaInput);
criarOlhoSenha(senha2Input);

// ===========================
// Botão salvar -> backend + deslogar
// ===========================
btnSalvar.addEventListener("click", async () => {
    const nome = nomeInput.value.trim();
    const email = emailInput.value.trim();
    const telefone = telefoneInput.value.replace(/\D/g, ""); // só números
    const novaSenha = senhaInput.value;
    const confirmarSenha = senha2Input.value;

    if (!nome || !email) {
        alert("Nome e email são obrigatórios!");
        return;
    }
    if (novaSenha && novaSenha !== confirmarSenha) {
        alert("As senhas não conferem!");
        return;
    }

    const token = localStorage.getItem("token");

    try {
        const response = await api.put(
            "/api/perfil",
            { nome, email, telefone, novaSenha, confirmarSenha },
            {
                headers: {
                    Authorization: `Bearer ${token}`,
                    "Content-Type": "application/json"
                }
            }
        );

        alert("Dados atualizados com sucesso! Você será deslogado.");
        console.log(response.data);

        // ===========================
        // Deslogar o usuário após atualizar dados
        // ===========================
        localStorage.removeItem("isLoggedIn");
        localStorage.removeItem("userImage");
        localStorage.removeItem("token");
        localStorage.removeItem("loginExpiracao");
        window.location.href = "index.html";

    } catch (error) {
        console.error(error);
        if (error.response && error.response.data) {
            alert(`Erro: ${error.response.data.mensagem || "Não foi possível atualizar"}`);
        } else {
            alert("Erro ao atualizar dados.");
        }
    }
});
