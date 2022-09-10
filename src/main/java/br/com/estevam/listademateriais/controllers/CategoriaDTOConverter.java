package br.com.estevam.listademateriais.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import br.com.estevam.listademateriais.dto.CategoriaDTO;
import br.com.estevam.listademateriais.repository.CategoriaRepository;

@Component
public class CategoriaDTOConverter implements Converter<String, CategoriaDTO>{

	@Autowired
	private CategoriaRepository repo;

	public CategoriaDTOConverter(CategoriaRepository repo) {
		this.repo=repo;
	}
	
	@Override
	public CategoriaDTO convert(String source) {
		return new CategoriaDTO(repo.findById(source).orElse(null));
	}
	
	
	
}
