// ==== EDITAR CONTA JS ====

// Importa a instância configurada do Axios
import api from "./api.js";


// ===========================
// Seleção dos elementos do formulário
// ===========================
const nomeInput = document.getElementById("nomeCompleto");
const telefoneInput = document.getElementById("telefone");
const emailInput = document.getElementById("email");
const senhaInput = document.getElementById("senha");
const senha2Input = document.getElementById("senha2");
const btnSalvar = document.getElementById("btnSalvar");



// =============================================================
// NOME → Permitir somente letras e espaços + formatação automática
// =============================================================

// Remove caracteres que não sejam letras ou espaços
nomeInput.addEventListener("input", () => {
    nomeInput.value = nomeInput.value.replace(/[^a-zA-ZÀ-ÿ\s]/g, "");
});

// Ao sair do campo, formata para "Primeira Letra Maiúscula"
nomeInput.addEventListener("blur", () => {
    const nomeFormatado = nomeInput.value
        .split(" ")
        .filter(word => word !== "") // remove espaços duplos
        .map(word => word.charAt(0).toUpperCase() + word.slice(1).toLowerCase())
        .join(" "); // junta novamente
    nomeInput.value = nomeFormatado;
});



// =============================================================
// TELEFONE → Máscara automática brasileira (XX) XXXXX-XXXX
// =============================================================
telefoneInput.addEventListener("input", () => {
    let tel = telefoneInput.value.replace(/\D/g, ""); // remove tudo que não for número
    
    if (tel.length > 11) tel = tel.slice(0, 11); // limita para 11 dígitos

    // Formatação automática conforme o tamanho
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



// =============================================================
// EMAIL → Permite somente caracteres válidos de email
// =============================================================
emailInput.addEventListener("input", () => {
    emailInput.value = emailInput.value.replace(/[^a-zA-Z0-9@._-]/g, "");
});



// =============================================================
// BOTÃO "OLHO" → Mostra/oculta senha
// =============================================================
function criarOlhoSenha(input) {
    // Cria um wrapper para posicionar o botão dentro do input
    const wrapper = document.createElement("div");
    wrapper.style.position = "relative";

    // Insere o input dentro do wrapper
    input.parentNode.insertBefore(wrapper, input);
    wrapper.appendChild(input);

    // Criação do botão do olho
    const btn = document.createElement("button");
    btn.type = "button";
    btn.innerHTML = '<i class="fa-solid fa-eye"></i>';

    // Estilo do botão do olho
    btn.style.position = "absolute";
    btn.style.right = "10px";
    btn.style.top = "50%";
    btn.style.transform = "translateY(-50%)";
    btn.style.background = "transparent";
    btn.style.border = "none";
    btn.style.cursor = "pointer";
    btn.style.color = "#333";

    wrapper.appendChild(btn);

    // Alterna entre mostrar e esconder a senha
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

// Aplica o "olho" nas duas senhas
criarOlhoSenha(senhaInput);
criarOlhoSenha(senha2Input);



// =============================================================
// SALVAR ALTERAÇÕES → Valida + envia para backend → desloga
// =============================================================
btnSalvar.addEventListener("click", async () => {

    // Captura dos dados
    const nome = nomeInput.value.trim();
    const email = emailInput.value.trim();
    const telefone = telefoneInput.value.replace(/\D/g, ""); // apenas números
    const novaSenha = senhaInput.value;
    const confirmarSenha = senha2Input.value;

    // Validação básica
    if (!nome || !email) {
        alert("Nome e email são obrigatórios!");
        return;
    }

    // Checa se as senhas coincidem
    if (novaSenha && novaSenha !== confirmarSenha) {
        alert("As senhas não conferem!");
        return;
    }

    const token = localStorage.getItem("token");

    try {
        // Envia atualização para o backend
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
        // DESLOGA após alteração para segurança
        // ===========================
        localStorage.removeItem("isLoggedIn");
        localStorage.removeItem("userImage");
        localStorage.removeItem("token");
        localStorage.removeItem("loginExpiracao");

        // Retorna para a página inicial
        window.location.href = "index.html";

    } catch (error) {
        console.error(error);

        // Se o backend retornou mensagem
        if (error.response && error.response.data) {
            alert(`Erro: ${error.response.data.mensagem || "Não foi possível atualizar"}`);
        } else {
            alert("Erro ao atualizar dados.");
        }
    }
});
