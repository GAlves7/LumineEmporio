import api from './api.js'; // Axios já configurado com baseURL

// ================== GERAR CONTAINER PRINCIPAL ==================
// Cria o container que mostrará os produtos do carrinho e o total
const container = document.createElement("div");
container.classList.add("carrinho-container");
container.innerHTML = `
    <h2>Carrinho</h2>
    <div class="carrinho-cards" id="cardsCarrinho"></div>
    <div class="carrinho-total" id="carrinhoTotal"></div>
`;
document.body.insertBefore(container, document.getElementById("footerArea"));

const cardsWrapper = document.getElementById("cardsCarrinho");
const totalWrapper = document.getElementById("carrinhoTotal");

// ================== FUNÇÃO PARA FORMATAR PREÇO ==================
function formatarPreco(valor) {
    return valor.toFixed(2).replace('.', ',');
}

// ================== SVG WHATSAPP ==================
// Ícone usado no botão de reserva
const svgZap = `
<svg xmlns="http://www.w3.org/2000/svg" width="22" height="22" fill="#1eff00ff" viewBox="0 0 24 24" style="margin-right:8px;">
    <path d="M20.52 3.48A11.78 11.78 0 0 0 12 0a11.94 11.94 0 0 0-10.4 17.94L0 24l6.26-1.64A12 12 0 0 0 12 24h.01A12 12 0 0 0 24 12a11.76 11.76 0 0 0-3.48-8.52ZM12 22a9.93 9.93 0 0 1-5.09-1.4l-.36-.21-3.72.97 1-3.63-.24-.37A10 10 0 1 1 22 12a9.93 9.93 0 0 1-10 10Zm5.12-7.47c-.28-.14-1.64-.81-1.9-.9s-.44-.14-.63.14-.72.9-.88 1.09-.33.21-.61.07a8.31 8.31 0 0 1-4.1-3.59c-.31-.53.31-.49.88-1.63a.54.54 0 0 0 0-.51c-.07-.14-.63-1.52-.86-2.08s-.46-.48-.63-.49h-.54a1 1 0 0 0-.72.34A3 3 0 0 0 6 8.79a5.25 5.25 0 0 0 1.11 2.76 12.11 12.11 0 0 0 4.86 4.32 16.71 16.71 0 0 0 1.64.61 4 4 0 0 0 1.85.12 3.1 3.1 0 0 0 2-1.44 2.48 2.48 0 0 0 .17-1.44c-.07-.14-.26-.21-.54-.35Z"/>
</svg>
`;

// ================== FUNÇÃO PARA LISTAR PRODUTOS ==================
async function listarCarrinho() {
    try {
        // Recupera o token do usuário logado
        const token = localStorage.getItem('token');
        const config = { headers: { Authorization: `Bearer ${token}` } };

        // Busca produtos do carrinho
        const carrinhoRes = await api.get('/reserva/carrinho', config);
        const produtosCarrinho = carrinhoRes.data.content;

        // Limpa a lista antes de renderizar
        cardsWrapper.innerHTML = '';
        totalWrapper.innerHTML = '';

        // Se o carrinho estiver vazio
        if (produtosCarrinho.length === 0) {
            cardsWrapper.innerHTML = `<p style="color:#fff;">Seu carrinho está vazio.</p>`;
            return;
        }

        let total = 0;

        // Loop para renderizar cada produto
        for (let produto of produtosCarrinho) {

            // Busca detalhes completos do produto
            const detalheRes = await api.get(`/catalogo/produto/${produto.idProduto}`, config);
            const detalhe = detalheRes.data;

            // Monta URL da imagem
            let imagemURL = '';
            if (detalhe.imagemProduto?.length > 0) {
                const idImagemProd = detalhe.imagemProduto[0].idImagemProd;
                imagemURL = `${api.defaults.baseURL}/catalogo/imagem/${idImagemProd}`;
            }

            const subtotal = produto.preco * produto.quantidade;
            total += subtotal;

            // Cria o card do produto
            const card = document.createElement('div');
            card.classList.add('card-carrinho');
            card.innerHTML = `
                <div class="card-img-placeholder">
                    ${imagemURL ? `<img src="${imagemURL}" alt="${produto.nomeProdutoVar}">` : ''}
                </div>

                <div class="card-info">
                    <h3>${produto.nomeProdutoVar}</h3>
                    <p>Tamanho: ${produto.tamanho}</p>
                    <p class="preco-unitario">Preço: R$ ${formatarPreco(produto.preco)}</p>

                    <!-- Controle de quantidade -->
                    <div class="qtd-wrapper">
                        <button class="btn-qtd" data-id="${produto.idProdutoVar}" data-action="decrement">−</button>
                        <span class="qtd-num">${produto.quantidade}</span>
                        <button class="btn-qtd" data-id="${produto.idProdutoVar}" data-action="increment">+</button>
                    </div>

                    <p>Subtotal: R$ <span class="subtotal">${formatarPreco(subtotal)}</span></p>

                    <button class="btn-remover" data-id="${produto.idProdutoVar}">Remover</button>
                </div>
            `;
            cardsWrapper.appendChild(card);
        }

        // RENDERIZA TOTAL + BOTÃO DE RESERVA
        totalWrapper.innerHTML = `
            <h3>Total: R$ ${formatarPreco(total)}</h3>
            <button id="btnReservar" class="btn-reservar">
                ${svgZap} Reservar
            </button>
        `;

        document.getElementById('btnReservar').addEventListener('click', iniciarReserva);

        // ================== EVENTO INCREMENT/DECREMENT ==================
        document.querySelectorAll('.btn-qtd').forEach(btn => {
            btn.addEventListener('click', async () => {
                const idProdutoVar = btn.getAttribute('data-id');
                const action = btn.getAttribute('data-action');

                const qtdSpan = btn.parentElement.querySelector('.qtd-num');
                let novaQtd = parseInt(qtdSpan.textContent);

                // Lógica de aumentar ou diminuir
                if (action === 'increment') novaQtd++;
                else if (action === 'decrement' && novaQtd > 1) novaQtd--;

                try {
                    // Envia atualização ao back-end
                    const formData = new FormData();
                    formData.append('idProdutoVar', idProdutoVar);
                    formData.append('quantidade', novaQtd);

                    await api.put('/reserva/carrinho-add', formData, config);

                    // Atualiza UI
                    qtdSpan.textContent = novaQtd;

                    const precoUnit = parseFloat(
                        btn.closest('.card-info')
                        .querySelector('.preco-unitario')
                        .textContent.split('R$ ')[1]
                        .replace(',', '.')
                    );

                    // Recalcula subtotal
                    btn.closest('.card-info')
                        .querySelector('.subtotal')
                        .textContent = formatarPreco(precoUnit * novaQtd);

                    // Recalcula total geral
                    let newTotal = 0;
                    document.querySelectorAll('.card-carrinho').forEach(c => {
                        const s = parseFloat(c.querySelector('.subtotal').textContent.replace(',', '.'));
                        newTotal += s;
                    });

                    totalWrapper.innerHTML = `
                        <h3>Total: R$ ${formatarPreco(newTotal)}</h3>
                        <button id="btnReservar" class="btn-reservar">
                            ${svgZap} Reservar
                        </button>
                    `;

                    document.getElementById('btnReservar').addEventListener('click', iniciarReserva);

                } catch (err) {
                    console.error('Erro ao atualizar quantidade:', err);
                    alert('Não foi possível atualizar a quantidade.');
                }
            });
        });

        // ================== EVENTO REMOVER PRODUTO ==================
        document.querySelectorAll('.btn-remover').forEach(btn => {
            btn.addEventListener('click', async () => {
                const idProdutoVar = btn.getAttribute('data-id');

                try {
                    const formData = new FormData();
                    formData.append('idProdutoVar', idProdutoVar);

                    // Envia a remoção ao servidor
                    await api.delete('/reserva/carrinho-delete', {
                        data: formData,
                        headers: { Authorization: `Bearer ${localStorage.getItem('token')}` }
                    });

                    // Recarrega a página para atualizar a lista
                    window.location.reload();

                } catch (err) {
                    console.error('Erro ao remover produto:', err);
                    alert('Não foi possível remover o produto.');
                }
            });
        });

    } catch (err) {
        console.error('Erro ao listar carrinho:', err);
        cardsWrapper.innerHTML = `<p style="color:#fff;">Faça o login ou cadastro para acessar seu carrinho!</p>`;
    }
}

// ================== FUNÇÃO PARA INICIAR RESERVA VIA WHATSAPP ==================
async function iniciarReserva() {
    try {
        const token = localStorage.getItem('token');

        // Inicia reserva e recebe link do WhatsApp
        const res = await api.post('/reserva/iniciar-reserva', null, {
            headers: { Authorization: `Bearer ${token}` }
        });

        const linkWhatsapp = res.data;

        if (!linkWhatsapp) {
            alert("Erro: o servidor não retornou o link do WhatsApp");
            return;
        }

        // Abre WhatsApp em nova aba
        window.open(linkWhatsapp, "_blank");

        // Recarrega página após abrir o WhatsApp
        setTimeout(() => {
            window.location.reload();
        }, 500);

    } catch (err) {
        console.error("Erro ao iniciar reserva:", err);
        alert("Não foi possível iniciar a reserva.");
    }
}

// ================== EXECUTAR AO CARREGAR ==================
listarCarrinho();

// Atualiza badge do carrinho no header
import('./header.js').then(() => {
    if (typeof atualizarBadgeCarrinho === "function") {
        atualizarBadgeCarrinho();
    }
});
