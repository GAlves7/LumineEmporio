package com.api.lumine_emporio.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.api.lumine_emporio.entity.ProdutoEntity;
import com.api.lumine_emporio.exception.NaoEncontradoException;
import com.api.lumine_emporio.repository.ProdutoRepository;

import jakarta.transaction.Transactional;

@Service
public class ProdutoService {
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Transactional
	public ProdutoEntity save(ProdutoEntity produtoEntity) {
		return produtoRepository.save(produtoEntity);
	}
	
	public Page<ProdutoEntity> findAll(Pageable pageable){
		return produtoRepository.findAll(pageable);
	}
	
	public ProdutoEntity findById(Long id) {
		return produtoRepository.findById(id).orElseThrow(() -> new NaoEncontradoException("Produto com ID: "+id+" não encontrado."));
	}
	
	public Page<ProdutoEntity> findByNomeOrDescricao(String q, Pageable pageable){
		return produtoRepository.findByNomeOrDescricao(q, pageable);
	}
	
	public List<ProdutoEntity> findAll(){
		return produtoRepository.findAll();
	}
}
