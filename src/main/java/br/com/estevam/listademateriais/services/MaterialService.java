package br.com.estevam.listademateriais.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.estevam.listademateriais.dto.FabricanteDTO;
import br.com.estevam.listademateriais.dto.MaterialDTO;
import br.com.estevam.listademateriais.model.Fabricante;
import br.com.estevam.listademateriais.model.Material;
import br.com.estevam.listademateriais.model.Referencia;
import br.com.estevam.listademateriais.repository.FabricanteRepository;
import br.com.estevam.listademateriais.repository.MaterialRepository;
import br.com.estevam.listademateriais.services.exception.ObjectNotFoundException;

@Service
public class MaterialService {

	@Autowired
	private MaterialRepository repo;
	
	@Autowired
	FabricanteRepository fabricanteRepository;
	
	public List<Material> findalAll(){
		return repo.findAll();
	}
	
	public Material findById(String id) {
		Optional<Material> obj = repo.findById(id);
		return obj.orElseThrow(()->new ObjectNotFoundException("Material não encontrado"));
	}
	
	public Material insert(Material obj) {
		obj = repo.insert(obj);
		for(Referencia ref : obj.getReferencias()) {
			ref.setMaterial(new MaterialDTO(obj));
		}
		return repo.save(obj);
	}
	
	public void delete(String id) {
		findById(id);
		repo.deleteById(id);
	}
	
	public Material update(Material obj) {
		Material newObj = findById(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}
	
	public void updateData(Material newObj, Material obj) {
		newObj.setDescricao(obj.getDescricao());
	}
	
	public Material fromDTO(MaterialDTO objDTO) {
		return new Material(objDTO.getId(),objDTO.getDescricao());
	}
	
	public Referencia addReferencia(Material material, Fabricante fabricante, String referencia) {
		material = repo.findById(material.getId()).orElseThrow(()->new ObjectNotFoundException("Material não encontrado"));		
		fabricante = fabricanteRepository.findById(fabricante.getId()).orElseThrow(()->new ObjectNotFoundException("Fabricante não encontrado"));
		Referencia obj = new Referencia(new MaterialDTO(material), new FabricanteDTO(fabricante),referencia);
		material.getReferencias().add(obj);
		fabricante.getReferencias().add(obj);
		fabricanteRepository.save(fabricante);
		repo.save(material);
		return obj;
	}
	
	public void removeReferencia(Referencia obj) {
		Material material = repo.findById(obj.getMaterial().getId()).orElseThrow(()->new ObjectNotFoundException("Material não encontrado"));		
		Fabricante fabricante = fabricanteRepository.findById(obj.getFabricante().getId()).orElseThrow(()->new ObjectNotFoundException("Fabricante não encontrado"));		
		material.getReferencias().remove(obj);
		fabricante.getReferencias().remove(obj);
		fabricanteRepository.save(fabricante);
		repo.save(material);
	}
}
