package com.api.lumine_emporio.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.api.lumine_emporio.entity.ItemCarrinhoEntity;
import com.api.lumine_emporio.entity.ProdutoVariacaoEntity;
import com.api.lumine_emporio.entity.UsuarioEntity;
import com.api.lumine_emporio.repository.ItemCarrinhoRepository;
import com.api.lumine_emporio.repository.ProdutoVariacaoRepository;
import com.api.lumine_emporio.repository.UsuarioRepository;

@Service
public class CarrinhoService {
	
	@Autowired
	private ItemCarrinhoRepository itemCarrinhoRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private ProdutoVariacaoRepository produtoVariacaoRepository;
	
	public ItemCarrinhoEntity adicionarAoCarrinho(UUID idUsuario, Long idProdutoVariacao, int quantidade) {
		Optional<UsuarioEntity> usuario = usuarioRepository.findById(idUsuario);
		if (!usuario.isPresent()) {
			throw new RuntimeException("Usuário não encontrado");
		}
		
		Optional<ProdutoVariacaoEntity> produtoVariacao = produtoVariacaoRepository.findById(idProdutoVariacao);
		if (!produtoVariacao.isPresent()) {
			throw new RuntimeException("Produto não encontrado");
		}
		
		if (produtoVariacao.get().getEstoque() < quantidade) {
			throw new RuntimeException("Estoque insuficiente. Disponível: " + produtoVariacao.get().getEstoque());
		}
		
		ItemCarrinhoEntity item = new ItemCarrinhoEntity(quantidade, usuario.get(), produtoVariacao.get());
		return itemCarrinhoRepository.save(item);
	}
	
	public void removerDoCarrinho(UUID idUsuario, Long idItemCarrinho) {
		Optional<UsuarioEntity> usuario = usuarioRepository.findById(idUsuario);
		if (!usuario.isPresent()) {
			throw new RuntimeException("Usuário não encontrado");
		}
		
		Optional<ItemCarrinhoEntity> item = itemCarrinhoRepository.findByUsuarioAndIdItemCarrinho(usuario.get(), idItemCarrinho);
		if (!item.isPresent()) {
			throw new RuntimeException("Item não encontrado no carrinho");
		}
		
		itemCarrinhoRepository.delete(item.get());
	}
	
	public ItemCarrinhoEntity atualizarQuantidade(UUID idUsuario, Long idItemCarrinho, int novaQuantidade) {
		Optional<UsuarioEntity> usuario = usuarioRepository.findById(idUsuario);
		if (!usuario.isPresent()) {
			throw new RuntimeException("Usuário não encontrado");
		}
		
		Optional<ItemCarrinhoEntity> item = itemCarrinhoRepository.findByUsuarioAndIdItemCarrinho(usuario.get(), idItemCarrinho);
		if (!item.isPresent()) {
			throw new RuntimeException("Item não encontrado no carrinho");
		}
		
		if (novaQuantidade <= 0) {
			throw new RuntimeException("Quantidade deve ser maior que zero");
		}
		
		if (item.get().getProdutoVariacao().getEstoque() < novaQuantidade) {
			throw new RuntimeException("Estoque insuficiente. Disponível: " + item.get().getProdutoVariacao().getEstoque());
		}
		
		item.get().setQuantidade(novaQuantidade);
		return itemCarrinhoRepository.save(item.get());
	}
	
	public List<ItemCarrinhoEntity> listarCarrinho(UUID idUsuario) {
		Optional<UsuarioEntity> usuario = usuarioRepository.findById(idUsuario);
		if (!usuario.isPresent()) {
			throw new RuntimeException("Usuário não encontrado");
		}
		
		return itemCarrinhoRepository.findByUsuario(usuario.get());
	}
	
	public double calcularTotal(UUID idUsuario) {
		List<ItemCarrinhoEntity> itens = listarCarrinho(idUsuario);
		double total = 0;
		for (ItemCarrinhoEntity item : itens) {
			double preco = item.getProdutoVariacao().getPreco();
			int quantidade = item.getQuantidade();
			total += preco * quantidade;
		}
		return total;
	}
	
	public void limparCarrinho(UUID idUsuario) {
		Optional<UsuarioEntity> usuario = usuarioRepository.findById(idUsuario);
		if (!usuario.isPresent()) {
			throw new RuntimeException("Usuário não encontrado");
		}
		
		itemCarrinhoRepository.deleteByUsuario(usuario.get());
	}
}