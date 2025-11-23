package com.api.lumine_emporio.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.lumine_emporio.entity.ReservaEntity;
import com.api.lumine_emporio.entity.enums.StatusReserva;
import com.api.lumine_emporio.entity.UsuarioEntity;


@Repository
public interface ReservarRepository extends JpaRepository<ReservaEntity, Long>{
	
	List<ReservaEntity> findByUsuarioEntity(UsuarioEntity usuarioEntity);
}
