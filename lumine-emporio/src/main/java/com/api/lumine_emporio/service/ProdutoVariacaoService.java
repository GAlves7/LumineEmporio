package com.api.lumine_emporio.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.lumine_emporio.entity.ProdutoVariacaoEntity;
import com.api.lumine_emporio.repository.ProdutoVariacaoRepository;

@Service
public class ProdutoVariacaoService {
	@Autowired
	private ProdutoVariacaoRepository produtoVariacaoRepository;
	
	
	public List<ProdutoVariacaoEntity> findAllById(List<Long> ids){
		return produtoVariacaoRepository.findAllById(ids);
	}


	public List<ProdutoVariacaoEntity> findByPromocaoIsNotNull() {
		// TODO Auto-generated method stub
		return produtoVariacaoRepository.findByPromocaoIsNotNull();
	}
	
	public ProdutoVariacaoEntity save(ProdutoVariacaoEntity produtoVariacaoEntity) {
		return produtoVariacaoRepository.save(produtoVariacaoEntity);
	}
	public ProdutoVariacaoEntity findById(Long id) {
		return produtoVariacaoRepository.findById(id).orElseThrow(() -> new RuntimeException("produtoVariacao nao encontrado"));
	}
}
