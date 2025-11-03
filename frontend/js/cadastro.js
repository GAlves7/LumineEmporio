// Mostrar/ocultar senha
const toggleCadastroSenha = document.getElementById('toggleCadastroSenha');
const cadastroSenha = document.getElementById('cadastroSenha');

toggleCadastroSenha.addEventListener('click', () => {
    const type = cadastroSenha.getAttribute('type') === 'password' ? 'text' : 'password';
    cadastroSenha.setAttribute('type', type);
    toggleCadastroSenha.classList.toggle('fa-eye-slash');
});

const toggleCadastroRepetirSenha = document.getElementById('toggleCadastroRepetirSenha');
const cadastroRepetirSenha = document.getElementById('cadastroRepetirSenha');

toggleCadastroRepetirSenha.addEventListener('click', () => {
    const type = cadastroRepetirSenha.getAttribute('type') === 'password' ? 'text' : 'password';
    cadastroRepetirSenha.setAttribute('type', type);
    toggleCadastroRepetirSenha.classList.toggle('fa-eye-slash');
});
