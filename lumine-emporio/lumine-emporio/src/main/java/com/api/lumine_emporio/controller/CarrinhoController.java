package com.api.lumine_emporio.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.api.lumine_emporio.dtos.AddCarrinhoDTO;
import com.api.lumine_emporio.dtos.AtualizarQuantidadeDTO;
import com.api.lumine_emporio.entity.ItemCarrinhoEntity;
import com.api.lumine_emporio.service.CarrinhoService;

@RestController
@RequestMapping("/api/carrinho")
@CrossOrigin(origins = "*")
public class CarrinhoController {
	
	@Autowired
	private CarrinhoService carrinhoService;
	
	@PostMapping("/adicionar")
	public ResponseEntity<?> adicionarAoCarrinho(@RequestBody AddCarrinhoDTO dto) {
		try {
			ItemCarrinhoEntity item = carrinhoService.adicionarAoCarrinho(
				dto.getIdUsuario(), 
				dto.getIdProdutoVariacao(), 
				dto.getQuantidade()
			);
			
			Map<String, Object> resposta = new HashMap<>();
			resposta.put("mensagem", "Produto adicionado ao carrinho com sucesso!");
			resposta.put("item", item);
			
			return ResponseEntity.status(HttpStatus.CREATED).body(resposta);
		} catch (Exception e) {
			Map<String, String> erro = new HashMap<>();
			erro.put("erro", e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
		}
	}
	
	@GetMapping("/{idUsuario}")
	public ResponseEntity<?> listarCarrinho(@PathVariable UUID idUsuario) {
		try {
			List<ItemCarrinhoEntity> itens = carrinhoService.listarCarrinho(idUsuario);
			
			Map<String, Object> resposta = new HashMap<>();
			resposta.put("itens", itens);
			resposta.put("total", carrinhoService.calcularTotal(idUsuario));
			resposta.put("quantidade", itens.size());
			
			return ResponseEntity.ok(resposta);
		} catch (Exception e) {
			Map<String, String> erro = new HashMap<>();
			erro.put("erro", e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
		}
	}
	
	@PutMapping("/atualizar/{idUsuario}/{idItemCarrinho}")
	public ResponseEntity<?> atualizarQuantidade(
		@PathVariable UUID idUsuario,
		@PathVariable Long idItemCarrinho,
		@RequestBody AtualizarQuantidadeDTO dto
	) {
		try {
			ItemCarrinhoEntity item = carrinhoService.atualizarQuantidade(
				idUsuario, 
				idItemCarrinho, 
				dto.getNovaQuantidade()
			);
			
			Map<String, Object> resposta = new HashMap<>();
			resposta.put("mensagem", "Quantidade atualizada com sucesso!");
			resposta.put("item", item);
			resposta.put("total", carrinhoService.calcularTotal(idUsuario));
			
			return ResponseEntity.ok(resposta);
		} catch (Exception e) {
			Map<String, String> erro = new HashMap<>();
			erro.put("erro", e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
		}
	}
	
	@DeleteMapping("/remover/{idUsuario}/{idItemCarrinho}")
	public ResponseEntity<?> removerDoCarrinho(
		@PathVariable UUID idUsuario,
		@PathVariable Long idItemCarrinho
	) {
		try {
			carrinhoService.removerDoCarrinho(idUsuario, idItemCarrinho);
			
			Map<String, Object> resposta = new HashMap<>();
			resposta.put("mensagem", "Produto removido do carrinho com sucesso!");
			resposta.put("total", carrinhoService.calcularTotal(idUsuario));
			
			return ResponseEntity.ok(resposta);
		} catch (Exception e) {
			Map<String, String> erro = new HashMap<>();
			erro.put("erro", e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
		}
	}
	
	@DeleteMapping("/limpar/{idUsuario}")
	public ResponseEntity<?> limparCarrinho(@PathVariable UUID idUsuario) {
		try {
			carrinhoService.limparCarrinho(idUsuario);
			
			Map<String, Object> resposta = new HashMap<>();
			resposta.put("mensagem", "Carrinho limpo com sucesso!");
			
			return ResponseEntity.ok(resposta);
		} catch (Exception e) {
			Map<String, String> erro = new HashMap<>();
			erro.put("erro", e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
		}
	}
	
	@GetMapping("/total/{idUsuario}")
	public ResponseEntity<?> obterTotal(@PathVariable UUID idUsuario) {
		try {
			double total = carrinhoService.calcularTotal(idUsuario);
			
			Map<String, Double> resposta = new HashMap<>();
			resposta.put("total", total);
			
			return ResponseEntity.ok(resposta);
		} catch (Exception e) {
			Map<String, String> erro = new HashMap<>();
			erro.put("erro", e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
		}
	}
}