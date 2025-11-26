package com.api.lumine_emporio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.lumine_emporio.entity.CorEntity;
import com.api.lumine_emporio.exception.NaoEncontradoException;
import com.api.lumine_emporio.repository.CorRepository;

import jakarta.transaction.Transactional;

@Service
public class CorService {
	@Autowired
	private CorRepository corRepository;
	
	@Transactional
	public CorEntity save(CorEntity corEntity) {
		if(corRepository.existsByCodigoHex(corEntity.getCodigoHex())) throw new RuntimeException("Cor ja existe");
		return corRepository.save(corEntity);
	}
	
	public CorEntity findById(Long id) {
		return corRepository.findById(id).orElseThrow(() -> new NaoEncontradoException("Cor com ID: "+id+" não encontrado."));
	}
}
