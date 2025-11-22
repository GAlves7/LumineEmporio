/* ================== GERAR CARDS DE EXEMPLO ================== */
const container = document.createElement("div");
container.classList.add("historico-container");
container.innerHTML = `
    <h2>Histórico de Pedidos</h2>
    <div class="historico-cards" id="cardsHistorico"></div>
`;
document.body.insertBefore(container, document.getElementById("footerArea"));

const cardsWrapper = document.getElementById("cardsHistorico");

// Criar 10 cards de exemplo (vazios)
for (let i = 1; i <= 6; i++) {
    const card = document.createElement("div");
    card.classList.add("card-historico");
    card.innerHTML = `
        <img src="img/userPerfil/userNovo.png" alt="Produto ${i}">
        <h3>Produto ${i}</h3>
        <p>R$ 0,00</p>
    `;
    cardsWrapper.appendChild(card);
}
