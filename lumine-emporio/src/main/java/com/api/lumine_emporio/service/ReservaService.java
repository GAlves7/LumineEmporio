package com.api.lumine_emporio.service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.api.lumine_emporio.entity.ItemCarrinhoEntity;
import com.api.lumine_emporio.entity.ItemReservaEntity;
import com.api.lumine_emporio.entity.ReservaEntity;
import com.api.lumine_emporio.entity.UsuarioEntity;
import com.api.lumine_emporio.entity.enums.FormaPagamento;
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
	public String criarReserva(UsuarioEntity usuarioEntity, List<ItemCarrinhoEntity> itensCarrinho) {

	    ReservaEntity reservaEntity = new ReservaEntity();
	    reservaEntity.setUsuarioEntity(usuarioEntity);
	    reservaEntity.setStatus(StatusReserva.P);

	    try {
	        reservarRepository.save(reservaEntity);

	        itensCarrinho.forEach(item ->  
	            itemReservaService.save(new ItemReservaEntity(
	                item.getQuantidade(),
	                item.getProdutoVariacao(),
	                reservaEntity)
	            )
	        );

	        // MONTAR MENSAGEM
	        StringBuilder msgItens = new StringBuilder("*PRODUTOS DA RESERVA*\n");

	        itensCarrinho.forEach(item ->
	        msgItens.append("• *Produto:* ")
	                .append(item.getProdutoVariacao().getProduto().getNome())
	                .append("\n")
	                .append("  *Qtd:* ")
	                .append(item.getQuantidade())
	                .append("\n")
	        );

	        itemCarrinhoService.deleteAll(itensCarrinho);

	        String mensagem = "Gostaria de reservar o seguinte pedido: " +
	                "COD:" + reservaEntity.getIdReserva() + "\n" + msgItens;

	        // Encoder para URL do WhatsApp
	        String mensagemEncoded = URLEncoder.encode(mensagem, StandardCharsets.UTF_8);

	        String link = "https://api.whatsapp.com/send?phone=558182407426&text=" + mensagemEncoded;

	        return link;

	    } catch (Exception e) {
	        throw new RuntimeException("Falha ao criar reserva: " + e.getMessage());
	    }
	}
	
	
	@Transactional
	public void finalisarReserva(Long idReserva, FormaPagamento formaPagamento, String endereco){
		ReservaEntity reservaEntity = reservarRepository.findById(idReserva).orElseThrow(() -> new RuntimeException("Reserva com id: "+idReserva+" nao encontrada."));
		reservaEntity.setFormaPagamento(formaPagamento);
		reservaEntity.setEndereco(endereco);
		reservaEntity.setStatus(StatusReserva.A);
		save(reservaEntity);
	}
	
	
	
	
	
	
}
