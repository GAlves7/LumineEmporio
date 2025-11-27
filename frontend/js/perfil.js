// ==== PERFIL JS ====

// Importa a configuração da API (baseURL + interceptors se houver)
import api from "./api.js";

// Seleciona o botão "Sair" dentro do bloco de botões do perfil
const btnSair = document.querySelector('.perfil-botoes .sair');

// Evento de logout ao clicar no botão "Sair"
btnSair.addEventListener('click', () => {
    // Remove informações de login do localStorage
    localStorage.removeItem('isLoggedIn');
    localStorage.removeItem('userImage');
    localStorage.removeItem('token');
    localStorage.removeItem('loginExpiracao');

    // Redireciona para a página inicial
    window.location.href = 'index.html';
});

// ---------------- FORMATAR TELEFONE ----------------

/**
 * Formata um número de telefone (11 dígitos) para o padrão:
 * (XX) XXXXX-XXXX
 */
function formatarTelefone(telefone) {
    // Se não tiver 11 dígitos, retorna como veio
    if (!telefone || telefone.length !== 11) return telefone;

    return `(${telefone.substring(0,2)}) ${telefone.substring(2,7)}-${telefone.substring(7)}`;
}

// ---------------- CARREGAR PERFIL ----------------

/**
 * Busca os dados do perfil no backend e preenche a interface.
 */
async function carregarPerfil() {
    const token = localStorage.getItem('token'); // Obtém token salvo após o login

    // Se não houver token, significa que não está logado
    if (!token) {
        alert("Você precisa estar logado!");
        window.location.href = "index.html";
        return;
    }

    try {
        // Requisição ao backend para obter dados do perfil do usuário
        const response = await api.get("/api/perfil", {
            headers: {
                Authorization: `Bearer ${token}` // Envia o token para validação
            }
        });

        const perfil = response.data; // Dados retornados da API

        // -------- Preenchendo o DOM com os dados recebidos --------

        // Nome
        document.querySelector('.perfil-info .perfil-dado .valor').textContent = perfil.nome;

        // Email (segundo .valor encontrado)
        document.querySelectorAll('.perfil-info .perfil-dado .valor')[1].textContent = perfil.email;

        // Telefone (com formatação)
        document.querySelector('#vl').textContent = formatarTelefone(perfil.telefone);

    } catch (error) {
        console.error("Erro ao carregar perfil:", error);
        alert("Erro ao carregar perfil. Faça login novamente.");

        // Se der erro na requisição, remove dados do login e faz logout
        localStorage.removeItem('isLoggedIn');
        localStorage.removeItem('userImage');
        localStorage.removeItem('token');
        localStorage.removeItem('loginExpiracao');

        // Redireciona
        window.location.href = "index.html";
    }
}

// Executa a função assim que o DOM estiver carregado
window.addEventListener('DOMContentLoaded', carregarPerfil);
