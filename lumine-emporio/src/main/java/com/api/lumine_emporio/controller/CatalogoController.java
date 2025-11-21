package com.api.lumine_emporio.controller;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.api.lumine_emporio.entity.ImagemProdutoEntity;
import com.api.lumine_emporio.entity.ProdutoEntity;
import com.api.lumine_emporio.entity.ProdutoVariacaoEntity;
import com.api.lumine_emporio.service.ImagemProdutoService;
import com.api.lumine_emporio.service.ProdutoService;
import com.api.lumine_emporio.service.ProdutoVariacaoService;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;


@RestController
@RequestMapping("/catalogo")
public class CatalogoController {
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private ProdutoVariacaoService produtoVariacaoService;
	
	@Autowired
	private ImagemProdutoService imagemProdutoService;
	
	@GetMapping
	public Page<ProdutoEntity> listarProdutos( @RequestParam(value = "page", defaultValue = "0") int page,
										@RequestParam(value = "pageSize", defaultValue = "10") int pageSize){
		
		return produtoService.findAll(PageRequest.of(page, pageSize));
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
	public Page<ProdutoEntity> pesquisarProduto(@RequestParam String q, @RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "pageSize", defaultValue = "10") int pageSize){
		
		return produtoService.findByNomeOrDescricao(q, PageRequest.of(page, pageSize));
	}
	
	@GetMapping("/produto/{id}/imagens")
	public ResponseEntity<Object> listImagemEntity(@PathVariable Long id){
		List<ImagemProdutoEntity> imagens = imagemProdutoService.findAllByProduto(produtoService.findById(id));
		return ResponseEntity.ok(imagens);
	}
	
	@GetMapping("/imagem/{id}")
	public ResponseEntity<Object> getImagemArquivo(@PathVariable Long id){
		ImagemProdutoEntity imagem = imagemProdutoService.findById(id);
		
		Path caminho = Paths.get(imagem.getUrl());
		try {
			Resource recurso = new UrlResource(caminho.toUri());
			
			if (!recurso.exists()) return ResponseEntity.notFound().build();
			
			return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM)
				.header(org.springframework.http.HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"")
				.body(recurso);
		} catch (MalformedURLException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(e.getMessage());
		}
	}
	
	
	
	
	
	
}
