package br.com.estevam.listademateriais.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.estevam.listademateriais.dto.CategoriaDTO;
import br.com.estevam.listademateriais.model.Categoria;
import br.com.estevam.listademateriais.repository.CategoriaRepository;
import br.com.estevam.listademateriais.services.exception.ObjectNotFoundException;
import br.com.estevam.listademateriais.services.exception.OperationException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repo;
	
	public List<Categoria> findAll(){
		return repo.findAll();
	}
	
	public Categoria findById(String id) {
		Optional<Categoria> obj = repo.findById(id);
		return obj.orElseThrow(()->new ObjectNotFoundException("Categoria não encontrada"));
	}
	
	public Categoria insert(Categoria obj) {
		Categoria pai = repo.findById(obj.getPai().getId()).orElse(null);
		obj.setPai(null);
		obj = repo.insert(obj);
		if(pai!=null) {
				pai.getFilhos().add(obj);
				obj.setPai(new CategoriaDTO(pai));
				repo.save(pai);
				repo.save(obj);
		}
		return obj;
	}
	
	public void delete(String id) {
		Categoria obj = findById(id);
		if(!obj.getFilhos().isEmpty()) {
			throw new OperationException("A categoria não pode ser deletada pois possui categorias associados a ela");
		}
		if(obj.getPai()!=null) {
			Categoria pai = repo.findById(obj.getPai().getId()).orElse(null);
			if(pai!=null) {
				pai.getFilhos().remove(obj);
				repo.save(pai);
			}	
		}
		repo.delete(obj);
	}
	
	public Categoria update(Categoria obj) {
		Categoria newObj = findById(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}
	
	public void updateData(Categoria newObj, Categoria obj) {
		newObj.setNome(obj.getNome());
		Categoria pai = repo.findById(newObj.getPai().getId()).orElse(null);
		Categoria newPai = repo.findById(obj.getPai().getId()).orElse(null);
		if(newPai!=null) {
			if(pai!=null) {
				pai.getFilhos().remove(newObj);
				repo.save(pai);
			}
			newObj.setPai(new CategoriaDTO (newPai));
			newPai.getFilhos().add(newObj);
			repo.save(newPai);
		}
	}
	
	public Categoria fromDTO(CategoriaDTO objDto){
		return new Categoria(objDto.getId(),objDto.getNome());
	}
}
