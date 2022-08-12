package br.com.estevam.listademateriais.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.estevam.listademateriais.dto.FabricanteDTO;
import br.com.estevam.listademateriais.model.Fabricante;
import br.com.estevam.listademateriais.repository.FabricanteRepository;
import br.com.estevam.listademateriais.services.exception.ObjectNotFoundException;

@Service
public class FabricanteService {

	@Autowired
	private FabricanteRepository repo;
	
	public List<Fabricante> findAll(){
		return repo.findAll();
	}
	
	public Fabricante findById(String id) {
		Optional<Fabricante> user = repo.findById(id);
		return user.orElseThrow(()->new ObjectNotFoundException("Fabricante não encontrada"));
	}
	
	public Fabricante insert(Fabricante obj) {
		return repo.insert(obj);
	}
	
	public Fabricante fromDTO(FabricanteDTO objDto) {
		return new Fabricante(objDto.getId(),objDto.getNome());
	}
	
	public void delete(String id) {
		findById(id);
		repo.deleteById(id);
	}
	
	public Fabricante update(Fabricante obj) {
		Fabricante newObj = findById(obj.getId());
		updateData(newObj,obj);
		return repo.save(newObj);
	}
	public void updateData(Fabricante newObj, Fabricante obj) {
		newObj.setNome(obj.getNome());
	}
	
}
