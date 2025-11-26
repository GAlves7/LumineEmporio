package com.api.lumine_emporio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.lumine_emporio.entity.ItemReservaEntity;

@Repository
public interface ItemReservaRepository extends JpaRepository<ItemReservaEntity, Long>{
	
}
