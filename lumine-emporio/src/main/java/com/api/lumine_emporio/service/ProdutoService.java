package com.api.lumine_emporio.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.api.lumine_emporio.entity.CategoriaEntity;
import com.api.lumine_emporio.entity.ProdutoEntity;
import com.api.lumine_emporio.exception.NaoEncontradoException;
import com.api.lumine_emporio.repository.ProdutoRepository;

import jakarta.transaction.Transactional;

@Service
public class ProdutoService {
	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	private CategoriaService categoriaService;
	
	@Transactional
	public ProdutoEntity save(ProdutoEntity produtoEntity) {
		return produtoRepository.save(produtoEntity);
	}
	
	
	public Page<ProdutoEntity> findAll(List<Long> categoriasIds, Pageable pageable) {

	    // Sem filtro
	    if (categoriasIds == null || categoriasIds.isEmpty()) {
	        return produtoRepository.findAll(pageable);
	    }

	    // Busca as categorias existentes
	    List<CategoriaEntity> categorias = categoriaService.findAllById(categoriasIds);

	    if (categorias.isEmpty()) {
	        return Page.empty(pageable);
	    }

	    return produtoRepository.findByCategoriasIn(categorias, pageable);
	}
	
	
	
	public ProdutoEntity findById(Long id) {
		return produtoRepository.findById(id).orElseThrow(() -> new NaoEncontradoException("Produto com ID: "+id+" não encontrado."));
	}
	
	public Page<ProdutoEntity> pesquisar(List<Long> categoriasIds, String q, Pageable pageable) {

	    boolean temCategorias = categoriasIds != null && !categoriasIds.isEmpty();
	    boolean temTexto = q != null && !q.trim().isEmpty();

	    // Normalizar o texto da pesquisa
	    String texto = "%" + q.toLowerCase() + "%";

	    // Caso tenha categorias
	    if (temCategorias) {

	        List<CategoriaEntity> categorias = categoriaService.findAllById(categoriasIds);

	        if (categorias.isEmpty()) {
	            return Page.empty(pageable);
	        }

	        return produtoRepository.findByCategoriasInAndTexto(categorias, texto, pageable);
	    }

	    // Apenas texto
	    return produtoRepository.findByTexto(texto, pageable);
	}
	
	public List<ProdutoEntity> findAll(){
		return produtoRepository.findAll();
	}
}
