// Controle do modal de login
const btnUser = document.querySelector('.btn-user');
const loginModal = document.getElementById('loginModal');

btnUser.addEventListener('click', () => {
    loginModal.style.display = loginModal.style.display === 'block' ? 'none' : 'block';
});

window.addEventListener('click', (e) => {
    if (e.target === loginModal) {
        loginModal.style.display = 'none';
    }
});

// Mostrar/ocultar senha
const togglePassword = document.getElementById('togglePassword');
const loginPassword = document.getElementById('loginPassword');

togglePassword.addEventListener('click', () => {
    const type = loginPassword.getAttribute('type') === 'password' ? 'text' : 'password';
    loginPassword.setAttribute('type', type);
    togglePassword.classList.toggle('fa-eye-slash');
});
