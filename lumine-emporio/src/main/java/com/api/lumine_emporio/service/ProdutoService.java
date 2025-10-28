package com.api.lumine_emporio.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.lumine_emporio.entity.ProdutoEntity;
import com.api.lumine_emporio.repository.ProdutoRepository;

@Service
public class ProdutoService {
	@Autowired
	private ProdutoRepository produtoRepository;
	
	
	public ProdutoEntity save(ProdutoEntity produtoEntity) {
		return produtoRepository.save(produtoEntity);
	}
	
	public List<ProdutoEntity> findAll(){
		return produtoRepository.findAll();
	}
	
	public ProdutoEntity findById(Long id) {
		return produtoRepository.findById(id).orElseThrow(() -> new RuntimeException("produto nao encontrado"));
	}
	
	public List<ProdutoEntity> findByNomeOrDescricao(String q){
		return produtoRepository.findByNomeOrDescricao(q);
	}
}
