package com.api.lumine_emporio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.lumine_emporio.entity.CorEntity;
import com.api.lumine_emporio.repository.CorRepository;

@Service
public class CorService {
	@Autowired
	private CorRepository corRepository;
	
	public CorEntity save(CorEntity corEntity) {
		if(corRepository.existsByCodigoHex(corEntity.getCodigoHex())) throw new RuntimeException("Cor ja existe");
		return corRepository.save(corEntity);
	}
}
