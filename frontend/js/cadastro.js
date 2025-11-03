import api from './api.js';

// Seleciona o ícone para mostrar/ocultar a senha do campo principal de cadastro
const toggleCadastroSenha = document.getElementById('toggleCadastroSenha');
// Seleciona o input de senha do cadastro
const cadastroSenha = document.getElementById('cadastroSenha');

// Adiciona evento de clique ao ícone
toggleCadastroSenha.addEventListener('click', () => {
    // Alterna o tipo do input entre 'password' e 'text' para mostrar ou esconder a senha
    const type = cadastroSenha.getAttribute('type') === 'password' ? 'text' : 'password';
    cadastroSenha.setAttribute('type', type);
    // Alterna a classe do ícone para mudar a aparência (olho aberto/fechado)
    toggleCadastroSenha.classList.toggle('fa-eye-slash');
});

// Seleciona o ícone para mostrar/ocultar a senha de confirmação
const toggleCadastroRepetirSenha = document.getElementById('toggleCadastroRepetirSenha');
// Seleciona o input de repetir senha
const cadastroRepetirSenha = document.getElementById('cadastroRepetirSenha');

// Adiciona evento de clique ao ícone de repetir senha
toggleCadastroRepetirSenha.addEventListener('click', () => {
    // Alterna o tipo do input entre 'password' e 'text'
    const type = cadastroRepetirSenha.getAttribute('type') === 'password' ? 'text' : 'password';
    cadastroRepetirSenha.setAttribute('type', type);
    // Alterna a classe do ícone para mudar a aparência (olho aberto/fechado)
    toggleCadastroRepetirSenha.classList.toggle('fa-eye-slash');
});

// Seleciona o formulário de cadastro
const formCadastro = document.getElementById('formCadastro');

// Adiciona evento de envio
formCadastro.addEventListener('submit', async (event) => {
    event.preventDefault(); // Impede o recarregamento da página

    // Captura os valores dos campos do formulário
    const nome = formCadastro.querySelector('input[placeholder="Nome completo"]').value.trim();
    const telefone = formCadastro.querySelector('input[placeholder="Telefone (XX) 9XXXX-XXXX"]').value.trim();
    const email = formCadastro.querySelector('input[placeholder="Email"]').value.trim();
    const senha = document.getElementById('cadastroSenha').value.trim();
    const repetirSenha = document.getElementById('cadastroRepetirSenha').value.trim();

    // Validação simples: senhas iguais
    if (senha !== repetirSenha) {
        alert("As senhas não coincidem!");
        return;
    }

    // Cria o objeto com os dados do usuário
    const usuario = {
        nome: nome,
        email: email,
        password: senha,
        telefone: telefone
    };

    try {
        // Envia os dados para o backend (ajuste a rota conforme o backend do grupo)
        const response = await api.post('/auth/register', usuario);

        // Sucesso
        alert("Cadastro realizado com sucesso!");
        console.log(response.data);

        // Redireciona, se quiser:
        // window.location.href = "login.html";

    } catch (error) {
        console.error("Erro ao cadastrar usuário:", error);
        alert("Erro ao cadastrar! Verifique os dados ou tente novamente.");
    }
});
