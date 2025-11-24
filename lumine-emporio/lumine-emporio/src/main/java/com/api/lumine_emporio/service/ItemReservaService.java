package com.api.lumine_emporio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.lumine_emporio.entity.ItemReservaEntity;
import com.api.lumine_emporio.repository.ItemReservaRepository;

import jakarta.transaction.Transactional;

@Service
public class ItemReservaService {
	@Autowired
	private ItemReservaRepository itemReservaRepository;
	
	@Transactional
	public ItemReservaEntity save(ItemReservaEntity itemReservaEntity) {
		return itemReservaRepository.save(itemReservaEntity);
	}
}
