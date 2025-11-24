// Botão de logout
const btnSair = document.querySelector('.perfil-botoes .sair');

btnSair.addEventListener('click', () => {
    localStorage.removeItem('isLoggedIn');
    localStorage.removeItem('userImage');
    localStorage.removeItem('token');
    localStorage.removeItem('loginExpiracao');
    window.location.href = 'index.html';
});

// Função para formatar telefone
function formatarTelefone(telefone) {
    if (!telefone || telefone.length !== 11) return telefone;
    return `(${telefone.substring(0,2)}) ${telefone.substring(2,7)}-${telefone.substring(7)}`;
}

// Função para carregar os dados do usuário
async function carregarPerfil() {
    const token = localStorage.getItem('token'); // Pegando token do login
    if (!token) {
        alert("Você precisa estar logado!");
        window.location.href = "index.html";
        return;
    }

    try {
        const response = await axios.get("http://localhost:8080/api/perfil", { // Substitua pelo seu endpoint real
            headers: {
                Authorization: `Bearer ${token}`
            }
        });

        const perfil = response.data;

        // Atualizando DOM
        document.querySelector('.perfil-info .perfil-dado .valor').textContent = perfil.nome;
        document.querySelectorAll('.perfil-info .perfil-dado .valor')[1].textContent = perfil.email;
        document.querySelector('#vl').textContent = formatarTelefone(perfil.telefone);

    } catch (error) {
        console.error("Erro ao carregar perfil:", error);
        alert("Erro ao carregar perfil. Faça login novamente.");
        window.location.href = "index.html";
    }
}

// Chamar função ao carregar a página
window.addEventListener('DOMContentLoaded', carregarPerfil);
