package com.api.lumine_emporio.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.lumine_emporio.entity.ProdutoEntity;
import com.api.lumine_emporio.entity.ProdutoVariacaoEntity;
import com.api.lumine_emporio.service.ProdutoService;
import com.api.lumine_emporio.service.ProdutoVariacaoService;


@RestController
@RequestMapping("/catalogo")
public class CatalogoController {
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private ProdutoVariacaoService produtoVariacaoService;
	
	@GetMapping
	public List<ProdutoEntity> listarProdutos(){
		return produtoService.findAll();
	}
	
	@GetMapping("/promocao")
	public List<ProdutoVariacaoEntity> listaPromocao(){
		return produtoVariacaoService.findByPromocaoIsNotNull();
	}
	
	@GetMapping("/produto/{id}")
	public ResponseEntity<Object> buscarProduto(@PathVariable Long id){
		return ResponseEntity.ok(produtoService.findById(id));
	}
	
	@GetMapping("/produtoVar/{id}")
	public ResponseEntity<Object> buscarVariacaoProduto(@PathVariable Long id){
		return ResponseEntity.ok(produtoVariacaoService.findById(id));
	}
	
	@GetMapping("/pesquisa")
	public List<ProdutoEntity> pesquisarProduto(@RequestParam String q){
		return produtoService.findByNomeOrDescricao(q);
	}
}
