package com.api.lumine_emporio.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.api.lumine_emporio.dtos.ItemCarrinhoDTO;
import com.api.lumine_emporio.entity.ItemCarrinhoEntity;
import com.api.lumine_emporio.entity.ProdutoVariacaoEntity;
import com.api.lumine_emporio.entity.UsuarioEntity;
import com.api.lumine_emporio.repository.ItemCarrinhoRepository;

import jakarta.transaction.Transactional;

@Service
public class ItemCarrinhoService {

	@Autowired
	private ItemCarrinhoRepository itemCarrinhoRepository;
   
	
	@Transactional
	public ItemCarrinhoEntity save(ItemCarrinhoEntity itemCarrinhoEntity) {
		return itemCarrinhoRepository.save(itemCarrinhoEntity);
	}
	
	
	public Page<ItemCarrinhoDTO> findAllByUsuarioPage(UsuarioEntity usuarioEntity, Pageable pageable){
		Page<ItemCarrinhoEntity> page = itemCarrinhoRepository.findAllByUsuario(usuarioEntity, pageable);
		return page.map(item -> new ItemCarrinhoDTO(
				item.getProdutoVariacao().getProduto().getNome(),
				item.getProdutoVariacao().getIdProdutoVar(),
				item.getProdutoVariacao().getProduto().getIdProduto(),
				item.getProdutoVariacao().getTamanho(),
				item.getProdutoVariacao().getPreco(),
				item.getQuantidade()
				));
	}
	
	public List<ItemCarrinhoEntity> findAllByUsuario(UsuarioEntity usuarioEntity){
		return itemCarrinhoRepository.findAllByUsuario(usuarioEntity);
	}
	
	
	
	
	@Transactional
	public void addCarrinho(ProdutoVariacaoEntity produtoVar, UsuarioEntity usuarioEntity, int quantidade) {
		ItemCarrinhoEntity itemCarrinhoEntity = new ItemCarrinhoEntity();
		
		if(itemCarrinhoRepository.existsByUsuarioAndProdutoVariacao(usuarioEntity, produtoVar) && quantidade > 0) {
			itemCarrinhoEntity = itemCarrinhoRepository.findByUsuarioAndProdutoVariacao(usuarioEntity, produtoVar);
			itemCarrinhoEntity.setQuantidade(quantidade);
		}
		else {
			itemCarrinhoEntity.setUsuario(usuarioEntity);
			itemCarrinhoEntity.setProdutoVariacao(produtoVar);
			itemCarrinhoEntity.setQuantidade(quantidade);
		}
		try {
			itemCarrinhoRepository.save(itemCarrinhoEntity);
			System.out.println("Sucesso ao salvar carrinho.\n");
		}
		catch (Exception e) {
			System.out.println("Falha ao salvar carrinho.\n");
			throw new RuntimeException("Falha ao salvar carrinho.");
		}
	}
	
	@Transactional
	public void removeCarrinho(ProdutoVariacaoEntity produtoVar, UsuarioEntity usuarioEntity) {
		
		try {
			ItemCarrinhoEntity itemCarrinhoEntity = itemCarrinhoRepository.findByUsuarioAndProdutoVariacao(usuarioEntity, produtoVar);
			itemCarrinhoRepository.delete(itemCarrinhoEntity);
		}
		catch(Exception e) {
			throw new RuntimeException("Erro ao deletar item do Carrinho: "+e.getMessage());
		}
		
	}
	
	@Transactional
	public void deleteAll(List<ItemCarrinhoEntity> carrinhosEntity) {
		if(carrinhosEntity.isEmpty()) throw new RuntimeException("Falha ao deletar carrinho, lista vazia");
		
		itemCarrinhoRepository.deleteAll(carrinhosEntity);
	}
	
	
	
	
	
	
	
	
	
	
}
