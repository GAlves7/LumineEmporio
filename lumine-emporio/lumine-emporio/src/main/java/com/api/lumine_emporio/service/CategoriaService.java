package com.api.lumine_emporio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.lumine_emporio.entity.CategoriaEntity;
import com.api.lumine_emporio.exception.NaoEncontradoException;
import com.api.lumine_emporio.repository.CategoriaRepository;

import jakarta.transaction.Transactional;

@Service
public class CategoriaService {
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Transactional
	public CategoriaEntity save(CategoriaEntity categoriaEntity) {
		return categoriaRepository.save(categoriaEntity);
	}
	
	public CategoriaEntity findById(Long id) {
		return categoriaRepository.findById(id).orElseThrow(() -> new NaoEncontradoException("Categoria com ID: "+id+" não encontrado."));
	}
}
