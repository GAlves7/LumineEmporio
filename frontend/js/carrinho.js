import api from './api.js'; // Axios já configurado com baseURL

// ================== GERAR CONTAINER PRINCIPAL ==================
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

// ================== FUNÇÃO PARA LISTAR PRODUTOS ==================
async function listarCarrinho() {
    try {
        const token = localStorage.getItem('token');
        const config = { headers: { Authorization: `Bearer ${token}` } };

        const carrinhoRes = await api.get('/reserva/carrinho', config);
        const produtosCarrinho = carrinhoRes.data.content;

        cardsWrapper.innerHTML = '';
        totalWrapper.innerHTML = '';

        if (produtosCarrinho.length === 0) {
            cardsWrapper.innerHTML = `<p style="color:#fff;">Seu carrinho está vazio.</p>`;
            return;
        }

        let total = 0;

        for (let produto of produtosCarrinho) {
            const detalheRes = await api.get(`/catalogo/produto/${produto.idProduto}`, config);
            const detalhe = detalheRes.data;

            let imagemURL = '';
            if (detalhe.imagemProduto?.length > 0) {
                const idImagemProd = detalhe.imagemProduto[0].idImagemProd;
                imagemURL = `${api.defaults.baseURL}/catalogo/imagem/${idImagemProd}`;
            }

            const subtotal = produto.preco * produto.quantidade;
            total += subtotal;

            const card = document.createElement('div');
            card.classList.add('card-carrinho');
            card.innerHTML = `
                <div class="card-img-placeholder">
                    ${imagemURL ? `<img src="${imagemURL}" alt="${produto.nomeProdutoVar}" style="width:100%; height:100%; object-fit:cover; border-radius:10px;">` : ''}
                </div>
                <div class="card-info">
                    <h3>${produto.nomeProdutoVar}</h3>
                    <p>Tamanho: ${produto.tamanho}</p>
                    <p class="preco-unitario">Preço: R$ ${formatarPreco(produto.preco)}</p>
                    <div class="qtd-wrapper">
                        <button class="btn-qtd" data-id="${produto.idProdutoVar}" data-action="decrement">−</button>
                        <span class="qtd-num">${produto.quantidade}</span>
                        <button class="btn-qtd" data-id="${produto.idProdutoVar}" data-action="increment">+</button>
                    </div>
                    <p>Subtotal: R$ <span class="subtotal">${formatarPreco(subtotal)}</span></p>
                    <button class="btn-remover" data-id="${produto.idProdutoVar}" style="background-color:red; color:white; border:none; padding:5px 10px; border-radius:5px; cursor:pointer;">Remover</button>
                </div>
            `;
            cardsWrapper.appendChild(card);
        }

        totalWrapper.innerHTML = `<h3>Total: R$ ${formatarPreco(total)}</h3>`;

        // ================== EVENTO INCREMENT/DECREMENT ==================
        document.querySelectorAll('.btn-qtd').forEach(btn => {
            btn.addEventListener('click', async () => {
                const idProdutoVar = btn.getAttribute('data-id');
                const action = btn.getAttribute('data-action');
                const qtdSpan = btn.parentElement.querySelector('.qtd-num');
                let novaQtd = parseInt(qtdSpan.textContent);

                if (action === 'increment') novaQtd++;
                else if (action === 'decrement' && novaQtd > 1) novaQtd--;

                try {
                    // O backend substitui a quantidade, então enviamos a nova
                    const formData = new FormData();
                    formData.append('idProdutoVar', idProdutoVar);
                    formData.append('quantidade', novaQtd);

                    await api.put('/reserva/carrinho-add', formData, config);

                    // Atualiza UI
                    qtdSpan.textContent = novaQtd;
                    const precoUnit = parseFloat(btn.closest('.card-info')
                        .querySelector('.preco-unitario')
                        .textContent.split('R$ ')[1].replace(',', '.'));

                    btn.closest('.card-info').querySelector('.subtotal').textContent = formatarPreco(precoUnit * novaQtd);

                    // Atualiza total
                    let newTotal = 0;
                    document.querySelectorAll('.card-carrinho').forEach(c => {
                        const s = parseFloat(c.querySelector('.subtotal').textContent.replace(',', '.'));
                        newTotal += s;
                    });
                    totalWrapper.innerHTML = `<h3>Total: R$ ${formatarPreco(newTotal)}</h3>`;

                } catch (err) {
                    console.error('Erro ao atualizar quantidade:', err);
                    alert('Não foi possível atualizar a quantidade.');
                }
            });
        });

        // ================== EVENTO REMOVER ==================
        document.querySelectorAll('.btn-remover').forEach(btn => {
            btn.addEventListener('click', async () => {
                const idProdutoVar = btn.getAttribute('data-id');
                try {
                    const formData = new FormData();
                    formData.append('idProdutoVar', idProdutoVar);

                    await api.delete('/reserva/carrinho-delete', {
                        data: formData,
                        headers: { Authorization: `Bearer ${localStorage.getItem('token')}` }
                    });

                    // Recarrega para atualizar o carrinho
                    window.location.reload();

                } catch (err) {
                    console.error('Erro ao remover produto:', err);
                    alert('Não foi possível remover o produto.');
                }
            });
        });

    } catch (err) {
        console.error('Erro ao listar carrinho:', err);
        cardsWrapper.innerHTML = `<p style="color:#fff;">Erro ao carregar o carrinho.</p>`;
    }
}

// ================== EXECUTAR ==================
listarCarrinho();
