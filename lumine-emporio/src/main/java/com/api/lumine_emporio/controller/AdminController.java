package com.api.lumine_emporio.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.api.lumine_emporio.dtos.CorDTO;
import com.api.lumine_emporio.dtos.ProdutoDTO;
import com.api.lumine_emporio.dtos.ProdutoVariacaoDTO;
import com.api.lumine_emporio.dtos.PromocaoDTO;
import com.api.lumine_emporio.entity.CorEntity;
import com.api.lumine_emporio.entity.ImagemProdutoEntity;
import com.api.lumine_emporio.entity.ProdutoEntity;
import com.api.lumine_emporio.entity.ProdutoVariacaoEntity;
import com.api.lumine_emporio.entity.PromocaoEntity;
import com.api.lumine_emporio.entity.enums.Tamanho;
import com.api.lumine_emporio.service.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final ImagemProdutoService imagemProdutoService;
	@Autowired
	private ProdutoService produtoService;
	@Autowired
	private ProdutoVariacaoService produtoVariacaoService;
	@Autowired
	private PromocaoService promocaoService;
	@Autowired
	private CorService corService;

    AdminController(ImagemProdutoService imagemProdutoService) {
        this.imagemProdutoService = imagemProdutoService;
    }
	
	@PostMapping("/produto")
	public ResponseEntity<Object> salvarPorduto(@RequestBody @Valid ProdutoDTO produtoDTO) {
		var produtoEntity = new ProdutoEntity();
		BeanUtils.copyProperties(produtoDTO, produtoEntity);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(produtoService.save(produtoEntity));
	}
	
	@PostMapping("/promocao")
	public ResponseEntity<Object> salvarPromocao(@RequestBody @Valid PromocaoDTO promocaoDTO){
		PromocaoEntity promocaoEntity = new PromocaoEntity();
		promocaoEntity.setDesconto(promocaoDTO.desconto());
		promocaoEntity.setProdutosVariacao(Set.copyOf(produtoVariacaoService.findAllById(promocaoDTO.produtosVariacao())));
		return ResponseEntity.status(HttpStatus.CREATED).body(promocaoService.save(promocaoEntity));
	}
	
	
	  @PostMapping("/produto/{id}/variacao") 
	  public ResponseEntity<Object> salvarProdutoVariacao(@PathVariable(name="id") long idProduto, @RequestBody @Valid ProdutoVariacaoDTO produtoVariacaoDTO){
		  ProdutoVariacaoEntity produtoVariacaoEntity = new ProdutoVariacaoEntity();
		  
		  produtoVariacaoEntity.setDescricao(produtoVariacaoDTO.descricao());
		  produtoVariacaoEntity.setPreco(produtoVariacaoDTO.preco());
		  produtoVariacaoEntity.setProduto(produtoService.findById(idProduto));
		  produtoVariacaoEntity.setEstoque(produtoVariacaoDTO.estoque());
		  produtoVariacaoEntity.setTamanho(Tamanho.getTamanho(produtoVariacaoDTO.tamanho()));
		  
		  return ResponseEntity.status(HttpStatus.CREATED).body(produtoVariacaoService.save(produtoVariacaoEntity));
		  
		  
	  }
	
	@PostMapping("/cor") 
	public CorEntity salvarCor(@RequestBody @Valid CorDTO corDTO) {
		CorEntity corEntity = new CorEntity();
		BeanUtils.copyProperties(corDTO, corEntity);
		
		return corService.save(corEntity);
	}
	
	@PostMapping("/imagem/adicionar")
	public ResponseEntity<Object> adicionarImagem(@RequestParam MultipartFile arquivo, @RequestParam Long idProduto){
		ProdutoEntity produtoEntity = produtoService.findById(idProduto);
		ImagemProdutoEntity imagem = new ImagemProdutoEntity(produtoEntity, imagemProdutoService.pegarProxId());
		
		if (arquivo.isEmpty()) return ResponseEntity.badRequest().body("Arquivo vazio.");
		
		try {
			Path caminho = Paths.get(imagemProdutoService.getCaminho(idProduto, imagem.getIdImagemProd(), arquivo.getContentType()));
			
			Files.createDirectories(caminho.getParent());
			Files.write(caminho, arquivo.getBytes());
			
			return ResponseEntity.ok().body(imagem);
			
		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(null);
		}
	}
	
	
	
	
	
	
	
	
	
	
}
