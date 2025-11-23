package com.api.lumine_emporio.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;
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
import com.api.lumine_emporio.config.TokenConfig;
import com.api.lumine_emporio.dtos.CategoriaDTO;
import com.api.lumine_emporio.dtos.CorDTO;
import com.api.lumine_emporio.dtos.MarcaDTO;
import com.api.lumine_emporio.dtos.ProdutoDTO;
import com.api.lumine_emporio.dtos.ProdutoVariacaoDTO;
import com.api.lumine_emporio.dtos.PromocaoDTO;
import com.api.lumine_emporio.entity.CategoriaEntity;
import com.api.lumine_emporio.entity.CorEntity;
import com.api.lumine_emporio.entity.ImagemProdutoEntity;
import com.api.lumine_emporio.entity.MarcaEntity;
import com.api.lumine_emporio.entity.ProdutoEntity;
import com.api.lumine_emporio.entity.ProdutoVariacaoEntity;
import com.api.lumine_emporio.entity.PromocaoEntity;
import com.api.lumine_emporio.service.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
    private CategoriaService categoriaService;
    @Autowired
	private ImagemProdutoService imagemProdutoService;
	@Autowired
	private ProdutoService produtoService;
	@Autowired
	private ProdutoVariacaoService produtoVariacaoService;
	@Autowired
	private PromocaoService promocaoService;
	@Autowired
	private CorService corService;
	@Autowired
	private MarcaService marcaService;

	
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
		produtoVariacaoEntity.setTamanho(produtoVariacaoDTO.tamanho());
		if (produtoVariacaoDTO.idCores() != null) produtoVariacaoDTO.idCores().forEach(idCor -> {
			produtoVariacaoEntity.addCor(corService.findById(idCor));
		});

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
		if (arquivo.isEmpty()) return ResponseEntity.badRequest().body("Arquivo vazio.");
		Random random = new Random();
		String url = imagemProdutoService.getCaminho(idProduto, random.nextInt(100000), arquivo.getOriginalFilename());

		ImagemProdutoEntity imagem = new ImagemProdutoEntity(url, produtoService.findById(idProduto));
		Path caminho = Paths.get(imagem.getUrl());

		try {
			Files.createDirectories(caminho.getParent()); 
			Files.write(caminho, arquivo.getBytes());

			return ResponseEntity.status(HttpStatus.CREATED).body(imagemProdutoService.save(imagem));

		} catch (IOException e) {
			System.out.println("Erro ao salvar arquivo da imagem do produto;" + e.getMessage());
			try {
				Files.deleteIfExists(caminho);
			} catch (IOException ex) {
				System.out.println("Falha ao excluir arquivo após erro de upload;" + e.getMessage());
				ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
			}
			
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage()); 
		} 
	}

	@PostMapping("/categoria")
	public ResponseEntity<Object> saveCategoria(@RequestBody @Valid CategoriaDTO categoriaDTO) {
		CategoriaEntity categoria = new CategoriaEntity(categoriaDTO.nome(), categoriaDTO.descricao());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(categoriaService.save(categoria));
	}

	
	@PostMapping("/marca")
	public ResponseEntity<Object> addMarca(@Valid @RequestBody MarcaDTO marcaDTO){
		var marcaEntity = new MarcaEntity();
		BeanUtils.copyProperties(marcaDTO, marcaEntity);
		marcaService.save(marcaEntity);
		return ResponseEntity.ok(marcaEntity);
	}







}
