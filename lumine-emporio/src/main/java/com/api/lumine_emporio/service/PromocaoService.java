package com.api.lumine_emporio.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.lumine_emporio.entity.PromocaoEntity;
import com.api.lumine_emporio.repository.PromocaoRepository;


@Service
public class PromocaoService {
	@Autowired
	private PromocaoRepository promocaoRepository;
	
	public PromocaoEntity save(PromocaoEntity promocaoEntity) {
		promocaoEntity.getProdutosVariacao().forEach(variacao -> {
			variacao.setPromocao(promocaoEntity);
		});
		return promocaoRepository.save(promocaoEntity);
	}
	
	
}
