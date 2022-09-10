package br.com.estevam.listademateriais.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import br.com.estevam.listademateriais.dto.FabricanteDTO;
import br.com.estevam.listademateriais.repository.FabricanteRepository;

@Component
public class FabricanteDTOConverter implements Converter<String, FabricanteDTO>{

	@Autowired
	private FabricanteRepository repo;

	public FabricanteDTOConverter(FabricanteRepository repo) {
		this.repo=repo;
	}
	
	@Override
	public FabricanteDTO convert(String source) {
		return new FabricanteDTO(repo.findById(source).orElse(null));
	}
	
	
	
}
