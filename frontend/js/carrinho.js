/* ================== GERAR CARDS DE EXEMPLO DO CARRINHO ================== */
const container = document.createElement("div");
container.classList.add("carrinho-container");
container.innerHTML = `
    <h2>Carrinho</h2>
    <div class="carrinho-cards" id="cardsCarrinho"></div>
`;
document.body.insertBefore(container, document.getElementById("footerArea"));

const cardsWrapper = document.getElementById("cardsCarrinho");

// Criar 2 cards de exemplo (horizontal, empilhando verticalmente)
for (let i = 1; i <= 2; i++) {
    const card = document.createElement("div");
    card.classList.add("card-carrinho");
    card.innerHTML = `
        <div class="card-img-placeholder"></div>
        <div class="card-info">
            <h3>Produto ${i}</h3>
            <p>R$ 0,00</p>
        </div>
    `;
    cardsWrapper.appendChild(card);
}
