package com.api.lumine_emporio.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.lumine_emporio.entity.ItemCarrinhoEntity;
import com.api.lumine_emporio.entity.ItemReservaEntity;
import com.api.lumine_emporio.entity.ReservaEntity;
import com.api.lumine_emporio.entity.UsuarioEntity;
import com.api.lumine_emporio.entity.enums.StatusReserva;
import com.api.lumine_emporio.repository.ReservarRepository;

import jakarta.transaction.Transactional;

@Service
public class ReservaService {
	@Autowired
	private ReservarRepository reservarRepository;
	@Autowired
	private ItemReservaService itemReservaService;
	@Autowired
	private ItemCarrinhoService itemCarrinhoService;
	
	
	@Transactional
	public ReservaEntity save(ReservaEntity reservaEntity) {
		return reservarRepository.save(reservaEntity);
	}
	
	@Transactional
	public void criarReserva(UsuarioEntity usuarioEntity, List<ItemCarrinhoEntity> itensCarrinho) {
		
		ReservaEntity reservaEntity = new ReservaEntity();
		reservaEntity.setUsuarioEntity(usuarioEntity);
		reservaEntity.setStatus(StatusReserva.P);
		try {
			reservarRepository.save(reservaEntity);
			itensCarrinho.forEach(itemcarrinho ->  
				itemReservaService.save(new ItemReservaEntity(
					itemcarrinho.getQuantidade(),
					itemcarrinho.getProdutoVariacao(),
					reservaEntity)
				));
			itemCarrinhoService.deleteAll(itensCarrinho);
			
			
			
		}
		catch (Exception e) {
			throw new RuntimeException("Falha ao criar reserva: "+e.getMessage());
		}
		
		
		
	}
}
