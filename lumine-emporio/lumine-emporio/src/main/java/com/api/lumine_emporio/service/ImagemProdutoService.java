package com.api.lumine_emporio.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.lumine_emporio.entity.ImagemProdutoEntity;
import com.api.lumine_emporio.entity.ProdutoEntity;
import com.api.lumine_emporio.exception.NaoEncontradoException;
import com.api.lumine_emporio.repository.ImagemProdutoRepository;

import jakarta.transaction.Transactional;

@Service
public class ImagemProdutoService {
	@Autowired
	private ImagemProdutoRepository imagemProdutoRepository;
	
	@Transactional
	public ImagemProdutoEntity save(ImagemProdutoEntity imagemProdutoEntity) {
		return imagemProdutoRepository.save(imagemProdutoEntity);
	}
	public boolean existsByUrl(String url) {
		return imagemProdutoRepository.existsByUrl(url);
	}

	public String getCaminho(Long idProduto, int nome, String tipoArquivo) {
	    String ext = tipoArquivo.substring(tipoArquivo.lastIndexOf("."));
	    return "/app/imagens/" + idProduto + "/" + nome + ext;
	}
	
	@Transactional
	public void deleteById(Long id) {
		imagemProdutoRepository.deleteById(id);
	}
	
	public List<ImagemProdutoEntity> findAllByProduto(ProdutoEntity produto){
		return imagemProdutoRepository.findAllByProduto(produto);
	}
	public ImagemProdutoEntity findById(Long id) {
		return imagemProdutoRepository.findById(id).orElseThrow(() -> new NaoEncontradoException("ImagemProd com ID: "+id+" não encontrado."));
	}
}
