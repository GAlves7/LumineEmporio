package com.api.lumine_emporio.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.api.lumine_emporio.dtos.ProdutoDTO;
import com.api.lumine_emporio.entity.ImagemProdutoEntity;
import com.api.lumine_emporio.entity.ProdutoEntity;
import com.api.lumine_emporio.entity.ProdutoVariacaoEntity;
import com.api.lumine_emporio.entity.enums.Tamanho;
import com.api.lumine_emporio.service.CategoriaService;
import com.api.lumine_emporio.service.ImagemProdutoService;
import com.api.lumine_emporio.service.MarcaService;
import com.api.lumine_emporio.service.ProdutoService;
import com.api.lumine_emporio.service.ProdutoVariacaoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/teste")
public class TestController {
	@Autowired
	private ProdutoService produtoService;
	@Autowired
	private ProdutoVariacaoService produtoVariacaoService;
	@Autowired
	private MarcaService marcaService;
	@Autowired
    private CategoriaService categoriaService;
	@Autowired
	private ImagemProdutoService imagemProdutoService;
	
	@GetMapping
	public String get() {
		return "Get deu certo";
	}
	
	@PostMapping
	public String post() {
		return "Post deu certo";
	}
	
	@PostMapping("/publica50")
	public ResponseEntity<Object> publica50(){
		
		for(int i=0; i<50; i++) {
			ProdutoEntity produtoEntity = new ProdutoEntity();
			produtoEntity.setNome("Blusa "+i);
			produtoEntity.setPreco(i);
			produtoEntity.setDescricao("desc: "+i);
			produtoService.save(produtoEntity);
			
			ProdutoVariacaoEntity produtoVariacaoEntity = new ProdutoVariacaoEntity();
			produtoVariacaoEntity.setProduto(produtoEntity);
			produtoVariacaoEntity.setEstoque(i * 10);
			produtoVariacaoEntity.setDescricao("Variacao "+i);
			produtoVariacaoEntity.setTamanho(Tamanho.G);
			produtoVariacaoEntity.setPreco(i * 11);
			
			produtoVariacaoService.save(produtoVariacaoEntity);
			
			ProdutoVariacaoEntity produtoVariacaoEntity2 = new ProdutoVariacaoEntity();
			produtoVariacaoEntity2.setProduto(produtoEntity);
			produtoVariacaoEntity2.setEstoque(i * 15);
			produtoVariacaoEntity2.setDescricao("Variacao "+i);
			produtoVariacaoEntity2.setTamanho(Tamanho.PP);
			produtoVariacaoEntity2.setPreco(i * 15);
			
			produtoVariacaoService.save(produtoVariacaoEntity2);
			
		}
		return ResponseEntity.ok(null);
	}
	
	@PostMapping("/imagem/adicionar")
	public ResponseEntity<Object> adicionarImagem(@RequestParam MultipartFile arquivo){
		if (arquivo.isEmpty()) return ResponseEntity.badRequest().body("Arquivo vazio.");
		
		produtoService.findAll().forEach(produtoEntity -> {
			Long idProduto = produtoEntity.getIdProduto();
			
			Random random = new Random();
			String url = imagemProdutoService.getCaminho(idProduto, random.nextInt(100000), arquivo.getOriginalFilename());
		
			ImagemProdutoEntity imagem = new ImagemProdutoEntity(url, produtoService.findById(idProduto));
			Path caminho = Paths.get(imagem.getUrl());
		
			try {
				Files.createDirectories(caminho.getParent()); 
				Files.write(caminho, arquivo.getBytes());
		
				imagemProdutoService.save(imagem);
		
			} catch (IOException e) {
				System.out.println("Erro ao salvar arquivo da imagem do produto;" + e.getMessage());
				try {
					Files.deleteIfExists(caminho);
				} catch (IOException ex) {
					System.out.println("Falha ao excluir arquivo após erro de upload;" + e.getMessage());
					ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
				} 
			}
			
		});
		return ResponseEntity.ok(null);
	}
	
}
