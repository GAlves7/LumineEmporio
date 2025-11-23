package com.api.lumine_emporio.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.lumine_emporio.dtos.ItemCarrinhoDTO;
import com.api.lumine_emporio.entity.ItemCarrinhoEntity;
import com.api.lumine_emporio.entity.UsuarioEntity;
import com.api.lumine_emporio.service.ItemCarrinhoService;
import com.api.lumine_emporio.service.ProdutoVariacaoService;

@RestController
@RequestMapping("/reserva")
public class ReservaController {

	@Autowired
	private ProdutoVariacaoService produtoVariacaoService;
	@Autowired
	private ItemCarrinhoService itemCarrinhoService;
	
	
	@GetMapping("/carrinho")
	public Page<ItemCarrinhoDTO> listCarrinho(
	        @RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "10") int pageSize,
	        @AuthenticationPrincipal UsuarioEntity usuarioEntity
	) {
		System.out.println(usuarioEntity);
	    return itemCarrinhoService.findAllByUsuario(usuarioEntity,  PageRequest.of(page, pageSize));
	}
	
	@PutMapping("/carrinho-add")
	public ResponseEntity<String> addCarrinho(
			@AuthenticationPrincipal UsuarioEntity usuarioEntity, 
			@RequestParam int quantidade, 
			@RequestParam Long idProdutoVar){
		
		System.out.println(usuarioEntity);
		itemCarrinhoService.addCarrinho(produtoVariacaoService.findById(idProdutoVar), usuarioEntity, quantidade);
		return ResponseEntity.ok("ProdutoVar de id: "+idProdutoVar+" adicionado ao carrinho.");
		
	}
	
	@DeleteMapping("/carrinho-delete")
	public ResponseEntity<String> deleteCarrinho(@AuthenticationPrincipal UsuarioEntity usuarioEntity, @RequestParam Long idProdutoVar){
		
		System.out.println(usuarioEntity);
		itemCarrinhoService.removeCarrinho(produtoVariacaoService.findById(idProdutoVar), usuarioEntity);
		return ResponseEntity.ok("ProdutoVar de id: "+idProdutoVar+" removido do carrinho.");
		
	}
	
	
	
	
}
