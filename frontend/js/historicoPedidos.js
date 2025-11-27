/* ============================================================
   GERAÇÃO DE CARDS DE EXEMPLO PARA O HISTÓRICO DE PEDIDOS
   (Essa parte é apenas visual, sem integração real com backend)
   ============================================================ */

// Cria um elemento <div> que será o container geral do histórico
const container = document.createElement("div");

// Adiciona a classe CSS que estiliza o container
container.classList.add("historico-container");

// Insere no container o título e a div que conterá os cards
container.innerHTML = `
    <h2>Histórico de Pedidos</h2>

    <!-- Área onde os cards serão inseridos dinamicamente -->
    <div class="historico-cards" id="cardsHistorico"></div>
`;

// Insere o container ANTES do footer na página
// Isso garante que o histórico apareça acima do rodapé
document.body.insertBefore(container, document.getElementById("footerArea"));

// Obtém a div onde os cards serão adicionados
const cardsWrapper = document.getElementById("cardsHistorico");

/* ============================================================
   GERA 6 CARDS DE EXEMPLO PARA EXIBIR NA TELA
   (Somente placeholders; serão substituídos futuramente
    pelos dados reais da API do backend)
   ============================================================ */

// Criar 6 cards "fake" apenas para compor a interface
for (let i = 1; i <= 6; i++) {

    // Cria o card individual
    const card = document.createElement("div");
    card.classList.add("card-historico");

    // Conteúdo HTML do card
    card.innerHTML = `
        <!-- Imagem padrão (imagem genérica enquanto não há dados reais) -->
        <img src="img/userPerfil/userNovo.png" alt="Produto ${i}">

        <!-- Nome do produto apenas para ilustração -->
        <h3>Produto ${i}</h3>

        <!-- Preço fictício -->
        <p>R$ 0,00</p>
    `;

    // Adiciona o card ao container de cards
    cardsWrapper.appendChild(card);
}
