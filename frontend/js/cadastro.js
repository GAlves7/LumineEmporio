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
