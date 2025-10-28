package com.api.lumine_emporio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.lumine_emporio.entity.ImagemProdutoEntity;
import com.api.lumine_emporio.repository.ImagemProdutoRepository;

@Service
public class ImagemProdutoService {
	@Autowired
	private ImagemProdutoRepository imagemProdutoRepository;
	
	public ImagemProdutoEntity save(ImagemProdutoEntity imagemProdutoEntity) {
		return imagemProdutoRepository.save(imagemProdutoEntity);
	}
	public boolean existsByUrl(String url) {
		return imagemProdutoRepository.existsByUrl(url);
	}
	public Long pegarProxId() {
		return imagemProdutoRepository.pegarProxId();
	}
	public String getCaminho(Long idProduto, Long IdImagemProd, String tipoArquivo) {
		return "imagem-produtos/"+idProduto+"/"+IdImagemProd+tipoArquivo;
	}
}
