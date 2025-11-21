package com.api.lumine_emporio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.lumine_emporio.entity.MarcaEntity;
import com.api.lumine_emporio.repository.MarcaRepository;

import jakarta.transaction.Transactional;

@Service
public class MarcaService {
	@Autowired
	private MarcaRepository marcaRepository;
	
	@Transactional
	public MarcaEntity save(MarcaEntity marcaEntity) {
		return marcaRepository.save(marcaEntity);
	}
}
