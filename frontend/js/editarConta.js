// ==== EDITAR CONTA JS ====

// Seleção dos inputs
const nomeInput = document.getElementById("nomeCompleto");
const telefoneInput = document.getElementById("telefone");
const senhaInput = document.getElementById("senha");
const senha2Input = document.getElementById("senha2");

// ===========================
// Impedir números e caracteres especiais no nome
// ===========================
nomeInput.addEventListener("input", () => {
    // Substitui tudo que não for letra ou espaço por vazio
    nomeInput.value = nomeInput.value.replace(/[^a-zA-ZÀ-ÿ\s]/g, "");
});

// ===========================
// Formatar nome automaticamente (capitalização)
// ===========================
nomeInput.addEventListener("blur", () => {
    const nomeFormatado = nomeInput.value
        .split(" ")
        .filter(word => word !== "")
        .map(word => word.charAt(0).toUpperCase() + word.slice(1).toLowerCase())
        .join(" ");
    
    nomeInput.value = nomeFormatado;
});

// ===========================
// Formatar telefone brasileiro automaticamente
// ===========================
telefoneInput.addEventListener("input", () => {
    let tel = telefoneInput.value.replace(/\D/g, ""); // remove tudo que não for número
    if (tel.length > 11) tel = tel.slice(0, 11); // limita a 11 números

    // Aplica a máscara
    let telFormatado = tel;
    if (tel.length > 10) {
        telFormatado = `(${tel.slice(0,2)}) ${tel.slice(2,7)}-${tel.slice(7,11)}`;
    } else if (tel.length > 5) {
        telFormatado = `(${tel.slice(0,2)}) ${tel.slice(2,6)}-${tel.slice(6,10)}`;
    } else if (tel.length > 2) {
        telFormatado = `(${tel.slice(0,2)}) ${tel.slice(2)}`;
    } else if (tel.length > 0) {
        telFormatado = `(${tel}`;
    }
    telefoneInput.value = telFormatado;
});

// Selecionar input de email
const emailInput = document.getElementById("email");

// Filtrar caracteres do email (somente válidos)
emailInput.addEventListener("input", () => {
    // Permite: letras, números, @, ., -, _
    emailInput.value = emailInput.value.replace(/[^a-zA-Z0-9@._-]/g, "");
});

// ===========================
// Mostrar/Ocultar senha
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

// Aplicar nos dois campos de senha
criarOlhoSenha(senhaInput);
criarOlhoSenha(senha2Input);
